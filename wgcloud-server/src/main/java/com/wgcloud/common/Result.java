package com.wgcloud.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String msg;
    private String code;
    private T data;

    public static <T> Result<T> success(){
        Result<T> result = new Result<T>();
        result.setCode("200");
        result.setMsg("success");
        return result;
    }

    public static <T> Result<T> success(T o){
        Result<T> result = new Result<T>();
        result.setCode("200");
        result.setMsg("success");
        result.setData(o);
        return result;
    }

    public static <T> Result<T> success(String msg, T o){
        Result<T> result = new Result<T>();
        result.setCode("200");
        result.setMsg(msg);
        result.setData(o);
        return result;
    }

    public static <T> Result<T> fail(){
        Result<T> result = new Result<T>();
        result.setCode("500");
        result.setMsg("fail");
        return result;
    }

    public static <T> Result<T> fail(String msg){
        Result<T> result = new Result<T>();
        result.setCode("500");
        result.setMsg(msg);
        return result;
    }
}
