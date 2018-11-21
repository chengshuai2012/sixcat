package com.link.cloud.network.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：qianlu on 2018/11/20 15:20
 * 邮箱：zar.l@qq.com
 */
public class LessonInfoBean implements Serializable {

    private String lessonName;
    private String lessonId;
    private String lessonDate;
    private List<CardInfoBean> cardInfo;

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public List<CardInfoBean> getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(List<CardInfoBean> cardInfo) {
        this.cardInfo = cardInfo;
    }
}
