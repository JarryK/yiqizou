package com.web.controller;

import cn.hutool.core.lang.Validator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.base.Page;
import com.web.base.RestResult;
import com.web.hotdata.HotDataStore;
import com.web.mapper.RegisterMapper;
import com.web.model.Confirm;
import com.web.model.Register;
import com.web.model.User;
import com.web.model.qo.RegisterAppendQo;
import com.web.security.jwt.JwtTokenUtils;
import com.web.service.RegisterService;
import com.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/register")
@Api(tags = "认证相关操作")
public class RegisterController {

    private final JwtTokenUtils jwtTokenUtils;
    private final HotDataStore hotDataStore;

    private final RegisterMapper mapper;
    private final RegisterService service;
    private final UserService userService;

    @ApiOperation("新增认证信息")
    @ResponseBody
    @PostMapping("/append")
    public RestResult<Object> append(@Validated @RequestBody RegisterAppendQo info, HttpServletRequest request) throws Exception {
           String tokenUserName = jwtTokenUtils.getTokenUserName(request);
           User user = (User) hotDataStore.get(tokenUserName + "_info");
           Register register = service.selectByUId(user.getUserId());
           if (Validator.isNotNull(register)){
               return RestResult.error("已经提交认证申请或认证完成");
           }
           register = new Register();
           register.setRegisterManId(user.getUserId());
           register.setRegisterMan(user.getRealName());
           register.setRegisterPhoto1(info.getRegisterPhoto1());
           register.setRegisterPhoto2(info.getRegisterPhoto2());
           register.setRegisterPhoto3(info.getRegisterPhoto3());
           register.setRegisterStatus(1);
           int i = service.insert(register);
           if (i < 1){
               return RestResult.error("创建失败");
           }
           return RestResult.success("创建成功");
    }

    @ApiOperation("提交证件照片")
    @ResponseBody
    @PostMapping("/submitPhoto")
    public RestResult<Object> submitPhoto(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        return RestResult.success("提交成功",service.saveRegisterPhoto(file));
    }

    @ApiOperation("审批认证信息")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/approval")
    public RestResult<Object> approval(@RequestBody Register info, HttpServletRequest request) throws Exception {
           if (!Validator.isNotNull(info.getRegisterId()) || info.getRegisterId() == 0){
               return RestResult.error("registerId：认证流水id不能为空");
           }
           if (!Validator.isNotNull(info.getRegisterStatus()) || info.getRegisterStatus() == 0){
               return RestResult.error("registerStatus：认证状态不能为空");
           }
           Register register = service.selectById(info.getRegisterId());
           if (!Validator.isNotNull(register)){
               return RestResult.error("认证信息不存在");
           }
           if (register.getRegisterStatus() != 1){
               return RestResult.error("不能重复审批");
           }
           register.setRegisterStatus(info.getRegisterStatus());
           int i = service.update(register);
           if (i < 1){
               return RestResult.error("更新失败");
           }
           // 认证通过更新用户认证状态
           if (info.getRegisterStatus() == 1){
               User user = userService.selectById(register.getRegisterManId());
               user.setRegisterStatus(2);
               userService.update(user);
           }
           return RestResult.success("更新成功");
    }

    @ApiOperation("分页查询")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/qry")
    public RestResult<Object> qry(@RequestBody Page<Register> info,HttpServletRequest request) throws Exception{
        Example example = returnQueryExample(info.getQueryObj());
        // 按照创建时间排序
        example.orderBy("createTime").desc();
        com.github.pagehelper.Page<Register> page = PageHelper.startPage(info.getCurrPage() == 0 ? 1 : info.getCurrPage(),info.getPageSize() == 0 ? 10:info.getPageSize());
        List<Register> list = mapper.selectByExample(example);
        PageInfo<Register> pageInfo =  new PageInfo<>(page.getResult());
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",pageInfo);
    }

    @ApiOperation("单个查询")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/qryOneAdmin")
    public RestResult<Object> qryOneAdmin(@RequestBody Register info,HttpServletRequest request) throws Exception{
        Example example = returnQueryExample(info);
        // 按照创建时间排序
        List<Register> list = mapper.selectByExample(example);
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",list.get(0));
    }

    @ApiOperation("单个查询")
    @ResponseBody
    @PostMapping("/qryOne")
    public RestResult<Object> qryOne(@RequestBody Register info,HttpServletRequest request) throws Exception{
        Example example = returnQueryExample(info);
        String tokenUserName = jwtTokenUtils.getTokenUserName(request);
        example.createCriteria().andEqualTo("userId",tokenUserName);
        // 按照创建时间排序
        List<Register> list = mapper.selectByExample(example);
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",list.get(0));
    }

    /**
     * 查询 Example
     * @param register
     * @return
     */
    public Example returnQueryExample(Register register){
        Example example = new Example(Confirm.class);
        Example.Criteria criteria = example.createCriteria();
        if (Validator.isNotNull(register)){
            if (Validator.isNotNull(register.getRegisterId()) && register.getRegisterId() != 0){
                criteria.andEqualTo("registerId",register.getRegisterId());
            }
            if (Validator.isNotNull(register.getRegisterManId()) && register.getRegisterManId() != 0){
                criteria.andEqualTo("userId",register.getRegisterManId());
            }
            if (Validator.isNotNull(register.getRegisterStatus()) && register.getRegisterStatus() != 0){
                criteria.andEqualTo("registerStatus",register.getRegisterStatus());
            }
            if (Validator.isNotNull(register.getRegisterMan())){
                criteria.andEqualTo("registerMan", "%" + register.getRegisterMan() + "%");
            }
        }
        return example;
    }
}
