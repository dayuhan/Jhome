package com.account.common.sbUtil.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Slf4j
public class ReadExcelUtils {

    public static final String EXCEL03_EXTENSION = ".xls";
    public static final String EXCEL07_EXTENSION = ".xlsx";
    private Workbook wb;

    public Workbook ReadExcelUtils(String filepath, MultipartFile file) {
        String ext = filepath.substring(filepath.lastIndexOf("."));
        try {
            InputStream is = file.getInputStream();
            if (EXCEL03_EXTENSION.equals(ext)) {
                wb = new HSSFWorkbook(is);
            } else if (EXCEL07_EXTENSION.equals(ext)) {
                wb = new XSSFWorkbook(is);
            } else {
                log.error("ReadExcelUtils 文件格式错误");
                return null;
            }
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException", e);
        } catch (IOException e) {
            log.error("IOException", e);
        }
        return wb;
    }

}
