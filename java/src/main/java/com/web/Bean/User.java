package com.web.Bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.Bean<br>
 * <b>创建人：</b><br>
 * <b>类描述：</b>用户实体类<br>
 * <b>创建时间：</b>2020/11/24 22:52<br>
 */
public class User extends JSONObject implements Serializable {

    private static final long serialVersionUID = 8666322188894677723L;

    private Long user_id;
    private String nickName;
    private int gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String phone;
    private Date create_time;
    private Date upData_time;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpData_time() {
        return upData_time;
    }

    public void setUpData_time(Date upData_time) {
        this.upData_time = upData_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (getGender() != user.getGender()) return false;
        if (getUser_id() != null ? !getUser_id().equals(user.getUser_id()) : user.getUser_id() != null) return false;
        if (getNickName() != null ? !getNickName().equals(user.getNickName()) : user.getNickName() != null)
            return false;
        if (getLanguage() != null ? !getLanguage().equals(user.getLanguage()) : user.getLanguage() != null)
            return false;
        if (getCity() != null ? !getCity().equals(user.getCity()) : user.getCity() != null) return false;
        if (getProvince() != null ? !getProvince().equals(user.getProvince()) : user.getProvince() != null)
            return false;
        if (getCountry() != null ? !getCountry().equals(user.getCountry()) : user.getCountry() != null) return false;
        if (getAvatarUrl() != null ? !getAvatarUrl().equals(user.getAvatarUrl()) : user.getAvatarUrl() != null)
            return false;
        if (getPhone() != null ? !getPhone().equals(user.getPhone()) : user.getPhone() != null) return false;
        if (getCreate_time() != null ? !getCreate_time().equals(user.getCreate_time()) : user.getCreate_time() != null)
            return false;
        return getUpData_time() != null ? getUpData_time().equals(user.getUpData_time()) : user.getUpData_time() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getUser_id() != null ? getUser_id().hashCode() : 0);
        result = 31 * result + (getNickName() != null ? getNickName().hashCode() : 0);
        result = 31 * result + getGender();
        result = 31 * result + (getLanguage() != null ? getLanguage().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getProvince() != null ? getProvince().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getAvatarUrl() != null ? getAvatarUrl().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getCreate_time() != null ? getCreate_time().hashCode() : 0);
        result = 31 * result + (getUpData_time() != null ? getUpData_time().hashCode() : 0);
        return result;
    }
}
