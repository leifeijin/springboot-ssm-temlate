package com.gczx.application.common;

import com.gczx.application.common.definition.Definition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Http Json响应结果信息类
 * @author guofu
 */
@Getter
@Setter
@ToString
public class JsonResult<T> {
    /*响应编码*/
    private int code;
    /*相应状态*/
    private String status;
    /*响应信息*/
    private String message;
    /*响应数据*/
    private T data;

    public JsonResult() {
    }

    /**
     * 成功构造函数
     * @param data 数据
     * @author guofu
     */
    public JsonResult(T data) {
        this.code = 200;
        this.message = Definition.RTS_OPERATE_SUCCESS;
        this.status = Definition.RTS_STATUS_SUCCESS;
        this.data = data;
    }

    /**
     * 成功时处理
     * @param data 数据
     * @param <T> 数据类型
     * @return 数据
     */
    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<T>(data);
    }

    /**
     * 失败时处理
     * @param data 数据
     * @param message 错误消息
     * @param code 错误编码
     * @param <T> 数据类型
     * @return 数据
     */
    public static <T> JsonResult<T> error(T data, String message, int code) {
        JsonResult<T> jsonResult = new JsonResult<T>();
        jsonResult.setCode(code);
        jsonResult.setMessage(message);
        jsonResult.setData(data);
        jsonResult.status = Definition.RTS_STATUS_FAILURE;
        return jsonResult;
    }

    public static <T> JsonResult<T> error(T data, String message) {
        return error(data, message, 400);
    }

    public static <T> JsonResult<T> error(T data, int code) {
        return error(data, Definition.RTS_OPERATE_FAILURE, code);
    }

    public static <T> JsonResult<T> error(T data) {
        return error(data, Definition.RTS_OPERATE_FAILURE, 400);
    }

    public static <T> JsonResult<T> error(String message, int code) {
        return error(null, message, code);
    }
}
