package com.web.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.Bean.User;
import com.web.Bmo.LoginBmo;
import com.web.Utils.C;
import com.web.Utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>java.com.web.Controller<br>
 * <b>创建人：</b><br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020/11/23 23:07<br>
 */
@RestController
@RequestMapping("/yqz/login")
public class LoginController extends BaseController {

    @Resource(name = "com.web.Bmo.LoginBmoImpl")
    private LoginBmo loginBmo;

    @ResponseBody
    @RequestMapping("/check")
    public Map<String, Object> check(@RequestBody  Map<String, Object> inMap, HttpSession session) {
        Map<String,Object> returnMap  = C.page.returnMap(false, "登录失败");
        try {
            log.info("inMap:" + JSON.toJSONString(inMap));
            Map<String,Object> userInfo = MyUtils.map.getMap(inMap,"userInfo", new LinkedHashMap());
            // LinkedHashMap转实列对象
//            User user = JSON.parseObject(JSON.toJSONString(userInfo), new TypeReference<User>() { });
            String nickName = MyUtils.map.getString(userInfo,"nickName");
            int gender = (int) MyUtils.map.getObject(userInfo,"gender");
            String language = MyUtils.map.getString(userInfo,"language");
            String city = MyUtils.map.getString(userInfo,"city");
            String province = MyUtils.map.getString(userInfo,"province");
            String country = MyUtils.map.getString(userInfo,"country");
            String avatarUrl = MyUtils.map.getString(userInfo,"avatarUrl");
            User user = new User();
            user.setNickName(nickName);
            user.setGender(gender);
            user.setLanguage(language);
            user.setCity(city);
            user.setProvince(province);
            user.setCountry(country);
            user.setAvatarUrl(avatarUrl);
            Map<String,Object> resultMap = loginBmo.saveUser(user);
            if (!C.bmo.returnMapBoolean(resultMap)){
                String msg = MyUtils.map.getString(resultMap,"msg");
                returnMap = C.page.returnMap(false, "保存用户信息失败！" + msg);
                return returnMap;
            }
            returnMap  = C.page.returnMap(true, "ok");
            return returnMap;
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            returnMap = C.page.returnMap(false, "登录异常！");
            return returnMap;
        }
    }

}
