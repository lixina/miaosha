package com.miaoshaproject.servive.impl;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserPwdDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.UserPwdDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.servive.UserService;
import com.miaoshaproject.servive.model.UserModel;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPwdDOMapper userPwdDOMapper;
    @Override
    public UserModel getUserById(Integer id) {
        // 调用userDoMapper获取到对应用户的dataobject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null){
            return null;
        }
        // 通过用户id获取用户加密过的密码
        UserPwdDO userPwdDO = userPwdDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO,userPwdDO);
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (StringUtils.isEmpty(userModel.getName())
                || userModel.getAge() == null
                || userModel.getGender() == null
                || StringUtils.isEmpty(userModel.getTelphone())){
            throw new BusinessException((EmBusinessError.PARAMETER_VALIDATION_ERROR),"参数不合法");
        }
        UserDO userDO = new UserDO();
        try {
            userDO = convertFromModel(userModel);
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已注册");
        }
        userDOMapper.insertSelective(userDO);

        UserPwdDO userPwdDO = convertPwdFromModel(userModel, userDO);
        userPwdDOMapper.insertSelective(userPwdDO);

        return;
    }

    @Override
    public UserModel login(String telphone, String userpwd) throws BusinessException {
        // 通过用户的手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if (userDO == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        int user_id = userDO.getId();
        UserPwdDO userPwdDO = userPwdDOMapper.selectByUserId(user_id);
        String password = userPwdDO.getEncrptPassword();
        // 对比用户信息内的加密密码是否和传输进来的密码相匹配
        if (!org.apache.commons.lang3.StringUtils.equals(userpwd, password)){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserModel userModel = convertFromDataObject(userDO,userPwdDO);
        return userModel;
    }

    private UserPwdDO convertPwdFromModel(UserModel userModel, UserDO userDO){
        if (userModel == null){
            return null;
        }
        UserPwdDO userPwdDO = new UserPwdDO();
        userPwdDO.setEncrptPassword(userModel.getUserPwd());
        userPwdDO.setUserId(userDO.getId());
        return userPwdDO;
    }
    private UserDO convertFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }
    private UserModel convertFromDataObject(UserDO userDO, UserPwdDO userPwdDO){
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if (userPwdDO != null) {
            userModel.setUserPwd(userPwdDO.getEncrptPassword());
        }
        return userModel;
    }
}
