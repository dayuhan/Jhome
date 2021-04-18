package com.ar.common.util;

public class Constant {
    /** 未认证 */
    public static final int UNAUTHENTICATED_EXCEPTION = 0;
    /**  未授权 */
    public static final int FORBIDDEN_EXCEPTION = 1;
    /** 超时 */
    public static final int TIMEOUT_EXCEPTION = 2;
    /**  业务逻辑异常 */
    public static final int BIZ_EXCEPTION = 3;
    /**  未知异常->系统异常 */
    public static final int UNKNOWN_EXCEPTION = 4;

    //mongodb 软删标记类型转换
    public static final  String  DELETE_FLAG_EFFECTIVE = "0";
    public static final  String  DELETE_FLAG_INVALID = "1";
    /**
     * 用户路径
     */
    public static final String ACCOUNT = "/uc/userInfo/loginByAccount";

    public static final String BYCODE = "/uc/userInfo/loginByCode";

    public static final String REGISTER = "/uc/userInfo/mobileRegister";

    public static final String LOGINOUT = "/uc/userInfo/loginOut";

    public static final String SMS = "/sendSms/captcha";

    public static final String USER_AGENT = "user-agent";

    public static final String ENCRYPT = "/databury/sysException/encrypt";

    public static final String DECRYPT = "/databury/sysException/decrypt";

    /**
     * 对应clientId的读权限
     */
    public static final String SCOPE_READ = "read";
    /**
     * 对应clientId的写权限
     */
    public static final String SCOPE_WRITE = "write";
    /**
     * 对应clientId的信任权限
     */
    public static final String TRUST = "trust";
    /**
     * 对应clientId的token有限时间
     */
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;

    /**
     * 对应clientId的刷新时间
     */
    public static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;

    //正式环境不需要详细的错误信息
    public  final static String NOT_NEED_DETAIL_MSG = "prod";

    /**  fastdfs文件系统 */
    public static final String FASTDFS_SERVER = "0";

    /** oss文件系统 */
    public static final String OSS_SERVER = "1";

    public final static String TOKEN ="token";
    public  final static String PRE_PHOTO_PATH="/fileresource/downloadresource2/";
    public  final static String DEFAULT_PHOTO_PATH ="/images/theader.jpg";
    public  final static String CHAPTER = "chapter";
    public  final static String SECTION = "section";
    public  final static String RESOURCE = "resource";
    public  final static Integer CHAPTER_LEVEL = 0;
    public  final static Integer SECTION_LEVEL = 1;
    public  final static Long CHAPTER_PARENTID = 1L;
    public  final static String AR_KEY = "ar";
    public  final static String VEDIO_KEY = "vedio";
    public  final static String MODEL_KEY = "model";
    //redis key前缀
    public  final static String LISTTEXTBOOKCATEGORYAPP_KEY = "ltca:";
    /**
     * 手机号校验正则
     */
    public  final static String PATTERN_MOBILE = "^1[3456789]\\d{9}$";
}
