package com.link.cloud.network.bean;


/**
 * 作者：qianlu on 2018/11/15 16:48
 * 邮箱：zar.l@qq.com
 */
public class MemberCardBean {


    private String cardName;
    private String cardNumber;
    private String beginTime;
    private String endTime;

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCardName() {
        return cardName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
