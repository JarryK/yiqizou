package com.yiqizou;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.web.Bean.User;
import com.web.Bmo.LoginBmo;
import com.web.Dao.LoginDao;
import com.web.Utils.MyUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import java.util.Date;

@SpringBootTest
class YiziqouApplicationTests {

    @Resource
    private static LoginDao loginBmo;

    public static void main(String[] args) {


            // 这个文件夹是测试文件
        // 工程类的测试内容可以单独到这个地方进行测试
        System.out.println("123123,阿秋是个小傻子");
        // 运行的时候会编译工程，所以工程内的service层，dao层，工具类等都可以调用
        User user = new User();
        user.setUser_id(Long.valueOf(12312312));
        setLoginBmo(user);
    }

    public static void setLoginBmo(User user) {
        loginBmo.save(user);
    }

    @Test
    void contextLoads() {
        String s = String.valueOf(new Date().getTime());
        s = s.substring(0,6);
        System.out.println(MyUtils.getRandomNuber(20));
        System.out.println(MyUtils.getRandomTableId(20));
    }


}
