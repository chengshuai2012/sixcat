package com.link.cloud.network.bean;

import java.io.Serializable;

/**
 * 作者：qianlu on 2018/11/20 15:21
 * 邮箱：zar.l@qq.com
 */
public class CardInfoBean implements Serializable {

    private String cardNo;
    private String cardDate;
    private String cardName;
    private int cardTimes;
    private String cardType;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardTimes() {
        return cardTimes;
    }

    public void setCardTimes(int cardTimes) {
        this.cardTimes = cardTimes;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
