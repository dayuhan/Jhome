package com.bracket.common.Bus.AbstractController;

import com.bracket.common.FastDFS.FastDFSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器基础类 【需要被工程项目继承】
 */
public abstract class BaseController {
    protected static final String FORWARD = "forward:";
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static final String REDIRECT = "redirect:";
    protected FastDFSClient fastDFSClient = new FastDFSClient();

    public BaseController() {
    }
//    @Value("${jhome.sysproperties.adminPath}")
//    protected String adminPath;
//    @Value("${jhome.sysproperties.foreignServerPath}")
//    protected String frontPath;


//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public String handleException(Exception ex) {
//        return String.format("来自Jhome异常请求消息：%s", ex.toString());
//    }
}
