package com.ar.common.rest;


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
 */
public enum BasicRestStatusEnum implements RestStatus {

    /**
     * CRUD请求成功
     */
    OK(200, "ok", "请求成功"),
    FAIL(201, "fail", "执行失败"),
    // 40xxx 存在但为非法参数
    INVALID_DIGESTS(40001, "message digest invalid", "数据签名非法"),

    INVALID_MODEL_FIELDS(40002, "the model has invalid fields", "字段校验非法"),

    INVALID_VERIFICATION_CODE(40003, "verification code invalid", "短信验证码输入错误"),

    INVALID_SUBJECT_STATUS(40004, "subject status invalid", "用户状态非法"),

    INVALID_SUBJECT_CREDENTIALS(40005, "subject credentials invalid", "该用户不存在或密码不正确"),

    INVALID_USER_DISABLED(40006, "The account has been disabled", "账号已被禁用"),

    INVALID_NOT_OLD_PAS(40020, "old incorrect password", "输入的旧密码不正确，请重新输入"),

    INVALID_SUBJECT_PRIVILEGE(40006, "subject privilege invalid", "用户权限非法"),

    INVALID_PARAMS_CONVERSION(40007, "parameters conversion invalid", "参数类型非法"),

    INVALID_NAMESPACE(40008, "namespace invalid", "命名空间非法"),

    INVALID_REQUEST_METHOD(40009, "request method invalid", "请求方法非法, 请检查URI与METHOD是否有误"),

    INVALID_VERSION_FORMAT(40010, "version format invalid", "版本号格式非法, e.g. 1.0.0"),

    INVALID_JSON_FORMAT(40011, "invalid json format", "JSON格式非法"),

    INVALID_XML_FORMAT(40012, "invalid xml format", "XML格式非法"),

    INVALID_PASSWORD_LENGTH(40013, "password length is not enough", "密码长度不够"),

    INVALID_FILE_FORMAT(40014, "无效的file格式", "上传文件格式非法"),

    NULL_TIMESTAMP(41001, "timestamp is null", "timestamp为NULL"),

    NULL_APP_KEY(41002, "app key null", "app key为NULL"),

    NULL_SECRET_KEY(41003, "secret key null", "secret key为NULL"),

    NULL_PARAMETERS_MANDATORY(41004, "request parameters null", "参数存在为NULL"),

    NULL_NO_DATA(41005, "No data yet", "暂无相关数据"),

    EXPIRED_TIMESTAMP(42001, "timestamp expired", "时间校验过期"),

    EXPIRED_VERIFICATION_CODE(42004, "verification code lose efficacy", "验证码失效"),

    EXPIRED_VERSION(42005, "app version expired", "应用版本过低, 请及时更新应用"),

    ERROR_JSON_PROPERTIES(43001, "json properties unknown error", "json参数不匹配"),

    HTTP_MEDIA_TYPE_NOT_SUPPORTED(43002, "http media type not supported", "不支持的Media Type类型"),

    EXCEED_TIME_LIMITED(45001, "exceed time limited", "参数中的时间超出限制"),

    EXCEED_CAPTCHA_LIMITED(45002, "the number of captchas is out of range", "验证码次数超出范围"),

    SENSITIVE_RISK_CONTROL_RULES(45003, "check rules for sensitive words", "违反敏感词的风险控制规则"),

    EXCEED_PASSWORD_LENGTH_LIMITED(45004, "password length over range", "密码长度超过范围"),

    SENSITIVE_NO_WORDS(45005, "no sensitive words", "敏感词库没有敏感词"),

    EXCEED_CAPTCHA_ERROE(45006, "captchas is error", "验证码错误"),

    EXCEED_CAPTCHA_OVERDUE(45007, "captchas is overdue", "验证码过期"),

    EXCEED_CAPTCHA_MAXIMUM(45008, "exceed the maximum", "获取验证码次数已达上限,请明日再试."),

    NOT_EXISTS_DATA(46001, "data not exists", "记录不存在"),

    NOT_EXISTS_USER(46002, "user not exists", "用户不存在"),

    NOT_EXISTS_USER_MOBILE(46003, "please fill in the registration number", "手机号不存在"),

    EXISTS_USER_ALREADY(46004, "user has already registered", "用户已经存在"),

    UNZIP_ERR(46009, "unzip cause err", "解压zip文件出错"),

    EXISTS_DATA_ALREADY(46010, "data has already exists", "数据已经存在"),

    DATA_WAS_USED(46011, "data was been used", "数据已被使用"),

    DATA_WAS_RELATED(46012, "data was been related", "数据已被关联"),

    DATA_WAS_NAME(46013, "data was been related", "名称已被使用"),

    VIDEO_FRAME_ERR(46014, "unzip cause err", "视频帧处理异常"),

    REVOKE_TOKEN_FAIL(46015, "logout in fail", "注销token失败"),

    REVOKE_TOKEN_SUCCESS(46016, "logout in success", "注销token成功"),

    AUTH_TOKEN_INVALID(46017, "logout the invalid", "注销token无效"),

    ERROR_HYSTRIX_DOWNGRADE(47001, "hystrix cause error", "hystrix 断路降级"),

    ERROR_NETWORK_TIMEOUT(47002, "network request timeout error", "网络请求超时"),

    ERROR_FEIGN_ERR(47101, "service request error", "服务请求错误"),

    ERROR_NETWORK_FAST(47003, "network request timeout fast", "网络请求频繁"),

    ERROR_SERVER_UNKNOWN(50001, "unknown server error", "服务端异常, 请联系开发人员"),

    ERROR_SQL_SYNTAX(50002, "SQL syntax error", "SQL语法错误, 请联系开发人员"),

    USER_ACCOUNT_ERR(110010, "user account err", "用户账号异常"),

    USER_ACCOUNT_AGAIN(110011, "Please login again", "请登陆平台");

    private final int code;

    private final String msg;

    private final String subMsg;

    BasicRestStatusEnum(int code, String msg, String subMsg) {
        this.code = code;
        this.msg = msg;
        this.subMsg = subMsg;
    }

    public static BasicRestStatusEnum valueof(int returnCode) {
        BasicRestStatusEnum[] arr = values();
        for (BasicRestStatusEnum tmp : arr) {
            if (returnCode == tmp.code) {
                return tmp;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + returnCode + "]");
    }


    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.msg;
    }

    @Override
    public String subMessage() {
        return this.subMsg;
    }

    @Override
    public String toString() {
        return Integer.toString(this.code);
    }
}
