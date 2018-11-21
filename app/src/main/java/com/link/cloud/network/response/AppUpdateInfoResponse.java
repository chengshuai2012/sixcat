package com.link.cloud.network.response;

/**
 * 作者：qianlu on 2018/11/9 17:32
 * 邮箱：zar.l@qq.com
 */
public class AppUpdateInfoResponse {

    /**
     * id : 12
     * package_remark : 优化软件卡顿
     * package_path : http://devicepackage.oss-cn-shenzhen.aliyuncs.com/20180805/智能储物柜2.3.1.1.apk
     * package_name : 储物柜
     * appid : LXFit001
     * package_state : 1
     * package_id : 11
     * package_ctime : 2018-08-05 14:59:20
     * ctime : 2018-08-05 15:44:28
     * package_version : 232
     * device_type_id : 4
     */

    private int id;
    private String package_remark;
    private String package_path;
    private String package_name;
    private String appid;
    private String package_state;
    private int package_id;
    private String package_ctime;
    private String ctime;
    private int package_version;
    private int device_type_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackage_remark() {
        return package_remark;
    }

    public void setPackage_remark(String package_remark) {
        this.package_remark = package_remark;
    }

    public String getPackage_path() {
        return package_path;
    }

    public void setPackage_path(String package_path) {
        this.package_path = package_path;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPackage_state() {
        return package_state;
    }

    public void setPackage_state(String package_state) {
        this.package_state = package_state;
    }

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public String getPackage_ctime() {
        return package_ctime;
    }

    public void setPackage_ctime(String package_ctime) {
        this.package_ctime = package_ctime;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getPackage_version() {
        return package_version;
    }

    public void setPackage_version(int package_version) {
        this.package_version = package_version;
    }

    public int getDevice_type_id() {
        return device_type_id;
    }

    public void setDevice_type_id(int device_type_id) {
        this.device_type_id = device_type_id;
    }
}
