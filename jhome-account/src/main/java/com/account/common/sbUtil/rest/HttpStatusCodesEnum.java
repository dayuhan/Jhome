package com.account.common.sbUtil.rest;


import com.ar.common.rest.RestStatus;

/**
 * http return code and message
 * 20xxx    请求成功 - SUCCESS
 * 40xxx    存在但为非法参数 - INVALID
 * 41xxx    NULL缺失参数 - NULL
 * 42xxx    参数过期 - EXPIRED
 * 43xxx    请求方式错误(POST,GET,HTTPS) - REQUIRED
 * 44xxx    存在但为空白参数 - EMPTY
 * 45XXX    超出限制 - LIMITED
 * 46xxx    服务端实体数据状态 - DATA
 * 47xxx    各种网络异常
 * 50xxx    未知的服务端错误 - SERVER
 * 60xxx    服务端异常 - SERVER
 * 71xxx
 */
public enum HttpStatusCodesEnum implements RestStatus {


    //公共
    /**
     * 默认返回
     */
    //公共
    ALIYUN_REQUEST_ERR(46077, "err from aliyun", "服务端异常"),
    FASTDFS_REQUEST_ERR(46078, "err from fastdfs", "服务端异常"),
    FASTDFS_DOWNLOAD_ERR(46079, "download from fastdfs err", "服务端异常"),
    ERROR_SMS_FAIL(47003, "sms request error", "赛名师请求错误"),

    INVALID_FILE_STRUCTURE(47005, "invalid file structure", "文件结构有误"),

    ERROR_TEXTBOOK(47004,"request error","教材错误"),
    ERROR_BOOKSHELF(47006,"request error","书架错误"),
    ERROR_CONFIGRATION(47009,"request error","配置提示"),
    ERROR_TEXTBOOK_CATEGORY_RESOURCE(47007,"request error","教材目录资源错误"),
    ERROR_BURY_FAIL(48003, "data bury request error", "数据埋点请求错误"),
    ERROR_RESOURCES(48999,"resource err","");



    private final int code;

    private final String msg;

    private final String subMsg;

    HttpStatusCodesEnum(int code, String msg, String subMsg) {
        this.code = code;
        this.msg = msg;
        this.subMsg = subMsg;
    }

    /**
     * Return the enum constant of this type with the specified numeric code.
     *
     * @param statusCode the numeric code of the enum to be returned
     * @return the enum constant with the specified numeric code
     * @throws IllegalArgumentException if this enum has no constant for the specified numeric code
     */
    public static HttpStatusCodesEnum valueOf(int statusCode) {
        for (HttpStatusCodesEnum status : values()) {
            if (status.code == statusCode) {
                return status;
            }
        }
        return null;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return msg;
    }

    @Override
    public String subMessage() {
        return subMsg;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return Integer.toString(code);
    }

}
