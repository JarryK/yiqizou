package com.web.Bmo;

import com.web.Bean.User;
import com.web.Dao.LoginDao;
import com.web.Utils.C;
import com.web.Utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.Bmo<br>
 * <b>创建人：</b>DadaYu<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020/11/24 22:49<br>
 */
@Service("com.web.Bmo.LoginBmoImpl")
public class LoginBmoImpl implements LoginBmo {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("com.web.Dao.LoginDao")
    protected LoginDao loginDao;

    @Override
    public Map<String, Object> saveUser(User user) {
        Map<String,Object> returnMap = new HashMap<>();
        try{
            Long userID = MyUtils.getRandomTableId(20);
            user.setUser_id(userID);
            user.setCreate_time(new Date());
            user.setUpData_time(new Date());
            int i = loginDao.save(user);
//            if (i<0){
//                returnMap = C.bmo.returnMap(false,"保存登录信息失败");
//                return returnMap;
//            }
            returnMap = C.bmo.returnMap(true,"保存登录信息成功");
            return returnMap;
        }catch (Exception e){
            log.debug("保存登录信息异常",e);
            returnMap = C.bmo.returnMap(false,"保存登录信息异常");
            return returnMap;
        }
    }
}
