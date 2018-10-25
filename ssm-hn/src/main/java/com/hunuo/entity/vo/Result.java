package com.hunuo.entity.vo;

import java.io.Serializable;

/**
 * 返回操作结果类
 */
public class Result implements Serializable {

    private Boolean success;

    private String message;

    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    //成功的构造函数
    public static Result ok(String message){
        return new Result(true,message);
    }

    //失败的构造函数
    public static Result fail(String message){
        return new Result(true,message);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
