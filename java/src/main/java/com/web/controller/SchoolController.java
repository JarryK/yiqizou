package com.web.controller;

import cn.hutool.core.lang.Validator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.base.Page;
import com.web.base.RestResult;
import com.web.mapper.SchoolMapper;
import com.web.model.Confirm;
import com.web.model.School;
import com.web.service.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/school")
@Api(tags = "学校相关操作")
public class SchoolController {

    private final SchoolMapper mapper;
    private final SchoolService service;

    @ApiOperation("新增")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/append")
    public RestResult<Object> append(@RequestBody School info, HttpSession session) throws Exception {
        if (!Validator.isNotNull(info.getSchoolName())){
            return RestResult.error("schoolName：学校名不能为空");
        }
        int i = service.insert(info);
        if (i < 1){
            return RestResult.error("创建失败");
        }
        return RestResult.success("创建成功");
    }

    @ApiOperation("删除信息")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/remove")
    public RestResult<Object> remove(@RequestBody School info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getSchoolId()) || info.getSchoolId() == 0){
            return RestResult.error("schoolId：学校Id不能为空");
        }
        School school = service.selectById(info.getSchoolId());
        if (!Validator.isNotNull(school)){
            return RestResult.error("信息不存在");
        }
        int i = service.delete(school);
        if (i < 1){
            return RestResult.error("删除失败");
        }
        return RestResult.success("删除成功");
    }

    @ApiOperation("更新学校名")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/upName")
    public RestResult<Object> upStatus(@RequestBody School info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getSchoolId()) || info.getSchoolId() == 0){
            return RestResult.error("schoolId：学校Id不能为空");
        }
        if (!Validator.isNotNull(info.getSchoolName())){
            return RestResult.error("schoolName：学校名不能为空");
        }
        School school = service.selectById(info.getSchoolId());
        if (!Validator.isNotNull(school)){
            return RestResult.error("信息不存在");
        }
        school.setSchoolName(info.getSchoolName());
        int i = service.update(school);
        if (i < 1){
            return RestResult.error("更新失败");
        }
        return RestResult.success("更新成功");
    }

    @ApiOperation("分页查询")
    @ResponseBody
    @PostMapping("/qry")
    public RestResult<Object> qry(@RequestBody Page<School> info, HttpServletRequest request) throws Exception {
        Example example = returnQueryExample(info.getQueryObj());
        // 按照创建时间排序
        example.orderBy("createTime").desc();
        com.github.pagehelper.Page<School> page = PageHelper.startPage(info.getCurrPage() == 0 ? 1 : info.getCurrPage(),info.getPageSize() == 0 ? 10:info.getPageSize());
        List<School> list = mapper.selectByExample(example);
        PageInfo<School> pageInfo =  new PageInfo<>(page.getResult());
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",pageInfo);
    }

    @ApiOperation("单个查询")
    @ResponseBody
    @PostMapping("/qryOne")
    public RestResult<Object> qryOne(@RequestBody School info, HttpServletRequest request) throws Exception {
        Example example = returnQueryExample(info);
        List<School> list = mapper.selectByExample(example);
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",list.get(0));
    }

    /**
     * 查询 Example
     * @param school
     * @return
     */
    public Example returnQueryExample(School school){
        Example example = new Example(Confirm.class);
        Example.Criteria criteria = example.createCriteria();
        if (Validator.isNotNull(school)){
            if (Validator.isNotNull(school.getSchoolId()) && school.getSchoolId() != 0){
                criteria.andEqualTo("schoolId",school.getSchoolId());
            }
            if (Validator.isNotNull(school.getCreateTime())){
                criteria.andEqualTo("schoolName", "%" + school.getSchoolName() + "%");
            }
        }
        return example;
    }

}
