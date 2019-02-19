package com.miaoshaproject.error;

import org.omg.CORBA.UNKNOWN;

public enum EmBusinessError implements CommenError {
    // 通用错误码
    PARAMETER_VALIDATION_ERROR(00004, "参数不合法"),
    // 用户信息相关错误定义
    USER_NOT_EXIST(10001,"用户不存在"),

    UNKNOWN_ERROR(10002,"未知错误"),
    USER_LOGIN_FAIL(20001,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(20002,"用户未登录"),
    STOCK_NOT_ENOUGTH(30001,"库存不足")
    ;
    private  int errorCode;
    private String errMsg;
    private EmBusinessError(int errorCode,String errMsg){
        this.errMsg = errMsg;
        this.errorCode = errorCode;
    }

    @Override
    public int getErrCode() {
        return this.errorCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommenError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
