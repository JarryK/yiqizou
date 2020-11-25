package com.web.Dao;

import com.web.Bean.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>com.web.Dao.LoginDao<br>
 * <b>创建人：</b><br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-04-15 13:35<br>
 */
@Service("com.web.Dao.LoginDao")
public interface LoginDao {
    /**
     * 保存登录信息
     * @param user
     * @return
     */
    public int save(User user);

}
