package com.fileStore.modules.fileManagement.controller;

import com.bracket.common.Bus.AbstractController.GlobalDataExceptionController;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Bus.Status;
import com.fileStore.conmmon.FileStoreException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.Subject;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: jhome-root
 * @description: 统一异常捕获
 * @author: Daxv
 * @create: 2021-01-07 11:50
 **/
@ControllerAdvice
@ResponseBody
public class FileStoreExceptionController extends GlobalDataExceptionController {
    /**
     * 任务链业务异常
     * @param exception
     * @return
     */
    @ExceptionHandler(FileStoreException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseJson DoubleTaskExceptionErrorHandler(Exception exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,exception.getMessage());
    }
}
