package com.web.Bmo;

import com.web.Bean.User;

import java.util.Map;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.Bmo<br>
 * <b>创建人：</b>DadaYu<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020/11/24 22:48<br>
 */
public interface LoginBmo {

    /**
     * 保存用户登录信息
     * @param user
     * @return
     */
    Map<String,Object> saveUser(User user);
}
