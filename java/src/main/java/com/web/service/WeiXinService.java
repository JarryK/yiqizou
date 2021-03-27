package com.web.service;

import com.alibaba.fastjson.JSONObject;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.service<br>
 * <b>创建人：</b>DadaYu<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2021/3/26 16:24<br>
 */
public interface WeiXinService {

    /**
     * 通过授权Code获取用户唯一标识
     * @param code
     * @return
     */
    public String getOpenId(String code) throws Exception;

    /**
     * 通过唯一标识 openId 获取用户信息
     * @param openid
     * @return
     */
    public JSONObject getUserInfo(String openid) throws Exception;
}
