package com.web.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.web.base.CommonParams;
import com.web.base.RestResult;
import com.web.bean.User;
import com.web.service.LoginService;
import com.web.dao.UserInfoMapper;
import com.web.hotdata.HotDataStore;
import com.web.service.WeiXinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
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

//    @ResponseBody
//    @RequestMapping("/getInfo")
//    public RestResult<Object> bindWx(@RequestBody  Map<String, Object> inMap, HttpSession session) throws Exception {
//
//    }

    /**
     * emoji表情替换
     *
     * @param source 原字符串
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source,String slipStr) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        }else{
            return source;
        }
    }

    @ApiOperation("微信登录")
    @ResponseBody
    @RequestMapping("/wxLogin")
    public RestResult<Object> getUserInfo(@RequestBody  Map<String, Object> inMap, HttpSession session) throws Exception {
        try {
            log.info("inMap:" + JSON.toJSONString(inMap));
            Map<String,Object> userInfo = (Map<String, Object>) MapUtils.getMap(inMap,"userInfo", new LinkedHashMap());
            // LinkedHashMap转实列对象
//            User user = JSON.parseObject(JSON.toJSONString(userInfo), new TypeReference<User>() { });

            String code = MapUtils.getString(inMap,"code").trim();
            String openid = weiXinService.getOpenId(code);
            String nickName = filterEmoji(MapUtils.getString(userInfo,"nickName"),"");
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
            int i;
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("openId",openid);
            List<User> list = userInfoMapper.selectByExample(example);
            if (list.size() > 0){
                User user1 = list.get(0);
                user.setUser_id(user1.getUser_id());
                i = userInfoMapper.updateByPrimaryKey(user);
            }else {
                i = loginBmo.saveUser(user);
            }
            if (i < 1){
                return RestResult.error("保存用户信息失败！");
            }
            hotDataStore.set(session.getId() + "_userInfo",user);
            StpUtil.setLoginId(user.getUser_id());
            boolean login = StpUtil.isLogin();
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            // 类似查询API还有：
            String loginIdAsString = StpUtil.getLoginIdAsString();// 获取当前会话登录id, 并转化为`String`类型
            int loginIdAsInt = StpUtil.getLoginIdAsInt();// 获取当前会话登录id, 并转化为`int`类型
            long loginIdAsLong = StpUtil.getLoginIdAsLong();// 获取当前会话登录id, 并转化为`long`类型
            Map<String,Object> tokenMap = new HashMap<>();
            tokenMap.put("tokenName",tokenInfo.getTokenName());
            tokenMap.put("tokenValue",tokenInfo.getTokenValue());
            return RestResult.success("保存用户信息成功",tokenMap);
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            return RestResult.error("登录异常！");
        }
    }

}
