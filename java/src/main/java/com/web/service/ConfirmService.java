package com.web.service;

import com.web.model.Confirm;

import java.util.List;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.service<br>
 * <b>创建人：</b>DadaYu<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2021/3/28 14:33<br>
 */
public interface ConfirmService {

    public int insert(Confirm o) throws Exception;

    public int delete(Confirm o);

    /**
     * 删除订单确认信息
     * @param id
     * @return
     */
    public int deleteByOrderId(long id);

    public int update(Confirm o);


    /**
     * 查询订单确认信息
     * @param id
     * @return
     */
    public List<Confirm> selectByOrderId(long id);
    /**
     * 查询订单确认数量
     * @param id
     * @return
     */
    public int selectCountByOrderId(long id);

    public Confirm selectById(long id);

    /**
     * 查询用户的订单确认信息
     * @param id
     * @return
     */
    public List<Confirm> selectByUid(long id);

    public List<Confirm> selectAll();
}
