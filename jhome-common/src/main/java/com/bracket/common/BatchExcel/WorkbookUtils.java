package com.bracket.common.BatchExcel;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WorkbookUtils {
    public SXSSFWorkbook workbook;

    public WorkbookUtils() {
        super();
        // TODO Auto-generated constructor stub
    }

    public WorkbookUtils(String filePath) {
        super();
        // TODO Auto-generated constructor stub
        workbook = new SXSSFWorkbook(getXSSFWorkbook(filePath), 100);
    }

    /**
     * 创建 XSSFWorkbook
     *
     * @param filePath
     * @return
     */
    public XSSFWorkbook getXSSFWorkbook(String filePath) {
        XSSFWorkbook workbook = null;
        BufferedInputStream inputStream = null;
        InputStream fileInputStream = null;
        try {
        	//从本地获取流文件
            if (!filePath.contains("http://")) {
                File fileXlsxPath = new File(filePath);
                fileInputStream = new FileInputStream(fileXlsxPath);
            } else {
            	//从网络获取流文件
				URL url = new URL(filePath);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5 * 1000);
				InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            }
            inputStream = new BufferedInputStream(inputStream);
            workbook = new XSSFWorkbook(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    /**
     * XSSFWorkbook对象
     *
     * @param filePath
     * @return
     */
    public static XSSFWorkbook getXSSFWorkbookByOut(String filePath) {
        XSSFWorkbook workbook = null;
        BufferedOutputStream outputStream = null;
        try {
            File fileXlsxPath = new File(filePath);
            outputStream = new BufferedOutputStream(new FileOutputStream(fileXlsxPath));
            workbook = new XSSFWorkbook();
            workbook.createSheet("测试Sheet");
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }


    public SXSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(SXSSFWorkbook workbook) {
        this.workbook = workbook;
    }

}
