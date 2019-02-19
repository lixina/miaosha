package com.miaoshaproject.servive;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.servive.model.UserModel;

public interface UserService {

    //通过用户id获取用户对象
    UserModel getUserById(Integer id);
    // 用户注册
    void register(UserModel userModel) throws BusinessException;
    // 用户登录
    UserModel login(String telphone, String userpwd) throws BusinessException;

}
