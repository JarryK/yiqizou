package com.web.service;

import com.alibaba.fastjson.JSONObject;
import com.web.model.User;

import java.util.Map;

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

    /**
     * emoji表情替换
     *
     * @param source 原字符串 slipStr 替换的字符
     * @return 过滤后的字符串
     */
    public String filterEmoji(String source,String slipStr);

    /**
     * 微信登录
     * @param user
     * @return
     */
    public Map<String,Object> wxLogin(User user);
}
