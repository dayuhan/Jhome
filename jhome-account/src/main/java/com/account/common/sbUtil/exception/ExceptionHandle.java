package com.account.common.sbUtil.exception;/*
package com.account.common.sbUtil.exception;

import com.UserAuthentication.common.AccountException;
import com.Util.common.Bus.CommonRlt;
import com.Util.common.ToolKit.JSONUtils;
import com.Util.common.ToolKit.ResponseJson;
import com.account.common.sbUtil.model.response.error.ErrorEntity;
import com.account.common.sbUtil.rest.HttpStatusCodesEnum;
import com.ar.common.exception.IllegalValidateException;
import com.ar.common.exception.RestStatusException;
import com.ar.common.rest.BasicRestStatusEnum;
import com.ar.common.rest.RestStatus;
import com.ar.common.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLSyntaxErrorException;

*/
/**
 * Global exception handling class
 *//*

@ControllerAdvice
public class ExceptionHandle {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);
    @Value("${spring.profiles.active}")
    private String active;

    @ResponseBody()
    @ExceptionHandler({AccountException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseJson validationAccountException(AccountException exception, HttpServletRequest request) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(JSONUtils.beanToJson(exception.getMessage()));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        //return new CommonRlt(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseJson(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMsg(exception.getMessage());

    }


    */
/**
     * <strong>Request域取出对应错误信息</strong>, 封装成实体ErrorEntity后转换成JSON输出
     * @param e {@code HttpStatusCodes}异常
     * @param request HttpServletRequest
     * @return ErrorEntity
     * @see ErrorEntity
     * @see
     *//*

    @ResponseBody
    @ExceptionHandler({RestStatusException.class})
    public Object statusCodesException(Exception e, HttpServletRequest request) {
        Object error = request.getAttribute(e.getMessage());
        if (error == null) {
            final String statusName = e.getMessage();
            RestStatus statusCodes = HttpStatusCodesEnum.valueOf(statusName);
            if (statusCodes == null) {
                statusCodes = BasicRestStatusEnum.valueOf(statusName);
            }
            error = new ErrorEntity(statusCodes);
        }
        final String info = getRequestInfo(request);
        LOGGER.debug("info: {}, StatusCodes异常: {}", info, e.getMessage());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(JSONUtils.beanToJson(error));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");

        return error;
    }

    */
/**
     * <strong>Request域取出对应错误信息</strong>, 封装成实体ErrorEntity后转换成JSON输出
     *
     * @param e       {@code IllegalValidateException}异常
     * @param request HttpServletRequest
     * @return ErrorEntity
     * @see ErrorEntity
     *//*

    @ResponseBody
    @ExceptionHandler({IllegalValidateException.class})
    public Object illegalValidateException(Exception e, HttpServletRequest request) {
        // 取出存储在Request域中的Map
          Object error = request.getAttribute(e.getMessage());
          String info = getRequestInfo(request);
        if (error == null) {
            final String statusName = e.getMessage();
            RestStatus statusCodes = HttpStatusCodesEnum.valueOf(statusName);
            if (statusCodes == null) {
                statusCodes = BasicRestStatusEnum.valueOf(statusName);
            }
            error = new ErrorEntity(statusCodes);
        }
        LOGGER.debug("request_id: {}, info: {} 字段校验异常",  info);
        return  error;
    }

    */
/**
     * SQL语法异常
     *
     * @param e {@code SQLSyntaxErrorException} SpringMVC参数映射错误
     * @param request HttpServletRequest
     * @return ErrorEntity
     * @see ErrorEntity
     *//*

    @ResponseBody
    @ExceptionHandler({SQLSyntaxErrorException.class})
    public Object sqlSyntaxErrorException(Exception e, HttpServletRequest request) {
        final String info = getRequestInfo(request);
        LOGGER.error(" info: {}, SQL语法异常: {}", info, e);
        return new ErrorEntity(BasicRestStatusEnum.ERROR_SQL_SYNTAX);
    }

    */
/**
     * @param e  {@code Exception}未知的服务端异常
     * @param request HttpServletRequest
     * @return ErrorEntity
     * @see ErrorEntity
     *//*

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Object exception(Exception e, HttpServletRequest request) {
        final String info = getRequestInfo(request);
        LOGGER.error("info: {} 未知的服务端异常: {}", info, e);
        if(Constant.NOT_NEED_DETAIL_MSG.equals(active)) {
            return new ErrorEntity(BasicRestStatusEnum.ERROR_SERVER_UNKNOWN);
        } else {
            return new ErrorEntity(BasicRestStatusEnum.ERROR_SERVER_UNKNOWN, e.getMessage());
        }
    }


    private String getRequestInfo(HttpServletRequest request) {
        String result = "";
        final String method = request.getMethod();
        if (method != null && !method.isEmpty()) {
            result += method;
        }
        final String uri = request.getRequestURI();
        if (uri != null && !uri.isEmpty()) {
            result += (": " + uri);
        }
        final String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            result += ("?" + queryString);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(result);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");

        return result;
    }
}
*/
