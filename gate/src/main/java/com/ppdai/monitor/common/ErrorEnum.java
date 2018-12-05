package com.ppdai.monitor.common;

/**
 * @Author haijiang
 * @Description 错误枚举
 * @Date 2018/09/25
 **/
public enum ErrorEnum {

    // ============================================ 服务之间的异常从100100到100199 ============================================
    /**
     * 服务之间的异常起始值(无业务意义)
     */
    EXCEPTION_100100(100100, "service error start"),

    /**
     * 服务异常
     */
    EXCEPTION(100101, "service error"),

    // ============================================ 空记录或者不需要发邮件提醒的异常从100200到100299 ============================================
    /**
     * 空记录或者不需要发邮件提醒的异常起始值(无业务意义)
     */
    EXCEPTION_100200(100200, "param error start"),

    /**
     * 参数无效
     */
    PARAMETER_ERROR(100201, "param invalidate"),

    /**
     * 参数func必输
     */
    ERROR_FUNC(100202, "param func is only in (%s)"),

    /**
     * 数据库中该信息为空
     */
    NO_EXISTS(100203, "this record is not exists"),

    /**
     * 参数timeScale必输
     */
    ERROR_TIME_SCALE(100204, "param timeScale is only in (%s)"),

    /**
     * 该条票据更新失败
     */
    TICKET_UPDATE_ERROR(100205, "the ticket update failed"),

    /**
     * 已被注册
     */
    REGISTER_RECORD(100206, "the record has been registered"),

    /**
     * 未被注册
     */
    NOT_REGISTER_RECORD(100207, "the record has not been registered"),

    /**
     * 参数lang必输
     */
    WEI_XIN_ERROR_LANG(100208, "lang is only in (%s)"),

    // ============================================ xxx异常从100300到100399 ============================================
    /**
     * 其他异常起始值(无业务意义)
     */
    EXCEPTION_100300(100300, "default error start"),

    /**
     * 第三方(微信通过code)调用异常
     */
    THIRD_WEI_XIN_CODE_ERROR(40029, "invalid code"),

    /**
     * 第三方(微信拉取用户信息的openid无效)调用异常
     */
    THIRD_WEI_XIN_INFO_ERROR(40003, "invalid openid");

    /**
     * 错误代码
     */
    private Integer errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    ErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
