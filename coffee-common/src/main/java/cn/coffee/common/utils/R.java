package cn.coffee.common.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class R {

    //操作成功
    public static final int SUCCESS = 200;
    //操作失败
    public static final int FAILED = 500;
    //未登入
    public static final int NO_LOGIN = 201;
    //为注册
    public static final int NO_REGISTERED = 202;
    //未认证
    public static final int UNAUTHORIZED = 401;
    //未授权
    public static final int FORBIDDEN = 403;

    /**
     * 响应是否成功
     */
    private Boolean success;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 成功返回的数据
     */
    private Object data;

    public R(Boolean success, Integer code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public static R success() {
        return new R(true,SUCCESS,"操作成功");
    }

    public static R error() {
        return new R(false,FAILED,"服务器遇到了不可思议的事情~");
    }

    public static R noLogin() {
        return new R(false,NO_LOGIN,"请先登录~");
    }

    public static R noRegistered(){
        return new R(false,NO_REGISTERED,"请先注册~");
    }

    /**
     * 自定义返回信息
     *
     * @param message 返回信息
     * @return 返回this
     */
    public R message(String message) {
        this.setMsg(message);
        return this;
    }

    /**
     * 自定义返回状态码
     *
     * @param code 状态码
     * @return 返回this
     */
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 自定义响应状态
     *
     * @param success 成功状态
     * @return 返回this
     */
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     * @param data
     * @return 响应的数据
     */
    public R data(Object data) {
        this.setData(data);
        return this;
    }




}
