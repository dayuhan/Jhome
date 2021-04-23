package com.bracket.common.Translate;

import com.bracket.common.BatchExcel.ExcelConfig;
import com.bracket.common.BatchExcel.ExcelteColumnMapping;
import com.bracket.common.BatchExcel.ExcelteEngine;
import com.bracket.common.BatchExcel.Notify.demoNotify;
import com.bracket.common.BatchExcel.WorkbookUtils;
import com.domain.common.UserInfo;
import lombok.SneakyThrows;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
 * @description: 代理服务
 * @author: Daxv
 * @create: 2021-04-22 15:02
 **/
public class ProxyServerThread implements Runnable {
    List<ProxyModel> proxyList = new ArrayList<>(); //可用代理
    List<ProxyModel> proxyListCandidate = new ArrayList<>();//候选代理
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @SneakyThrows
    @Override
    public void run() {
        //读取文件
        //获取候选代理
        getProxyList();
        //获取筛选可用的代理
        getProxyListCandidate();
        logger.info("***************************************");
        logger.info("可用的代理服务数量："+proxyList.size());
        logger.info("***************************************");

        //更新本地代理文件
    }

    /**
     * 读取文件获取
     */
    public List<ProxyModel> getProxyList() throws IllegalAccessException, InstantiationException, UnsupportedEncodingException {

        demoNotify dNotify = new demoNotify();
        StringBuilder sBuilder = new StringBuilder();
        List<ExcelteColumnMapping> excelteColumnMappings = new ArrayList<ExcelteColumnMapping>();
        ExcelteColumnMapping excelteColumnMapping1 = new ExcelteColumnMapping();
        excelteColumnMapping1.setEntityColumnName("ip");
        excelteColumnMapping1.setExcelColumnName("A");
        excelteColumnMappings.add(excelteColumnMapping1);

        ExcelteColumnMapping excelteColumnMapping2 = new ExcelteColumnMapping();
        excelteColumnMapping2.setEntityColumnName("port");
        excelteColumnMapping2.setExcelColumnName("B");
        excelteColumnMappings.add(excelteColumnMapping2);

        String path = this.getClass().getClassLoader().getResource("ServerIps.xlsx").getPath();
        path=path.substring(1);
        WorkbookUtils workbookUtils = new WorkbookUtils(path);
        SXSSFWorkbook sworkbook = workbookUtils.getWorkbook();
        XSSFWorkbook workbook = sworkbook.getXSSFWorkbook();
        ExcelConfig excelConfig = new ExcelConfig();
        excelConfig.setSheetRowStartIndex(1);
        excelConfig.setWorkSheet(workbook.getSheetAt(0));

        ExcelteEngine<ProxyModel> engine = new ExcelteEngine<ProxyModel>(dNotify);

        try {
            proxyListCandidate = engine.MappingToEntity(excelConfig, excelteColumnMappings, sBuilder, ProxyModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取可用的代理
     */
    public void getProxyListCandidate() {
        proxyListCandidate.stream().forEach(c -> {
            try {
                if (ProxyUtil.doIsConnect(c.getIp(),Integer.valueOf(c.getPort()) ))
                {
                    logger.info("可用IP："+c.getIp()+":"+c.getPort());
                    proxyList.add(c);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * 更新本地接口文件
     */
    public void updateProy()
    {

    }


}
