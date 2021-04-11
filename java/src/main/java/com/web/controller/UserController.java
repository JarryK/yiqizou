package com.web.controller;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.web.base.RestResult;
import com.web.model.User;
import com.web.model.qo.WeiXinLogin;
import com.web.mapper.UserMapper;
import com.web.hotdata.HotDataStore;
import com.web.service.UserService;
import com.web.service.WeiXinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>java.com.web.Controller<br>
 * <b>创建人：</b><br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020/11/23 23:07<br>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/user")
@Api(tags = "登录相关操作")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final HotDataStore hotDataStore;

    private final UserMapper mapper;
    private final UserService service;



    private final WeiXinService weiXinService;

    @ApiOperation("微信登录,没有就会默认录入信息")
    @ResponseBody
    @RequestMapping("/wxLogin")
    public RestResult<Object> getUserInfo(@RequestBody WeiXinLogin info, HttpSession session) throws Exception {
        try {
            log.info("inMap:" + JSON.toJSONString(info));
            Map<String,Object> userInfo = (Map<String,Object>) info.getUserInfo();
            String code = info.getCode().trim();;
            String openid = weiXinService.getOpenId(code);
            if (openid.equals("")){
                return RestResult.error("授权失败");
            }
            String nickName = weiXinService.filterEmoji(MapUtils.getString(userInfo,"nickName"),"");
            int gender = (int) MapUtils.getObject(userInfo,"gender");
            String language = MapUtils.getString(userInfo,"language");
            String city = MapUtils.getString(userInfo,"city");
            String province = MapUtils.getString(userInfo,"province");
            String country = MapUtils.getString(userInfo,"country");
            String avatarUrl = MapUtils.getString(userInfo,"avatarUrl");
            User user = new User();
            user.setOpenId(openid);
            user.setNickName(nickName);
            user.setGender(gender);
            user.setLanguage(language);
            user.setCity(city);
            user.setProvince(province);
            user.setCountry(country);
            user.setAvatarUrl(avatarUrl);
            user.setLastLoginTime(new Date());
            user.setCreditScore(100);
            User user_info = service.selectByOpenId(openid);
            if (Validator.isNotNull(user_info)){
                user.setUserId(user_info.getUserId());
                int i = service.update(user);
                if (i < 1){
                    return RestResult.error("更新用户信息失败！");
                }
            }else {
                int i = service.insert(user);
                if (i < 1){
                    return RestResult.error("保存用户信息失败！");
                }
            }
            return RestResult.success("登录成功",weiXinService.wxLogin(user));
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            return RestResult.error("登录异常！");
        }
    }


}
