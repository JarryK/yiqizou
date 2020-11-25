package com.yiqizou;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.web.Utils.MyUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void contextLoads() {
        String s = String.valueOf(new Date().getTime());
        s = s.substring(0,6);
        System.out.println(MyUtils.getRandomNuber(20));
        System.out.println(MyUtils.getRandomTableId(20));
    }


}
