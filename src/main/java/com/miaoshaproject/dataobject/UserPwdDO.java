package com.miaoshaproject.dataobject;

public class UserPwdDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_pwd.id
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_pwd.encrpt_password
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    private String encrptPassword;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_pwd.user_id
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    private Integer userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_pwd.id
     *
     * @return the value of user_pwd.id
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_pwd.id
     *
     * @param id the value for user_pwd.id
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_pwd.encrpt_password
     *
     * @return the value of user_pwd.encrpt_password
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    public String getEncrptPassword() {
        return encrptPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_pwd.encrpt_password
     *
     * @param encrptPassword the value for user_pwd.encrpt_password
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword == null ? null : encrptPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_pwd.user_id
     *
     * @return the value of user_pwd.user_id
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_pwd.user_id
     *
     * @param userId the value for user_pwd.user_id
     *
     * @mbg.generated Fri Dec 28 16:00:32 CST 2018
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}