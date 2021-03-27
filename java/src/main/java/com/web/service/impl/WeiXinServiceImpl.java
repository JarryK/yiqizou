package com.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web.base.CommonParams;
import com.web.service.WeiXinService;
import com.web.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
@Primary
public class WeiXinServiceImpl implements WeiXinService {


    @Override
    public String getOpenId(String code) throws Exception {
        String url = CommonParams.WX_URL.jscode2session + "?appId=" +
                CommonParams.WX_URL.appId + "&secret=" + CommonParams.WX_URL.appSecret +
                "&js_code=" + code + "&grant_type=authorization_code";
        String resultJson = HttpUtil.doPost(url);
        Map resultJsonMap = JSON.parseObject(resultJson,Map.class);
        return MapUtils.getString(resultJsonMap,"openid","");
    }

    @Override
    public JSONObject getUserInfo(String openid) throws Exception {
        String url = CommonParams.WX_URL.userInfo + "?openId" + openid;
        String resultJson = HttpUtil.doPost(url);
        return JSON.parseObject(resultJson);
    }
}
