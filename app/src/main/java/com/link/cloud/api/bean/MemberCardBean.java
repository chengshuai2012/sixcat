package com.link.cloud.api.bean;

/**
 * 作者：qianlu on 2018/10/25 11:23
 * 邮箱：zar.l@qq.com
 */
public class MemberCardBean {

    private String cardName;
    private String cardNumber;
    private String beginTime;
    private String endTime;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
