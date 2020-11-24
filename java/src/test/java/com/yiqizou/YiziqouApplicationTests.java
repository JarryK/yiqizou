package com.yiqizou;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
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

@SpringBootTest
class YiziqouApplicationTests {

    @Test
    void contextLoads() {

        String encryptedData = "u8HzkFTvsO++nUyOIzALL0J91GAbGOVtQZzZnPiT6aq4CrO/zW6lSRcZsy/UB9XiVCeE+FSMR0Fr27hqxJO9p275BbcINhV+69YfX/Ix9YVqMBFssh8qUTP0ouBwsuCOZRY5Clzh0m9AOAsvmv9LSRDPFPPmAVHudHX9H4pdwkwbeTZbkV7JIp/LSRKRxuBWJndCJmoGosnkjFo/Tz8L5yB2+RjiUACP5RN3jQqtkK7zB9YL7mk4da6051pEf+T027qvMzBVV4njD8h1P6Bnz9U6CwofDJpiIKs4jnTSSWtFxAVfEZpfrwlMVAy9z+WYgw2y6q6KskESNXHwUNihuhRptuqlxwnoiRjOuwuqcIDxcctqVznRHSyIbTe8qPFNq5+UeOL4EnlUFLrNka/FJZKtRKp4LxFbAOCTVBKPyKNMIJdpWcyPz8NVTWVdAa62/uib5ZjCrcP2kF2i8Xi84GlODkpuhKX8hXhdZYbbcyh5ZRa5Dvpcv4IqjJMY8TBx";
        String sessionKey ="0315CFFa1rC42A0XHyFa1qMoMD05CFFF";
        String iv = "E0+81H8xL7ELyW2NLL3fcA==";
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
