package com.weiwu.nuclearindustry.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultUtil {
    private int code;
    private String Message;
    private Object data;

    public static ResultUtil setMessageData(int code, String msg, Object data){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(code);
        resultUtil.setData(data);
        resultUtil.setMessage(msg);
        return resultUtil;
    }

    public static ResultUtil success(){
        return setMessageData(200, null, null);
    }

    public static ResultUtil success(String msg){
        return setMessageData(200, msg, null);
    }

    public static ResultUtil success(String msg, Object data){
        return setMessageData(200, msg, data);
    }

    public static ResultUtil fail(){
        return setMessageData(404, null, null);
    }

    public static ResultUtil fail(String msg){
        return setMessageData(404, msg, null);
    }

    public static ResultUtil fail(String msg, Object data){
        return setMessageData(404, msg, data);
    }

    public static ResultUtil unauthorized(Object data){
        return setMessageData(401, "unauthorized", data);
    }

    public static ResultUtil forbidden(Object data){
        return setMessageData(403, "forbidden", data);
    }
}
