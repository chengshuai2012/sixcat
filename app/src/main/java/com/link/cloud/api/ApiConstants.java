package com.link.cloud.api;


/**
 * Created by qianlu on 2018/5/16.
 * 邮箱：zar.l@qq.com
 */
public class ApiConstants {
    //API接口地址
    public static String REST_API_URL = "http://39.108.100.128:8082/api/";//mia_da

    public static final String HOME_PAGE_URL = "http://www.soonvein.com/";

    public static final String BASE_DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    //用户绑定账号验证
    public  static final  String VALIDATIONUSER="validationUser";

    //绑定人脸信息
    public  static final  String BINDUSERFACE="bindUserFace";

    //生物特征绑定(指静脉)
    public  static final  String BINDUSERFINGER="bindUserFinger";

    //人员进出场验证
    public  static final  String ISOPENBRAKE="isOpenBrake";

    //签到
    public  static final  String SIGNIN="signIn";

    //更衣柜操作
    public  static final  String ISOPENCABINET="isOpenCabinet";

    //更衣柜操作
    public  static final  String CLEARCABINET="clearCabinet";

    //更衣柜操作
    public  static final  String OPENCABINET="openCabinet";


    //设置衣柜暂停使用,如果传了cabinetNumber，则暂停单个箱柜，不传则暂停所有
    public  static final  String DISABLECABINET="disableCabinet";

    //设置衣柜恢复使用,如果传了cabinetNumber，则开启单个箱柜，不传则开启所有
    public  static final  String RECOVERYCABINET="recoveryCabinet";

    //更衣柜操作
    public  static final  String GETLESSONINFO="getLessonInfo";

    //选择即将消课课程
    public  static final  String SELECTLESSON="selectLesson";
    //解析二维码
    public  static final  String VALIDATIONQRCODE="validationQrCode";
    //消费
    public  static final  String CONSUME="consume";
    //-人员进出场验证（二维码）
    public  static final  String OPENBRAKEBYQRCODE="openBrakeByQrcode";
    //更衣柜操作（二维码）
    public  static final  String OPENCABINETBYQRCODE="openCabinetByQrCode";
    //签到（二维码）
    public  static final  String SIGNINBYQRCODE="signInByQrCode";

    public static final String APP_KEY = "APP_KEY";
    public static final String APP_SECRET = "APP_SECRET";
    public static final String API_URL = "API_URL";
    public static final String CHANNEL_NAME = "CHANNEL_NAME";
    public static final String KEY_DEVICE_ID = "deviceID";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_REG_USER_COUNT = "reg_user_count";
    public static final String EXTRAS_SHORTCUT = "shortcut2desktop";
    public static final String EXTRAS_FRAGMENT = "fragments";
    public static final String EXTRAS_PHONENUM = "phoneNum";
    public static final String EXTRAS_PRICE = "price";
    public static final String EXTRAS_MARK = "mark";
    public static final String EXTRAS_MEMBER = "memberInfo";
    public static final String EXTRAS_SIGNED_INFO = "signedInInfo";
    public static final String EXTRAS_ELIMINATE_INFO="eliminateInfo";
    public static final String EXTRAS_PAY_INFO="payedInfo";
    public static final String EXTRAS_LESSON_INFO="lessonInfo";
    public static final String EXTRAS_DOWHAT = "doWhat";
    public static final int ACTION_REGISTER = 0x0001;
    public static final int ACTION_SIGNIN = 0x0002;
    public static final int ACTION_CONSUME = 0x0003;
    public static final int ACTION_Eliminate = 0x0003;







}
