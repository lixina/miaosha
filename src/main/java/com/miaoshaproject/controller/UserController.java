package com.miaoshaproject.controller;

import com.miaoshaproject.controller.ViewProject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.


        EmBusinessError;
import com.miaoshaproject.response.CommenReturnType;
import com.miaoshaproject.servive.UserService;
import com.miaoshaproject.servive.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials =  "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private  HttpServletRequest httpServletRequest;

    // 用户登录接口
    @RequestMapping(value = "login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_RORMED})
    @ResponseBody
    public CommenReturnType login(@RequestParam(name="telphone")String telphone,
                                  @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 入参校验
        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 用户登录服务 用来校验用户登录是否合法

        UserModel userModel = userService.login(telphone, this.encodingByMd5(password));
        // 将登录凭证加入到用户登录成功的sessio中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
        return CommenReturnType.create(null);
    }
    // 用户注册接口
    @RequestMapping(value = "register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_RORMED})
    @ResponseBody
    public CommenReturnType register(@RequestParam(name="telphone")String telphone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Byte gender,
                                     @RequestParam(name="password")String password,
                                     @RequestParam(name="age")Integer age ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 验证手机号和对应的otpCode符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        // 用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setTelphone(telphone);
        userModel.setGender(gender);
        userModel.setRegisterMode("byPhone");
        userModel.setUserPwd(this.encodingByMd5(password));
        userModel.setAge(age);
        userService.register(userModel);
        return CommenReturnType.create(null);
    }

    public String encodingByMd5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 加密字符串
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("UTF-8")));
        return newstr;
    }
    // 用户获取otp短信接口
    @RequestMapping(value = "getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_RORMED})
    /*@RequestMapping(value = "getotp")*/
    @ResponseBody
    public CommenReturnType getOtp(@RequestParam(name="telphone")String telphone) {
        // 按照规则生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        // 将otp验证码通对应用户的手机号关联,使用httpsesion
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        // 将OTP验证码通过短信通道发送给用户
        System.out.println("otpCode"+otpCode+","+"telphone"+telphone);
        return CommenReturnType.create(null);
    }

    @ResponseBody
    @RequestMapping("/get")
    public CommenReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        // 若获取的对应用户信息不存在
        if(userModel == null){
                throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        // 将核心领域模型领域用户对象转化为可供UI使用的viewObject
        UserVO userVO = convertFromModel(userModel);
        return CommenReturnType.create(userVO);
    }
    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

}
