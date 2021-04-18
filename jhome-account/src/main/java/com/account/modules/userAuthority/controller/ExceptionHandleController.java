package com.account.modules.userAuthority.controller;

import com.account.common.exception.AccountException;
import com.bracket.common.Bus.AbstractController.GlobalDataExceptionController;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.ToolKit.JSONUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@ControllerAdvice

public class ExceptionHandleController extends GlobalDataExceptionController {

    @ExceptionHandler({AccountException.class})
    @ResponseBody
    public Map validationAccountException(AccountException exception) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(JSONUtils.beanToJson(exception.getMessage()));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        //CommonRlt commonRlt=new CommonRlt(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        ResponseJson responseJson=   new ResponseJson(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMsg(exception.getMessage());
        return responseJson;
    }

    /**
     * 描述：默认异常提示
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseJson defaultErrorHandler(Exception exception) {
        LOG.error(exception.getMessage(), exception);
        return ERROR;
    }
    /**
     * 描述：默认异常提示
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseJson defaultErrorHandler(Exception exception, HttpServletRequest request) {
        LOG.error(exception.getMessage(), exception);
        return ERROR;
    }
}
