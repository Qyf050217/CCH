package com.zust.cch.common;
import lombok.Data;
@Data
public class Result<T> {
    // 状态码：200-成功  500-业务失败   401-未登录等  400-参数校验失败
    private Integer code;
    private String msg;   // 给前端的提示信息
    private T data;       // 返回的数据

    private Result() {
    }

    // ================== 成功相关的方法 ==================
    /**
     * 成功，但不返回具体数据 (例如：删除用户成功、退出登录成功)
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        return result;
    }

    /**
     * 成功，且返回具体数据
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // ================== 失败相关的方法 ==================
    /**
     * 失败，返回错误提示 (默认状态码 500)
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败，自定义状态码和错误提示
     */
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}