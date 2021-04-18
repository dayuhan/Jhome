package com.account.modules.userAuthority.service.impl;

import com.account.modules.config.DataBaseConfig;
import com.account.modules.userAuthority.service.BackupService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class BackupServiceImpl  implements BackupService {
    @Override
    public Page backupData(Integer pageNum, Integer pageSize) {
        return null;
    }
    private static final String BACKUP_FOLDER_PATH = "_backup";
    @Resource
    private DataBaseConfig dataBaseConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void backupNew(HttpServletResponse response) {
        String dbName = dataBaseConfig.getName();
        String username = dataBaseConfig.getUsername();
        String password = dataBaseConfig.getPassword();
        String hostIp = dataBaseConfig.getHostIp();
        String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = dbName+ name + ".sql";
        String backupFolderPath = "_backup";
        File backupFolderFile = new File(BACKUP_FOLDER_PATH);
        if (!backupFolderFile.exists()){
            backupFolderFile.mkdirs();
        }
        if (!BACKUP_FOLDER_PATH.endsWith(File.separator) && !BACKUP_FOLDER_PATH.endsWith("/")){
            backupFolderPath = BACKUP_FOLDER_PATH + File.separator;
        }
        String backupFilePath = backupFolderPath + fileName;
        //如果没有配置mysql的环境变量,就需要将命令再mysqldump所在的目录下执行命令,或将mysqldump放到_backup目录下
        String command = backupFolderPath + "\\mysqldump -h"+hostIp+" -u"+username+
                " -p"+password + " "+ dbName+ " >"+backupFilePath;
        try {
            Process process = Runtime.getRuntime().exec(getOsCommand(command));
            if (process.waitFor() == 0){
                System.out.println("数据备份成功");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
//        Backup backup = new Backup();
//        backup.setFileName(fileName);
        File file = new File(backupFilePath);
//        backup.setFileSize((double)file.length()/1024);
//        backupDao.insert(backup);
        backupExport(file, fileName, response);
    }

    private void backupExport(File file, String fileName, HttpServletResponse response) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            int readLength;
            setResponseHeader(response, fileName);
            while((readLength=is.read(buffer)) !=-1){
                response.getOutputStream().write(buffer, 0, readLength);
            }
        } catch (Exception e) {
            try {
                OutputStream outputStream = response.getOutputStream();
                response.setHeader("content-type", "text/html;charset=UTF-8");
                byte[] dataByteArr = "下载失败".getBytes(StandardCharsets.UTF_8);
                outputStream.write(dataByteArr);
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void backupDel(Integer id) {
//        Backup backup = backupDao.selectById(id);
//        KanqAssert.dataCheck(backup, CodeEnum.BACKUP_NOT_EXIST.getMessage());
//        backupDao.deleteById(id);
//        String restoreFilePath = BACKUP_FOLDER_PATH + File.separator + backup.getFileName();
//        File file = new File(restoreFilePath);
//        file.delete();
    }


    private String getOsCommand(String command) {
        String os = System.getProperty("os.name");
        String shell = "/bin/bash ";
        String c = "-c ";
        if (os.toLowerCase().startsWith("win")){
            shell = "cmd ";
            c = "/c";
        }
        return shell+c+command;
    }
}
