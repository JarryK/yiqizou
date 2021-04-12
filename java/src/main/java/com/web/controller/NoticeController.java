package com.web.controller;

import cn.hutool.core.lang.Validator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.base.Page;
import com.web.base.RestResult;
import com.web.mapper.NoticeMapper;
import com.web.model.Notice;
import com.web.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/note")
@Api(tags = "公告相关操作")
public class NoticeController {

    private final NoticeMapper mapper;
    private final NoticeService service;

    @ApiOperation("添加")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/append")
    public RestResult<Object> append (Notice info, HttpServletRequest request){
        if (!Validator.isNotNull(info.getNoticeTitle())){
            return RestResult.error("noticeTitle: 公告标题不可为空");
        }
        if (!Validator.isNotNull(info.getNoticeDetail())){
            return RestResult.error("noticeDetail: 公告内容不可为空");
        }
        if (!Validator.isNotNull(info.getNoticePublisher())){
            return RestResult.error("noticePublisher: 发布人不可为空");
        }
        int i = service.insert(info);
        if (i < 1){
            return RestResult.error("新增失败");
        }
        return RestResult.success("新增成功");
    }


    @ApiOperation("更新")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/upData")
    public RestResult<Object> upData(@RequestBody Notice info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getNoticeId()) || info.getNoticeId() == 0){
            return RestResult.error("noticeId: 公告ID不能为空");
        }
        Notice check = new Notice();
        check.setNoticeId(info.getNoticeId());
        Notice notice = mapper.selectByPrimaryKey(check);
        if (!Validator.isNotNull(notice)) {
            return RestResult.error("未查询到数据");
        }
        if (Validator.isNotNull(info.getNoticePublisher())){
            notice.setNoticePublisher(info.getNoticePublisher());
        }
        if (Validator.isNotNull(info.getNoticeTitle())){
            notice.setNoticeTitle(info.getNoticeTitle());
        }
        if (Validator.isNotNull(info.getNoticeDetail())){
            notice.setNoticeDetail(info.getNoticeDetail());
        }
        int i = service.update(info);
        if (i < 1){
            return RestResult.error("更新失败");
        }
        return RestResult.success("更新成功");
    }

    @ApiOperation("删除")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/del")
    public RestResult<Object> del(@RequestBody Notice info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getNoticeId()) || info.getNoticeId() == 0){
            return RestResult.error("noticeId: 公告ID不能为空");
        }
        Notice m = service.select(info.getNoticeId());
        if (!Validator.isNotNull(m)){
            return RestResult.error("未查询数据");
        }
        int i = service.delete(m);
        if (i < 1){
            return RestResult.error("删除失败");
        }
        return RestResult.success("删除成功");
    }

    @ApiOperation("查询")
    @ResponseBody
    @PostMapping("/qry")
    public RestResult<Object> qry(@RequestBody Page<Notice> info, HttpServletRequest request) throws Exception {
        Example example = returnQueryExample(info.getQueryObj());
        example.orderBy("crateTime").desc();
        // 使用分页插件进行分页，只对查询结果判断是否存在，返回分页信息
        com.github.pagehelper.Page<Notice> page = PageHelper.startPage(info.getCurrPage() == 0 ? 1 : info.getCurrPage(),info.getPageSize() == 0 ? 10:info.getPageSize());
        List<Notice> list = mapper.selectByExample(example);
        PageInfo<Notice> pageInfo =  new PageInfo<>(page.getResult());
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",pageInfo);
    }

    @ApiOperation("单个查询")
    @ResponseBody
    @PostMapping("/qryOne")
    public RestResult<Object> qry(@RequestBody Notice info, HttpServletRequest request) throws Exception {
        Example example = returnQueryExample(info);
        List<Notice> o = mapper.selectByExample(example);
        if (!Validator.isNotNull(o)){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",o.get(0));
    }

    /**
     * 查询 Example
     * @param notice
     * @return
     */
    public Example returnQueryExample(Notice notice){
        Example example = new Example(Notice.class);
        Example.Criteria criteria = example.createCriteria();
        if (Validator.isNotNull(notice)){
            if (Validator.isNotNull(notice.getNoticeId()) && notice.getNoticeId() != 0){
                criteria.andEqualTo("noticeId",notice.getNoticeId());
            }
            if (Validator.isNotNull(notice.getNoticeTitle())){
                criteria.andLike("noticeTitle","%" + notice.getNoticeTitle() + "%");
            }
            if (Validator.isNotNull(notice.getNoticeDetail())){
                criteria.andLike("noticeDetail","%" + notice.getNoticeDetail() + "%");
            }
            if (Validator.isNotNull(notice.getNoticePublisher())){
                criteria.andLike("noticePublisher","%" + notice.getNoticePublisher() + "%");
            }
        }
        return example;
    }
}
