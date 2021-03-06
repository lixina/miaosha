package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.UserPwdDO;
import org.springframework.stereotype.Component;

@Component
public interface UserPwdDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pwd
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pwd
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    int insert(UserPwdDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pwd
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    int insertSelective(UserPwdDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pwd
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    UserPwdDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pwd
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    int updateByPrimaryKeySelective(UserPwdDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pwd
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    int updateByPrimaryKey(UserPwdDO record);

    UserPwdDO selectByUserId(Integer userId);
}