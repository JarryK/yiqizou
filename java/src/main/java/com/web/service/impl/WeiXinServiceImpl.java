package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web.base.WeiXinParams;
import com.web.model.User;
import com.web.hotdata.HotDataStore;
import com.web.security.jwt.JwtSecurityProperties;
import com.web.security.jwt.JwtTokenUtils;
import com.web.security.model.UserSecurity;
import com.web.security.service.IUserSecurityService;
import com.web.service.WeiXinService;
import com.web.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Primary
public class WeiXinServiceImpl implements WeiXinService {

    private final JwtSecurityProperties properties;
    private final JwtTokenUtils tokenUtils;
    private final HotDataStore hotDataStore;

    private final WeiXinParams weiXinParams;
    private final IUserSecurityService userSecurityService;

    @Override
    public String getOpenId(String code) throws Exception {
        String url = weiXinParams.jscode2session + "?appId=" +
                weiXinParams.appId + "&secret=" + weiXinParams.appSecret +
                "&js_code=" + code + "&grant_type=authorization_code";
        String resultJson = HttpUtil.doPost(url);
        Map resultJsonMap = JSON.parseObject(resultJson,Map.class);
        return MapUtils.getString(resultJsonMap,"openid","");
    }

    @Override
    public JSONObject getUserInfo(String openid) throws Exception {
        String url = weiXinParams.userInfo + "?openId" + openid;
        String resultJson = HttpUtil.doPost(url);
        return JSON.parseObject(resultJson);
    }

    /**
     * emoji表情替换
     *
     * @param source 原字符串 slipStr 替换的字符
     * @return 过滤后的字符串
     */
    @Override
    public String filterEmoji(String source,String slipStr) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        }else{
            return source;
        }
    }

    @Override
    public Map<String, Object> wxLogin(User user) {
        UserSecurity userSecurity = userSecurityService.queryUserSecurityByUsername(user.getUserId());
        // 创建Token
        String token = tokenUtils.createToken(userSecurity);
        hotDataStore.set(user.getUserId() + "_info",user);
        // 将Token信息放入缓存,如果之前有相同账号登录过的话，新的token会覆盖原token，原token不可用，相当于自动踢掉已登录用户
        hotDataStore.set(user.getUserId().toString(), token,properties.getTokenValidityInSeconds());
        Map<String, Object> authInfo = new HashMap<String, Object>(3) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", userSecurity);
            put("user_info", user);
        }};
        return authInfo;
    }
}
