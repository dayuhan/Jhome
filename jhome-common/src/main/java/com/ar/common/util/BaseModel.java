package com.ar.common.util;

import java.io.Serializable;

public class BaseModel<Data> implements Serializable {
    private static final long serialVersionUID = -4296070073731740489L;
    private int code;
    private Data data;
    private String msg;
    /**
     * 请求数据成功  1
     */
    public static int SUCCESS = 1;
    /**
     * 系统异常 -1
     */
    public static int FAIL = -1;

    public BaseModel(int code, Data data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseModel(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMsg(){
        return  this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
}
