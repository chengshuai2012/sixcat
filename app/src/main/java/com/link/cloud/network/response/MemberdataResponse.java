package com.link.cloud.network.response;

import com.link.cloud.network.bean.MemberCardBean;
import com.link.cloud.network.bean.UserInfoBean;

import java.util.List;

/**
 * 作者：qianlu on 2018/11/15 16:47
 * 邮箱：zar.l@qq.com
 */
public class MemberdataResponse {
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
