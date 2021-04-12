package com.web.service.impl;

import com.web.file.model.UserFile;
import com.web.file.service.UserFileService;
import com.web.mapper.RegisterMapper;
import com.web.model.Register;
import com.web.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class RegisterServiceImpl implements RegisterService {

    private final RegisterMapper mapper;
    private final UserFileService userFileService;
    @Override
    public int insert(Register o) throws Exception {
        o.setUpDataTime(new Date());
        o.setCreateTime(new Date());
        return mapper.insert(o);
    }

    @Override
    public int delete(Register o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int update(Register o) {
        o.setUpDataTime(new Date());
        return mapper.updateByPrimaryKey(o);
    }

    @Override
    public Register selectById(long id) {
        Register o = new Register();
        o.setRegisterId(id);
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public List<Register> selectAll() {
        return mapper.selectAll();
    }

    public Register selectByUId(long id){
        Example example = new Example(Register.class);
        example.createCriteria().andEqualTo("userId",id);
        example.createCriteria().andEqualTo("registerStatus",1);
        return mapper.selectByExample(example).get(0);
    }

    @Override
    public String saveRegisterPhoto(MultipartFile file) throws Exception{
        UserFile userFile = userFileService.uploadFile(file);
        return userFileService.getImgBase64(userFile.getId());
    }
}
