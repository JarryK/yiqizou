package com.web.service;

import com.web.model.School;

import java.util.List;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.service<br>
 * <b>创建人：</b>DadaYu<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2021/3/28 14:34<br>
 */
public interface SchoolService {

    public int insert(School o) throws Exception;

    public int delete(School o);

    public int update(School o);

    public School selectById(long id);

    public List<School> selectAll();
}
