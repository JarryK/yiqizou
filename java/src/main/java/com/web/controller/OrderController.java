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
import com.web.model.qo.OrderAppendQo;
import com.web.model.qo.OrderQueryQo;
import com.web.security.jwt.JwtTokenUtils;
import com.web.service.ConfirmService;
import com.web.service.OrderService;
import com.web.service.ScoreService;
import com.web.service.UserService;
import com.web.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/order")
@Api(tags = "订单相关操作")
public class OrderController {

    @Value("${order.time}")
    private static int time;

    private final JwtTokenUtils jwtTokenUtils;

    private final OrderMapper mapper;
    private final OrderService service;
    private final UserService userService;
    private final ConfirmMapper confirmMapper;
    private final ConfirmService confirmService;
    private final ScoreService scoreService;


    @ApiOperation("新增订单")
    @ResponseBody
    @PostMapping("/append")
    public RestResult<Object> append(@Validated @RequestBody OrderAppendQo info, HttpSession session) throws Exception {
        if (info.getCreateManId() == 0){
            return RestResult.error("createManId：创建人Id不能为空");
        }
        if (info.getPeopleNumber() == 0){
            return RestResult.error("peopleNumber：计划人数不能为空");
        }
        User user = userService.selectById(info.getCreateManId());
        if (!Validator.isNotNull(user)){
            return RestResult.error("用户不存在");
        }
        List<Order> orderList = service.selectByUid(info.getCreateManId());
        if (Validator.isNotNull(orderList)){
            for (Order o:orderList) {
                if (o.getOrderStatus() == 1){
                    return RestResult.error("用户还有在途单未完成");
                }
            }
        }
        Date formatTime = DateUtil.timeStringToDate(info.getTime());
        Order order = new Order();
        order.setCreateMan(info.getCreateMan());
        order.setCreateManId(info.getCreateManId());
        order.setStartLocation(info.getStartLocation());
        order.setEndLocation(info.getEndLocation());
        order.setStartTime(formatTime);
        order.setRemark(info.getRemark());
        order.setPeopleNumber(info.getPeopleNumber());
        order.setPeopleNumberCheck(1);
        order.setOrderStatus(1);
        int i = service.insert(order);
        if (i < 1){
            return RestResult.success("创建失败");
        }
        Confirm confirm = new Confirm();
        confirm.setOrderId(order.getOrderId());
        confirm.setUserId(info.getCreateManId());
        confirmService.insert(confirm);
        return RestResult.success("创建成功");
    }

    @ApiOperation("删除")
    @ResponseBody
    @PostMapping("/remove")
    public RestResult<Object> remove(@RequestBody Order info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getOrderId()) || info.getOrderId() == 0){
            return RestResult.error("orderId：订单id不能为空");
        }
        String username = jwtTokenUtils.getTokenUserName(request);
        Order order = service.selectById(info.getOrderId());
        if (!Validator.isNotNull(order)){
            return RestResult.error("订单不存在");
        }
        if (!username.equals(String.valueOf(order.getCreateManId()))){
            return RestResult.error("不可删除他人订单");
        }
        return removeOrder(info,order);
    }

    /**
     * 删除订单操作
     * @param info 入参
     * @param order 订单信息
     * @return
     * @throws Exception
     */
    public RestResult<Object> removeOrder(Order info,Order order) throws Exception{
        int orderStatus = order.getOrderStatus();
        int a = 0;
        long t = 0;
        if (orderStatus == 1 || orderStatus == 2){
            // 订单加入人数多于三人扣除信用分 3分
            a =  confirmService.selectCountByOrderId(info.getOrderId());
            t = new Date().getTime() - order.getCreateTime().getTime();
            // 清除订单确认信息
            confirmService.deleteByOrderId(order.getOrderId());
        }
        int i = service.delete(order);
        if (i < 1){
            return RestResult.error("删除失败");
        }
        if (a > 3){
            userService.creditScoreDeduction(order.getCreateManId(),3);
            return RestResult.success("删除成功，订单人数超过3人扣除信用分3分");
        }
        if (t > time * 60 * 1000){
            userService.creditScoreDeduction(order.getCreateManId(),1);
            return RestResult.success("删除成功，订单超时扣除信用分1分");
        }
        return RestResult.success("删除成功");
    }

    @ApiOperation("管理员删除")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/removeAdmin")
    public RestResult<Object> removeAdmin(@RequestBody Order info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getOrderId()) || info.getOrderId() == 0){
            return RestResult.error("orderId：订单id不能为空");
        }
        Order order = service.selectById(info.getOrderId());
        if (!Validator.isNotNull(order)){
            return RestResult.error("订单不存在");
        }
        int i = service.delete(order);
        if (i < 1){
            return RestResult.success("创建失败");
        }
        return RestResult.success("删除成功");
    }

    @ApiOperation("更新状态")
    @ResponseBody
    @PostMapping("/upStatus")
    public RestResult<Object> upStatus(@RequestBody Order info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getOrderId()) || info.getOrderId() == 0){
            return RestResult.error("orderId：订单id不能为空");
        }
        if (!Validator.isNotNull(info.getOrderStatus()) || info.getOrderStatus() == 0){
            return RestResult.error("orderStatus：订单状态不能为空");
        }
        String username = jwtTokenUtils.getTokenUserName(request);
        Order order = service.selectById(info.getOrderId());
        if (!Validator.isNotNull(order)){
            return RestResult.error("订单不存在");
        }
        if (!username.equals(String.valueOf(order.getCreateManId()))){
            return RestResult.error("不可更改他人订单");
        }
        return upOrderStatus(info,order);
    }


    @ApiOperation("更新状态")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseBody
    @PostMapping("/upStatusAdmin")
    public RestResult<Object> upStatusAdmin(@RequestBody Order info, HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getOrderId()) || info.getOrderId() == 0){
            return RestResult.error("orderId：订单id不能为空");
        }
        if (!Validator.isNotNull(info.getOrderStatus()) || info.getOrderStatus() == 0){
            return RestResult.error("orderStatus：订单状态不能为空");
        }
        Order order = service.selectById(info.getOrderId());
        if (!Validator.isNotNull(order)){
            return RestResult.error("订单不存在");
        }
        return upOrderStatus(info,order);
    }

    /**
     * 订单状态更新操作
     * @param info 入参
     * @param order 订单
     * @return
     * @throws Exception
     */
    public RestResult<Object> upOrderStatus(Order info,Order order) throws Exception {
        if (order.getOrderStatus() == 3 || order.getOrderStatus() == 4){
            return RestResult.error("订单已结束或取消，不能更改");
        }
        long t1 = order.getCreateTime().getTime();
        long t2 = new Date().getTime() - t1;
        List<Confirm> confirms = confirmService.selectByOrderId(info.getOrderId());
        if (info.getOrderStatus() == 3){
            // 订单完成所有确认的用户加一分，未完成的扣一分
            for (Confirm c : confirms ) {
                switch (c.getStatus()){
                    case 1:
                        // 未到的扣分
                        userService.creditScoreDeduction(c.getUserId(),1);
                        break;
                    case 2:
                        // 非刷单， 加分
                        if (t2 > time * 60 * 1000){
                            userService.creditScoreBonus(c.getUserId(),1);
                            if (order.getCreateManId() == c.getUserId()){
                                // 订单完成时发起者多加一分信用分
                                userService.creditScoreBonus(order.getCreateManId(),1);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        if (info.getOrderStatus() == 4){
            boolean flag = false;
            // 订单人数大于三人扣3分
            if (confirms.size() > 3){
                flag = true;
                userService.creditScoreDeduction(order.getCreateManId(),3);
            }
            // 超时取消订单扣1分，但不重复扣分
            if (t2 > time * 60 * 1000){
                if (!flag){
                    userService.creditScoreDeduction(order.getCreateManId(),1);
                }
            }
            // 清除订单确认信息
            confirmService.deleteByOrderId(order.getOrderId());
        }
        order.setOrderStatus(info.getOrderStatus());
        int i = service.update(order);
        if (i < 1){
            return RestResult.success("更新失败");
        }
        return RestResult.success("更新成功");
    }

    @ApiOperation("分页查询")
    @ResponseBody
    @PostMapping("/qry")
    public RestResult<Object> qry(@RequestBody Page<OrderQueryQo> info, HttpServletRequest request) throws Exception {
        OrderQueryQo order = info.getQueryObj();
        // 按照订单出发时间排序
        Example example = returnQueryExample(order);
        example.orderBy("startTime").desc();
        com.github.pagehelper.Page<Order> page = PageHelper.startPage(info.getCurrPage() == 0 ? 1 : info.getCurrPage(),info.getPageSize() == 0 ? 10:info.getPageSize());
        List<Order> list = mapper.selectByExample(example);
        PageInfo<Order> pageInfo =  new PageInfo<>(page.getResult());
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",pageInfo);
    }

    @ApiOperation("单个查询")
    @ResponseBody
    @PostMapping("/qryOne")
    public RestResult<Object> qryOne(@RequestBody OrderQueryQo info , HttpServletRequest request) throws Exception {
        List<Order> list = mapper.selectByExample(returnQueryExample(info));
        if (list.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",list.get(0));
    }


    /**
     * 订单查询 Example
     * @param order 订单查询参数类
     * @return
     */
    public Example returnQueryExample(OrderQueryQo order){
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        if (Validator.isNotNull(order)){
            if (Validator.isNotNull(order.getOrderId()) && order.getOrderId() != 0){
                criteria.andEqualTo("orderId",order.getOrderId());
            }
            if (Validator.isNotNull(order.getCreateManId()) && order.getCreateManId() != 0){
                criteria.andEqualTo("createManId",order.getCreateManId());
            }
            if (Validator.isNotNull(order.getOrderStatus()) && order.getOrderStatus() != 0){
                criteria.andEqualTo("orderStatus",order.getOrderStatus());
            }
            if (Validator.isNotNull(order.getMaxNum()) && order.getMaxNum() != 0){
                criteria.andLessThanOrEqualTo("orderId",order.getMaxNum());
            }
            if (Validator.isNotNull(order.getMinNum()) && order.getMinNum() != 0){
                criteria.andGreaterThanOrEqualTo("orderId",order.getMinNum());
            }
            if (Validator.isNotNull(order.getMaxCheckNum()) && order.getMaxCheckNum() != 0){
                criteria.andLessThanOrEqualTo("peopleNumberCheck",order.getMaxCheckNum());
            }
            if (Validator.isNotNull(order.getMinCheckNum()) && order.getMinCheckNum() != 0){
                criteria.andGreaterThanOrEqualTo("peopleNumberCheck",order.getMinCheckNum());
            }
            if (Validator.isNotNull(order.getCreateMan())){
                criteria.andLike("createMan", "%" + order.getCreateMan() + "%");
            }
            if (Validator.isNotNull(order.getStartLocation())){
                criteria.andLike("startLocation","%" + order.getStartLocation() + "%");
            }
            if (Validator.isNotNull(order.getEndLocation())){
                criteria.andLike("endLocation","%" + order.getEndLocation() + "%");
            }
            if (Validator.isNotNull(order.getMaxTime())){
                criteria.andLessThanOrEqualTo("startTime",order.getMaxTime());
            }
            if (Validator.isNotNull(order.getMinTime())){
                criteria.andGreaterThanOrEqualTo("startTime",order.getMinTime());
            }
        }
        return example;
    }

    @ApiOperation("查询订单用户列表")
    @ResponseBody
    @PostMapping("/qryOrderUser")
    public RestResult<Object> qryOrderUser(@RequestBody Order info , HttpServletRequest request) throws Exception {
        if (!Validator.isNotNull(info.getOrderId()) || info.getOrderId() == 0){
            return RestResult.error("orderId：订单Id不能为空");
        }
        Order order = service.selectById(info.getOrderId());
        if (!Validator.isNotNull(order)){
            return RestResult.error("订单信息不存在");
        }
        Map<String,Object> dataMap = new HashMap<>();
        int checkNum = 0;
        List<Map<String, Object>> mapList = service.selectOrderUserListByOId(info.getOrderId());
        for (Map<String, Object> m : mapList) {
            int status = (int) MapUtils.getNumber(m,"confirmStatus");
            if (status == 2){
                checkNum += 1;
            }
        }
        dataMap.put("list",mapList);
        dataMap.put("num",mapList.size());
        dataMap.put("checkNum",checkNum);
        if (mapList.size() < 1){
            return RestResult.error("查询为空");
        }
        return RestResult.success("查询成功",dataMap);
    }

}