package com.hzyw.basic.vo;

import org.omg.PortableInterceptor.SUCCESSFUL;

/**
 * @description:封装json对象，所有返回结果都使用它
 **/
public class Result<T> {
    // 业务自定义状态码
    private int code;
    // 请求状态描述，调试用
    private String msg;
    // 请求数据，对象或数组均可
    private T data;

    public static final int SUCCESSFUL_CODE = 200;
    public static final String SUCCESSFUL_MSG = "处理成功";

    public Result(int code, String msg, T data) {
        this.code = code ;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功时候的调用
     * @param data data
     * @param <T> t
     * @return Result
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    public static Result success(){
        return new Result(SUCCESSFUL_CODE, SUCCESSFUL_MSG, null);
    }
    /**
     * 返回消息的调用
     * @param data data
     * @param <T> t
     * @return Result
     */
    public static <T> Result<T> fail(String msg){
        return new Result<T>(msg);
    }
    /**
     * 失败时候的调用
     * @param codeMsg codeMsg
     * @param <T> t
     * @return Result
     */
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    /**
     * 成功的构造函数
     * @param data data
     */
    public Result(T data){
        //默认200是成功
        this.code = 200;
        this.msg = "SUCCESS";
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String msg) {
        this.msg = msg;
    }

    /**
     * 失败的构造函数
     * @param codeMsg codeMsg
     */
    private Result(CodeMsg codeMsg) {
        if(codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
