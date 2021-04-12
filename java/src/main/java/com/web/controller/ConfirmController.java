package com.web.controller;

import cn.hutool.core.lang.Validator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.base.Page;
import com.web.base.RestResult;
import com.web.mapper.ConfirmMapper;
import com.web.mapper.OrderMapper;
import com.web.model.Confirm;
import com.web.model.Order;
import com.web.model.User;
import com.web.model.qo.ConfirmAppendQo;
import com.web.security.jwt.JwtTokenUtils;
import com.web.service.ConfirmService;
import com.web.service.OrderService;
import com.web.service.ScoreService;
import com.web.service.UserService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/confirm")
@Api(tags = "订单确认信息相关操作")
public class ConfirmController {

    private final JwtTokenUtils jwtTokenUtils;

    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final UserService userService;
    private final ConfirmMapper mapper;
    private final ConfirmService service;
    private final ScoreService scoreService;

    @ApiOperation("新增确认信息")
    @ResponseBody
    @PostMapping("/append")
    public RestResult<Object> append(@Validated @RequestBody ConfirmAppendQo info, HttpSession session) throws Exception {
        if (info.getOrderId() == 0){
            return RestResult.error("orderId：订单Id不能为空");
        }
        if (info.getUserId() == 0){
            return RestResult.error("userId：用户id不能为空");
        }
        Order order = orderService.selectById(info.getOrderId());
        if (!Validator.isNotNull(order)){
            return RestResult.error("订单不存在");
        }
        User user = userService.selectById(info.getUserId());
        if (!Validator.isNotNull(user)){
            return RestResult.error("用户不存在");
        }
        if (order.getPeopleNumber() - order.getPeopleNumberCheck() < 1){
            return RestResult.error("订单人数已满");
        }
        Confirm confirm = new Confirm();
        confirm.setUserId(user.getUserId());
        confirm.setOrderId(order.getOrderId());
        confirm.setStatus(1);
        int i = service.insert(confirm);
        if (i < 1){
           return RestResult.error("创建失败");
        }
        // 更新订单确认人数
        order.setPeopleNumberCheck(order.getPeopleNumberCheck() + 1);
        orderService.update(order);
        return RestResult.success("创建成功");
    }

    @ApiOperation("删除订单确认信息")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/remove")
    public RestResult<Object> remove(@RequestBody Confirm info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getConfirmId()) || info.getConfirmId() == 0){
            return RestResult.error("confirmId：流水Id不能为空");
        }
        Order order = orderService.selectById(info.getOrderId());
        if (!Validator.isNotNull(order)){
            return RestResult.error("订单不存在");
        }
        Confirm confirm = service.selectById(info.getConfirmId());
        if (!Validator.isNotNull(confirm)){
            return RestResult.error("确认信息不存在");
        }
        if (order.getOrderStatus() == 1 || order.getOrderStatus() == 2){
            order.setPeopleNumberCheck(order.getPeopleNumberCheck() - 1);
            orderService.update(order);
        }
        int i = service.delete(confirm);
        if (i < 1){
            return RestResult.error("删除失败");
        }
        return RestResult.success("删除成功");
    }

    @ApiOperation("更新确认信息状态")
    @ResponseBody
    @PostMapping("/upStatus")
    public RestResult<Object> upStatus(@RequestBody Confirm info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getConfirmId()) || info.getConfirmId() == 0){
            return RestResult.error("confirmId：流水Id不能为空");
        }
        if (!Validator.isNotNull(info.getStatus()) || info.getStatus() == 0){
            return RestResult.error("status：确认状态不能为空");
        }
        String userName = jwtTokenUtils.getTokenUserName(request);
        User user = userService.selectById(Long.parseLong(userName));
        Confirm confirm = service.selectById(info.getConfirmId());
        if (!Validator.isNotNull(confirm)){
            return RestResult.error("确认信息不存在");
        }
        if (confirm.getUserId() != user.getUserId()){
            return RestResult.error("不可更改他人信息");
        }
        if (confirm.getStatus() != 1){
            return RestResult.error("当前状态不能更改");
        }
        Order order = orderService.selectById(confirm.getOrderId());
        if (!Validator.isNotNull(order)){
            return RestResult.error("订单不存在");
        }
        if (order.getOrderStatus() == 3 || order.getOrderStatus() == 4){
            return RestResult.error("当前订单已结束");
        }
        confirm.setStatus(info.getStatus());
        int i = service.update(confirm);
        if (i < 1){
           return RestResult.error("更新失败");
        }
        return RestResult.success("更新成功");
    }

    @ApiOperation("更新确认信息状态")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/upStatusAdmin")
    public RestResult<Object> upStatusAdmin(@RequestBody Confirm info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getConfirmId()) || info.getConfirmId() == 0){
            return RestResult.error("confirmId：流水Id不能为空");
        }
        if (!Validator.isNotNull(info.getStatus()) || info.getStatus() == 0){
            return RestResult.error("status：确认状态不能为空");
        }
        Confirm confirm = service.selectById(info.getConfirmId());
        if (!Validator.isNotNull(confirm)){
            return RestResult.error("确认信息不存在");
        }
        if (confirm.getStatus() != 1){
            return RestResult.error("当前状态不能更改");
        }
        Order order = orderService.selectById(confirm.getOrderId());
        if (!Validator.isNotNull(order)){
            return RestResult.error("订单不存在");
        }
        confirm.setStatus(info.getStatus());
        int i = service.update(confirm);
        if (i < 1){
            return RestResult.error("更新失败");
        }
        return RestResult.success("更新成功");
    }

    @ApiOperation("分页查询")
    @ResponseBody
    @PostMapping("/qry")
    public RestResult<Object> qry(@RequestBody Page<Confirm> info, HttpServletRequest request) throws Exception {
        Confirm confirm = info.getQueryObj();
        String tokenUserName = jwtTokenUtils.getTokenUserName(request);
        confirm.setUserId(Long.parseLong(tokenUserName));
        Example example = returnQueryExample(confirm);
        // 按照创建时间排序
        example.orderBy("createTime").desc();
        com.github.pagehelper.Page<Confirm> page = PageHelper.startPage(info.getCurrPage() == 0 ? 1 : info.getCurrPage(),info.getPageSize() == 0 ? 10:info.getPageSize());
        List<Confirm> list = mapper.selectByExample(example);
        PageInfo<Confirm> pageInfo =  new PageInfo<>(page.getResult());
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",pageInfo);
    }

    @ApiOperation("单个查询")
    @ResponseBody
    @PostMapping("/qryOne")
    public RestResult<Object> qryOne(@RequestBody Confirm info, HttpServletRequest request) throws Exception {
        String tokenUserName = jwtTokenUtils.getTokenUserName(request);
        info.setUserId(Long.parseLong(tokenUserName));
        Example example = returnQueryExample(info);
        List<Confirm> list = mapper.selectByExample(example);
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",list.get(0));
    }

    @ApiOperation("分页查询")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/qryAdmin")
    public RestResult<Object> qryAdmin(@RequestBody Page<Confirm> info, HttpServletRequest request) throws Exception {
        Example example = returnQueryExample(info.getQueryObj());
        // 按照创建时间排序
        example.orderBy("createTime").desc();
        com.github.pagehelper.Page<Confirm> page = PageHelper.startPage(info.getCurrPage() == 0 ? 1 : info.getCurrPage(),info.getPageSize() == 0 ? 10:info.getPageSize());
        List<Confirm> list = mapper.selectByExample(example);
        PageInfo<Confirm> pageInfo =  new PageInfo<>(page.getResult());
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",pageInfo);
    }

    @ApiOperation("单个查询")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/qryOneAdmin")
    public RestResult<Object> qryOneAdmin(@RequestBody Confirm info, HttpServletRequest request) throws Exception {
        Example example = returnQueryExample(info);
        List<Confirm> list = mapper.selectByExample(example);
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",list.get(0));
    }

    /**
     * 订单查询 Example
     * @param confirm
     * @return
     */
    public Example returnQueryExample(Confirm confirm){
        Example example = new Example(Confirm.class);
        Example.Criteria criteria = example.createCriteria();
        if (Validator.isNotNull(confirm)){
            if (Validator.isNotNull(confirm.getOrderId()) && confirm.getOrderId() != 0){
                criteria.andEqualTo("orderId",confirm.getOrderId());
            }
            if (Validator.isNotNull(confirm.getUserId()) && confirm.getUserId() != 0){
                criteria.andEqualTo("userId",confirm.getUserId());
            }
            if (Validator.isNotNull(confirm.getConfirmId()) && confirm.getConfirmId() != 0){
                criteria.andEqualTo("confirmId",confirm.getConfirmId());
            }
            if (Validator.isNotNull(confirm.getStatus()) && confirm.getStatus() != 0){
                criteria.andEqualTo("status",confirm.getStatus());
            }
        }
        return example;
    }

}
