package com.timeshaft.after_end.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseService<T> {
    public enum Code
    {
        SUCCESS, ERROR, NEED_LOGIN, REFUSE, ADDRESS_ERROR,
        REQUEST_METHOD_ERROR, ADDRESS_PERMISSION_ERROR,
        TIMEOUT, CONCURRENT_CONFLICT, INTERNAL_SERVER_ERROR,
        API_ERROR, BAD_GATEWAY,HTTP_VERSION_ERROR;
    }
    /*
            0	成功
            40000	请求错误
            40001	未授权，请登录
            40003	拒绝访问
            40004	请求地址出错，未找到指定资源
            40005	请求方法不允许
            40006	请求地址未授权，拒绝访问
            40008	请求超时
            40009	并发冲突，尝试创建资源已存在
            50000	服务器内部错误
            50001	服务器未实现该API方法。
            50002	网关错误。
            50005	HTTP版本不支持
     */
    private int getCode(Code code){
        switch(code) {
            case SUCCESS:
                return 0;
            case ERROR:
                return 40000;
            case NEED_LOGIN:
                return 40001;
            case REFUSE:
                return 40003;
            case ADDRESS_ERROR:
                return 40004;
            case REQUEST_METHOD_ERROR:
                return 40005;
            case ADDRESS_PERMISSION_ERROR:
                return 40006;
            case TIMEOUT:
                return 40008;
            case CONCURRENT_CONFLICT:
                return 40009;
            case INTERNAL_SERVER_ERROR:
                return 50000;
            case API_ERROR:
                return 50001;
            case BAD_GATEWAY:
                return 50002;
            case HTTP_VERSION_ERROR:
                return 50005;
        }
        return 999;
    }


    private T data;
    private int code;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    /**
     * 若没有数据返回，默认状态码为0，提示信息为：操作成功！
     */
    public ResponseService() {
        this.code = 0;
        this.msg = "成功";
        this.data = null;
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param msg
     */
    public ResponseService(Code code, String msg) {
        this.code = getCode(code);
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为0，默认提示信息为：操作成功！
     * @param data
     */
    public ResponseService(T data) {
        this.data = data;
        this.code = 0;
        this.msg = "成功";
    }

    /**
     * 有数据返回，状态码为0，人为指定提示信息
     * @param data
     * @param msg
     */
    public ResponseService(T data, String msg) {
        this.data = data;
        this.code = 0;
        this.msg = msg;
    }

    /**
     * 有数据返回，状态码为code，人为指定提示信息
     * @param code
     * @param data
     * @param msg
     */
    public ResponseService(Code code, T data, String msg) {
        this.data = data;
        this.code = getCode(code);
        this.msg = msg;
    }
}
