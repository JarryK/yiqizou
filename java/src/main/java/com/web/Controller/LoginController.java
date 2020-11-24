package com.web.Controller;

import com.alibaba.fastjson.JSON;
import com.web.Utils.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
public class LoginController {
    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @RequestMapping("/check")
    public Map<String, Object> check(@RequestBody  Map<String, Object> inMap, HttpSession session) {
        try {
            log.info("inMap:" + JSON.toJSONString(inMap));
            return C.page.returnMap(true, "ok");
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            return C.page.returnMap(false, "登录异常！");
        }
    }

}
