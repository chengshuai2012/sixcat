package com.link.cloud.utils;

import android.os.Environment;
import android.util.Log;

import com.link.cloud.network.ApiService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 49488 on 2018/7/30.
 */

public class DownLoad {
    static Retrofit retrofit ;
    public static void download(String url, final String UID) {
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl("http://devicepackage.oss-cn-shenzhen.aliyuncs.com/").addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        ApiService downloadService = retrofit.create(ApiService.class);
        String fileUrl = url.substring(url.lastIndexOf("/"),url.length());
        Log.e("my",fileUrl);
        Call<ResponseBody> responseBodyCall = downloadService.downloadFile(fileUrl);
        responseBodyCall.enqueue(new Callback<ResponseBody>()

        {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   final Response<ResponseBody> response) {
                final File file = createFile(UID); //下载文件放在子线程
                new Thread() {
                    @Override
                    public void run() {
                        super.run(); //保存到本地
                        writeFile2Disk(response, file);
                    }
                }.start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

            }
        });}

    public static File createFile(String uid) {
        File file = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File fileD = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/faceFile");
            if(!fileD.exists()) {
                fileD.mkdir();
            }
            file = new File(fileD.getAbsolutePath()+"/"+uid+".data");
            if(file.exists()){
                file.delete();
            }
        }
        return file;
    }

    public static void writeFile2Disk(Response<ResponseBody> response, File file) {
        OutputStream os = null;
        InputStream is = response.body().byteStream();
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    }
