package com.bracket.common.DownLoad;

import com.bracket.common.BatchExcel.WorkbookUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

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
 * @description: 下载帮助类
 * @author: Daxv
 * @create: 2021-05-19 14:33
 **/
public class downUtil {


    /** 根据文件 创建sheet
     * @param response
     * @param filePath 文件地址
     */
    public void GetFilePathDown(HttpServletResponse response, String filePath) throws UnsupportedEncodingException {
        String route = "static/";//resource 下文件夹
        String fileName = UUID.randomUUID() + "";

        //第一、根据文件创建
        if (filePath == null)
            filePath =route+ this.getClass().getClassLoader().getResource("文件名.xls").getPath();

        //第三、编码url中文乱码路径
        filePath=filePath.substring(1);
        filePath= URLDecoder.decode(filePath,"UTF-8");

        WorkbookUtils workbookUtils = new WorkbookUtils(filePath);
        SXSSFWorkbook sxssfWorkbook = workbookUtils.getWorkbook();
        XSSFWorkbook wb = sxssfWorkbook.getXSSFWorkbook();

        //第二、自己创建Sheet页面
        /*
        XSSFWorkbook wb=new XSSFWorkbook();
        XSSFSheet sheets = wb.createSheet("");
        XSSFRow row;
        //创建 HSSFrow对象
        row = sheets.createRow(0);
        row.createCell(0).setCellValue("xxx");
        for (int i = 0; i < 100; i++) {
            //创建HSSFROW对象
            row = sheets.createRow(i + 1);
            row.createCell(0).setCellValue("xxx");
        }*/
        OutputStream outputStream = null;
        try {
            //设置请求头
            setResponseHeader(response, fileName);

            //一、一次性输出
            outputStream = new BufferedOutputStream(response.getOutputStream());
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();

            /*
            //二、文件比较到 分段下载
            FileInputStream is = null;
            try {
                File file=new File(filePath);
                 is=new FileInputStream(file);
                byte[] buffer=new byte[2048];
                int readLength;
                while ((readLength=is.read(buffer))!=-1)
                {
                    response.getOutputStream().write(buffer,0,readLength);
                }
            }catch (Exception ex)
            {
                outputStream=response.getOutputStream();
                response.setHeader("content-Type","text/html;charset=UTF-8");
                byte[] dataByteArr="下载失败".getBytes(StandardCharsets.UTF_8);
                outputStream.write(dataByteArr);

            }finally {
                is.close();
            }*/

        } catch (Exception ex) {

        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setResponseHeader(HttpServletResponse response, String fileName) {
        //exl导出设置的头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        //word 导出设置的头
        /*
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msword");
        //设置浏览器以下载的方式处理该文件默认名为resume.doc
        response.addHeader("Content-Disposition","attachment;filename=resume.doc");*/

    }
}
