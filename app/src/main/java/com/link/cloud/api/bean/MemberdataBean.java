package com.link.cloud.api.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：qianlu on 2018/10/25 11:24
 * 邮箱：zar.l@qq.com
 */
public class MemberdataBean {


    private UserInfoBean userInfo;
    private List<MemberCardBean> memberCard;


    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public List<MemberCardBean> getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(List<MemberCardBean> memberCard) {
        this.memberCard = memberCard;
    }
}
