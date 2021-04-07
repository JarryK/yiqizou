package com.web.controller;

import com.alibaba.fastjson.JSON;
import com.web.base.RestResult;
import com.web.model.User;
import com.web.service.LoginService;
import com.web.dao.UserInfoMapper;
import com.web.hotdata.HotDataStore;
import com.web.service.WeiXinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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
@RequestMapping("/yqz/login")
@Api(tags = "登录相关操作")
public class LoginController extends BaseController {

    @Resource
    private final LoginService loginBmo;

    @Resource
    private final UserInfoMapper userInfoMapper;

    private final HotDataStore hotDataStore;


    private final WeiXinService weiXinService;

    @ApiOperation("微信登录")
    @ResponseBody
    @RequestMapping("/wxLogin")
    public RestResult<Object> getUserInfo(@RequestBody  Map<String, Object> inMap, HttpSession session) throws Exception {
        try {
            log.info("inMap:" + JSON.toJSONString(inMap));
            Map<String,Object> userInfo = (Map<String, Object>) MapUtils.getMap(inMap,"userInfo", new LinkedHashMap());
            String code = MapUtils.getString(inMap,"code").trim();
            String openid = weiXinService.getOpenId(code);
            String nickName = weiXinService.filterEmoji(MapUtils.getString(userInfo,"nickName"),"");
            int gender = (int) MapUtils.getObject(userInfo,"gender");
            String language = MapUtils.getString(userInfo,"language");
            String city = MapUtils.getString(userInfo,"city");
            String province = MapUtils.getString(userInfo,"province");
            String country = MapUtils.getString(userInfo,"country");
            String avatarUrl = MapUtils.getString(userInfo,"avatarUrl");
            User user = new User();
            if (openid.equals("")){
                return RestResult.error("授权失败");
            }
            user.setOpenId(openid);
            user.setNickName(nickName);
            user.setGender(gender);
            user.setLanguage(language);
            user.setCity(city);
            user.setProvince(province);
            user.setCountry(country);
            user.setAvatarUrl(avatarUrl);
            user.setLastLoginTime(new Date());
            int i;
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("openId",openid);
            List<User> list = userInfoMapper.selectByExample(example);
            if (list.size() > 0){
                User user1 = list.get(0);
                user.setUserId(user1.getUserId());
                user.setUpDataTime(new Date());
                i = userInfoMapper.updateByPrimaryKey(user);
            }else {
                user.setCreateTime(new Date());
                i = loginBmo.saveUser(user);
            }
            if (i < 1){
                return RestResult.error("登陆失败！");
            }
            return RestResult.success("登录成功",weiXinService.wxLogin(user));
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            return RestResult.error("登录异常！");
        }
    }

}
