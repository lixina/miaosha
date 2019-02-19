package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommenReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static final String CONTENT_TYPE_RORMED="application/x-www-form-urlencoded";
    // 定义exceptionhandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData = new HashMap<>();
        if (ex instanceof BusinessException){
        BusinessException businessException = (BusinessException) ex;
        CommenReturnType commenReturnType = new CommenReturnType();
        responseData.put("errMsg",businessException.getErrMsg());
        responseData.put("errCode",businessException.getErrCode());
        }else{
            responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
            responseData.put("errCode",EmBusinessError.UNKNOWN_ERROR.getErrCode());
        }
        return CommenReturnType.create(responseData,"fail");
    }
}
