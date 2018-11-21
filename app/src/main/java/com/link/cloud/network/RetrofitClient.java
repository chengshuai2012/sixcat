package com.link.cloud.network;


import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.link.cloud.SixCatApplication;
import com.link.cloud.utils.Utils;
import com.zitech.framework.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private static Context context;
    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";
    public static final String CONTENT_TYPE = "Content-Type:";
    public static final String JSON = "application/json;charset=utf-8";
    public static final String FORM = "application/x-www-form-urlencoded";


    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    //    private static final String ROOT_URL = "";
    private static OkHttpClient mOkHttpClient;
    private static boolean debug = true;

    private HashMap<String, Retrofit> mRetrofits;

    private static volatile RetrofitClient instance = null;

    public static RetrofitClient getInstance() {
        context = SixCatApplication.getInstance().getApplicationContext();
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }


    private RetrofitClient() {
        mRetrofits = new HashMap<>();
        initOkHttpClient();
//        mRetrofits.put()
    }

    public static void setDebug(boolean debug) {
        RetrofitClient.debug = debug;
    }


    public Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                //拓展添加RxJava的功能，导入的库：compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //使用Gson对Json进行解析，导入的库：compile 'com.squareup.retrofit2:converter-gson:2.0.2'
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T create(String baseUrl, Class<T> mClass) {
        if (!mRetrofits.containsKey(baseUrl)) {
            mRetrofits.put(baseUrl, createRetrofit(baseUrl));
        }
        return mRetrofits.get(baseUrl).create(mClass);
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (mOkHttpClient == null) {

                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(BaseApplication.getInstance().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);
                    OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache);
                    //拓展功能：网络请求的log，compile 'com.squareup.okhttp3:logging-interceptor:3.2.0'
                    builder.addInterceptor(mRewriteCacheControlInterceptor);
                    builder.addNetworkInterceptor(mRewriteCacheControlInterceptor);
                    builder.addInterceptor(interceptor);

                    SSLContext ctx = null;
                    try {
                        ctx = SSLContext.getInstance("SSL");
                        ctx.init(new KeyManager[0], new TrustManager[]{new TrustAllCertsManager()}, new SecureRandom());
                    } catch (KeyManagementException | NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    SSLSocketFactory sslSocketFactory = ctx.getSocketFactory();
                    builder.sslSocketFactory(sslSocketFactory);
                    builder.hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });

//                            .cookieJar(new CookiesManager())
                    mOkHttpClient = builder.retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();


                }
            }
        }
    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        private static final String F_BREAK = " %n";
        private static final String F_URL = " %s";
        private static final String F_TIME = " in %.1fms";
        private static final String F_HEADERS = "%s";
        private static final String F_RESPONSE = F_BREAK + "Response: %d";
        private static final String F_BODY = "body: %s";
        private static final String F_BREAKER = F_BREAK + "-------------------------------------------" + F_BREAK;
        private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS;
        private static final String F_RESPONSE_WITHOUT_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BREAKER;
        private static final String F_REQUEST_WITH_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS + F_BODY + F_BREAK;
        private static final String F_RESPONSE_WITH_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BODY + F_BREAK + F_BREAKER;
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            String code = "link";
            String datetime = System.currentTimeMillis() + "";
            String appkey = ApiConstants.APP_KEY;
            String sign = Utils.generateSign(code, appkey, datetime);
            Request.Builder requestBuilder = original.newBuilder();
            JsonObject postBody = bodyToJsonObject(original.body());
            if (postBody == null) {
                postBody = new JsonObject();
            }
            postBody.addProperty("key", appkey);
            postBody.addProperty("datetime", datetime);
            postBody.addProperty("code", code);
            postBody.addProperty("sign", sign);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), postBody.toString());
            requestBuilder.post(requestBody);
            Request request = requestBuilder.build();
            //return chain.proceed(request);
            long t1 = System.nanoTime();
            Response originalResponse = chain.proceed(request);
            long t2 = System.nanoTime();
            double time = (t2 - t1) / 1e6d;

            return originalResponse;
        }

        private String stringifyRequestBody(Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }

        public JsonObject bodyToJsonObject(final RequestBody request) {
            try {
                final RequestBody copy = request;
                final Buffer buffer = new Buffer();
                if (copy != null)
                    copy.writeTo(buffer);
                else
                    return null;
                return new JsonParser().parse(buffer.readUtf8()).getAsJsonObject();
            } catch (final Exception e) {
                return null;
            }
        }
    };


    private static class TrustAllCertsManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }


    /**
     * 自动管理Cookies
     */
    private class CookiesManager implements CookieJar {
        private final PersistentCookieCache cookieStore = new PersistentCookieCache(BaseApplication.getInstance(), true);

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }
}
