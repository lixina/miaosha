package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommenReturnType;
import com.miaoshaproject.servive.OrderService;
import com.miaoshaproject.servive.model.OrderModel;
import com.miaoshaproject.servive.model.UserModel;
import org.apache.catalina.Session;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
public class OrderController extends BaseController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    // 封装下单请求
    @RequestMapping(value = "createOrder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_RORMED})
    @ResponseBody
    public CommenReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId",required = false)Integer promoId)throws BusinessException {
        // 获取用户的登录信息
        HttpSession session = httpServletRequest.getSession();
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录");
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, amount,promoId);
        return CommenReturnType.create(null);
    }
}
