package com.miaoshaproject.servive.model;

/**
 * 处理业务逻辑的核心模型
 */
public class UserModel {

    private Integer id;
    private String name;
    private Byte gender;
    private  Integer age;
    private String telphone;
    private String registerMode;
    private String thirdPartyId;
    private String UserPwd;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public void setUserPwd(String userPwd) {
        UserPwd = userPwd;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Byte getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getTelphone() {
        return telphone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public String getUserPwd() {
        return UserPwd;
    }

}
