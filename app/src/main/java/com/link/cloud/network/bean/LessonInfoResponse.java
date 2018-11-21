package com.link.cloud.network.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：qianlu on 2018/11/20 15:20
 * 邮箱：zar.l@qq.com
 */
public class LessonInfoResponse implements Serializable{


    private String coach;
    private String memberImg;
    private String coachImg;
    private String memberName;
    private List<LessonInfoBean> lessonInfo;


    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getMemberImg() {
        return memberImg;
    }

    public void setMemberImg(String memberImg) {
        this.memberImg = memberImg;
    }

    public String getCoachImg() {
        return coachImg;
    }

    public void setCoachImg(String coachImg) {
        this.coachImg = coachImg;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public List<LessonInfoBean> getLessonInfo() {
        return lessonInfo;
    }

    public void setLessonInfo(List<LessonInfoBean> lessonInfo) {
        this.lessonInfo = lessonInfo;
    }
}
