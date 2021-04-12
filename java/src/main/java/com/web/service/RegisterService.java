package com.web.service;

import com.web.model.Register;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RegisterService {

    public int insert(Register o) throws Exception;

    public int delete(Register o);

    public int update(Register o);

    public Register selectById(long id);

    public List<Register> selectAll();

    /**
     * 查询有无在认证中的记录
     * @param id
     * @return
     */
    public Register selectByUId(long id);

    /**
     * 保存文件并返回base64
     * @param file
     * @return
     * @throws Exception
     */
    public String saveRegisterPhoto(MultipartFile file) throws Exception;
}
