package com.bracket.common.ToolKit;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-08-28 10:45
 **/

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 * @program: jhome-root
 * @description: resquest 复制对象
 * @author: Daxv
 * @create: 2020-08-28 10:45
 **/
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;
    private String bodyString;
    public MyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.bodyString= StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        body=bodyString.getBytes("UTF-8");
    }
    public String getBodyString()
    {
        return this.bodyString;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return super.getReader();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
       final ByteArrayInputStream bais=new ByteArrayInputStream(body);
       return new ServletInputStream() {
           @Override
           public boolean isFinished() {
               return false;
           }

           @Override
           public boolean isReady() {
               return false;
           }

           @Override
           public void setReadListener(ReadListener readListener) {

           }

           @Override
           public int read() throws IOException {
               return 0;
           }
       };
    }
}
