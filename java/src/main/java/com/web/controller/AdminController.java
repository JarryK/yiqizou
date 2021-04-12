package com.web.controller;

import cn.hutool.core.lang.Validator;
import com.web.base.RestResult;
import com.web.mapper.AdminMapper;
import com.web.model.Admin;
import com.web.model.qo.AdminSignUp;
import com.web.security.model.qo.AuthUserQo;
import com.web.security.service.IUserSecurityService;
import com.web.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/admin")
@Api(tags = "管理员登录相关操作")
public class AdminController {

    private final AdminMapper mapper;
    private final AdminService service;
    private final IUserSecurityService userSecurityService;

    @ApiOperation("登录")
    @ResponseBody
    @PostMapping("/signIn")
    public RestResult<Object> append(@Validated @RequestBody AuthUserQo info, HttpSession session) throws Exception {
        String username = info.getUsername();
        String key = "";
        if (Validator.isMobile(username)) {
            key = "phone";
        } else {
            key = "code";
        }
        Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo(key, username);
        List<Admin> list = mapper.selectByExample(example);
        if (list.size() < 1) {
            return RestResult.error("用户不存在");
        }
        info.setUsername(String.valueOf(list.get(0).getId()));
        return RestResult.success("登录成功", service.login(info));
    }

    @ApiOperation("注册")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/signUp")
    public RestResult<Object> signUp(@Validated @RequestBody AdminSignUp info, HttpSession session) throws Exception {
        if (!Validator.isMobile(info.getPhone())){
            return RestResult.error("手机格式不正确");
        }
        Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo("code", info.getCode());
        List<Admin> list = mapper.selectByExample(example);
        if (list.size() > 0) {
            return RestResult.error("code已存在");
        }
        example = new Example(Admin.class);
        example.createCriteria().andEqualTo("phone",info.getPhone());
        list = mapper.selectByExample(example);
        if (list.size() > 0) {
            return RestResult.error("绑定电话已存在");
        }
        return RestResult.success("注册成功", service.insert(info,info.getPassword()));
    }

}
