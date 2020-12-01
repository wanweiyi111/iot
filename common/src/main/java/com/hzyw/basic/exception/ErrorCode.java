package com.hzyw.basic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hao yuan
 * @date 2019.08.07
 */
@Getter
@AllArgsConstructor
public enum ErrorCode implements CodeEnum {

    /**
     * 业务意义参见msg字段
     */
    USER_NOT_LOGIN("401", "用户未登录"),
    USER_NOT_LOGIN_TIMEOUT("403", "用户登录已过期或联系管理员查看是否有该功能权限!"),
    USER_NO_PERMISSION("405", "用户无该功能权限，如有需要，请联系管理员"),

    ID_NOT_EXIST("0001", "id不存在"),
    ID_EMPTY("0002", "id不能为空"),
    PARAMS_EMPTY("0003", "参数不能为空"),
    CODE_EMPTY("0004", "编码不能为空"),
    IDS_MORE_10("0005", "一次最多只能删除10个"),
    JSON_PARSE_EXCEPTION("0006", "格式异常"),
    FILE_EMPTY("0007", "文件不能为空"),
    SAVE_AGAIN("0008", "已经保存"),
    CREATE_DIR_FAILED("0009", "目录创建失败"),
    CREATE_FILE_FAILED("0010", "文件创建失败"),
    DELETE_FAILED("0013", "删除失败，请联系管理员"),
    MQTT_MESSAGE_EMPTY("0014", "MQTT消息为空"),
    NETWORK_ERROR("9999", "网络错误，待会重试");

    private String code;
    private String msg;
}