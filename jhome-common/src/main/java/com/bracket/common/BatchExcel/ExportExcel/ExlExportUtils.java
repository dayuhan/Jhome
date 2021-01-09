package com.bracket.common.BatchExcel.ExportExcel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
 * @description: 导出ex
 * @author: Daxv
 * @create: 2021-01-07 15:07
 **/
public abstract class ExlExportUtils {
    public void exlExport(HttpServletResponse response) {
        String fileName = UUID.randomUUID() + "";
        XSSFWorkbook wb = new XSSFWorkbook();
        //根据页面获取Sheet页面
        this.GetSheetInfo(wb);
//        XSSFSheet sheets = wb.createSheet("");
//        XSSFRow row;
//        //创建 HSSFrow对象
//        row = sheets.createRow(0);
//        row.createCell(0).setCellValue("xxx");
//        for (int i = 0; i < 100; i++) {
//            //创建HSSFROW对象
//            row = sheets.createRow(i + 1);
//            row.createCell(0).setCellValue("xxx");
//        }
        OutputStream outputStream = null;
        try {
            setResponseHeader(response, fileName);
            outputStream = new BufferedOutputStream(response.getOutputStream());
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
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
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
    }

    protected abstract XSSFSheet GetSheetInfo(XSSFWorkbook wb);
}
