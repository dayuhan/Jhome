package com.bracket.common.Http;

import com.bracket.common.Bus.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * ServletResponse 对外输出
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-06-26 14:28
 **/
public class HttpUtil {
    protected static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    public static boolean SendFlush(ServletResponse response, ResponseJson result) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(result.toString());
            out.flush();
            return true;
        } catch (Exception ex) {
            logger.error("HttpUtil-SendFlush:%s",ex.getMessage());
            return false;
        }
    }
}
