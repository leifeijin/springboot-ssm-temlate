package com.gczx.application.common.definition;

/**
 * @author leifeijin
 */
public enum Definition {
    // 返回结果状态
    RTS_STATUS_SUCCESS("success"),
    RTS_STATUS_FAILURE("failure"),
    // 返回结果信息
    RTS_OPERATE_SUCCESS ("操作成功"),
    RTS_OPERATE_FAILURE ("操作失败");


    private String value;

    Definition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
