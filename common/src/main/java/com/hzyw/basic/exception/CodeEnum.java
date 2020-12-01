package com.hzyw.basic.exception;

/**
 * @author male
 */
@SuppressWarnings("unused")
public interface CodeEnum {

    ErrorCode USER_NOT_LOGIN = ErrorCode.USER_NOT_LOGIN;
    ErrorCode USER_NOT_LOGIN_TIMEOUT = ErrorCode.USER_NOT_LOGIN_TIMEOUT;

    ErrorCode ID_NOT_EXIST = ErrorCode.ID_NOT_EXIST;
    ErrorCode ID_EMPTY = ErrorCode.ID_EMPTY;
    ErrorCode PARAMS_EMPTY = ErrorCode.PARAMS_EMPTY;
    ErrorCode CODE_EMPTY = ErrorCode.CODE_EMPTY;
    ErrorCode IDS_MORE_10 = ErrorCode.IDS_MORE_10;
    ErrorCode JSON_PARSE_EXCEPTION = ErrorCode.JSON_PARSE_EXCEPTION;
    ErrorCode FILE_EMPTY = ErrorCode.FILE_EMPTY;
    ErrorCode SAVE_AGAIN = ErrorCode.SAVE_AGAIN;
    ErrorCode CREATE_DIR_FAILED = ErrorCode.CREATE_DIR_FAILED;
    ErrorCode CREATE_FILE_FAILED = ErrorCode.CREATE_FILE_FAILED;
    ErrorCode DELETE_FAILED = ErrorCode.DELETE_FAILED;
    ErrorCode MQTT_MESSAGE_EMPTY=ErrorCode.MQTT_MESSAGE_EMPTY;
    ErrorCode NETWORK_ERROR = ErrorCode.NETWORK_ERROR;

    /**
     * 获取HTTP状态码
     * @return HTTP状态码
     */
    String getCode();

    /**
     * 获取HTTP消息
     * @return HTTP消息
     */
    String getMsg();
}