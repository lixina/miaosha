package com.miaoshaproject.response;

public class CommenReturnType {
    // 对应请求的返回处理结果
    private String status;
    // 通用的错误码格式、前端需要的json数据
    private Object data;
    // 定义一个通用的创建方法
    public static CommenReturnType create(Object result){
        return CommenReturnType.create(result,"success");
    }
    public static CommenReturnType create(Object result,String status){
        CommenReturnType type = new CommenReturnType();
        type.setData(result);
        type.setStatus(status);
        return type;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
