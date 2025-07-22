package org.example.interviewapidatabase.pojo;

import lombok.Data;

@Data
public class Result<T> {
    private int code; // 0-成功，1-失败
    private String msg;
    private T data;

    // 1. 手动定义无参构造器
    public Result() {}

    // 2. 手动定义全参构造器（3个参数，必须与调用时一致）
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 3. 带数据的成功返回（参数数量与全参构造器匹配）
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data); // 3个参数
    }

    // 4. 无数据的成功返回（参数数量与全参构造器匹配）
    public static Result<Void> success() {
        return new Result<>(0, "操作成功", null); // 3个参数（data为null）
    }

    // 5. 错误返回（参数数量与全参构造器匹配）
    public static Result<Void> error(String msg) {
        return new Result<>(1, msg, null); // 3个参数（data为null）
    }
}
