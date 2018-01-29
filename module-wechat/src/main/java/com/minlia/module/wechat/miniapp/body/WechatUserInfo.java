package com.minlia.module.wechat.miniapp.body;

import com.minlia.module.wechat.miniapp.enumeration.Gender;

public class WechatUserInfo {
    private String openId;
    private String nickName;
    private Gender gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = Gender.valueOf(gender);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getAvatarUrl(int size) {
        switch (size) {
            case 0:
            case 46:
            case 64:
            case 96:
            case 132:
                return replaceAvatarSize(size);
        }
        return null;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }



    private String replaceAvatarSize(int size) {
        if (avatarUrl == null) {
            return null;
        }
        int pos = avatarUrl.lastIndexOf('/');
        if (pos == -1) {
            return null;
        }
        return avatarUrl.substring(0, pos + 1) + size;
    }
    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
