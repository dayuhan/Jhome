package com.fileStore.modules.fileManagement.service.impl;

import com.fileStore.autoconfiguration.SysConfigurationPropertiesBean;
import com.fileStore.conmmon.*;
import com.fileStore.modules.fileManagement.model.bo.*;
import com.fileStore.modules.fileManagement.model.query.FsFileDirectoryDetailQuery;
import com.fileStore.modules.fileManagement.model.query.UploadBase64ArrayRes;
import com.fileStore.modules.fileManagement.model.query.UploadThumbnailRes;
import com.fileStore.modules.fileManagement.model.query.UploadVedioRes;
import com.fileStore.modules.fileManagement.model.vo.FastDFSFile;
import com.fileStore.modules.fileManagement.service.FileService;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    private final static Logger logger = Logger.getLogger(FileServiceImpl.class.getName());
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    @Value("${jhome.sysproperties.serverType}")
    private String serverType;

/*    @Autowired
    DfsFileService dfsFileService;*/

    @Autowired
    FileUploadUtils fileUploadUtils;

    @Value("${decompress_dir}")
    private String decompressDir;

    @Autowired
    SysConfigurationPropertiesBean sysConfigurationPropertiesBean;

    @Autowired
    FsFileDirectoryDetailServiceImpl directoryDetailService;

    @Autowired
    UserAuxiliary userAuxiliary;

    @Override
    public String upLoad(FsFileDirectoryDetailQuery directoryDetailQuery, MultipartFile multipartFile) throws IOException {
        String fileId = "";
        if (Constant.OSS_SERVER.equals(serverType)) {
            String fileName = multipartFile.getOriginalFilename();
            int len = multipartFile.getInputStream().available();
            fileId = aliyunOSSUtil.upLoad(multipartFile.getInputStream(), FileStringUtil.getFileNameByPath(fileName));
            logger.info("文件上传fastdfs成功：" + fileId);
            //dfsFileService.save(fileId, FileStringUtil.getFileNameByPath(fileName), Files.getFileExtension(fileName), len);
            logger.info("文件保存记录成功：" + fileId);
        } else if (Constant.ORDINARY_SERVER.equals(serverType)) {
            logger.info("##上传文件..##");
            InputStream inputStream = null;
            byte[] fileBuff = null;
            try {
                if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
                    //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, "文件对象或文件名为空");
                }
                logger.info("获取文件名：" + multipartFile.getOriginalFilename());
                String ext = Files.getFileExtension(multipartFile.getOriginalFilename());
                inputStream = multipartFile.getInputStream();
                int len = inputStream.available();
                if (len <= 0) {
                    //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, "文件流为空");
                }
                fileBuff = new byte[len];
                inputStream.read(fileBuff);
                FastDFSFile fastDFSFile = new FastDFSFile(multipartFile.getOriginalFilename(), fileBuff, ext);
                fileId =  fileUploadUtils.uploadFile(fastDFSFile);
                logger.info("文件上传fastdfs成功：" + fileId);
                //dfsFileService.save(fileId, FileStringUtil.getFileNameByPath(multipartFile.getOriginalFilename()), ext, len);
                logger.info("文件保存记录成功：" + fileId);

                if (directoryDetailQuery!=null)
                {
                    directoryDetailQuery.setFileName(multipartFile.getOriginalFilename());
                    directoryDetailQuery.setCreateBy(userAuxiliary.getUserInfo().getLoginName());
                    directoryDetailQuery.setUpdateBy(userAuxiliary.getUserInfo().getLoginName());
                    directoryDetailQuery.setFileUrl(fileId);
                    directoryDetailService.addFsFileDirectoryDetail(directoryDetailQuery);
                }

            } catch (Exception e) {
               // Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
                    }
                }
            }
        }
        return fileId;
    }

    @Override
    public String upLoad(UploadBase64Req request) throws IOException {
        String fileId = "";
        String fileName = FileStringUtil.getFileNameByPath(request.getFileName());
        if (Constant.OSS_SERVER.equals(serverType)) {
//            String fileName = new StringBuilder(FileStringUtil.getRandomNumLetter(10)).append(".").append(request.getFileExt()).toString();
            InputStream inputStream = new ByteArrayInputStream(Base64.decodeBase64(request.getBase64Str()));
            int len = inputStream.available();

            fileId = aliyunOSSUtil.upLoad(inputStream, request.getFileName());
            logger.info("文件上传fastdfs成功：" + fileId);
            //dfsFileService.save(fileId, fileName, Files.getFileExtension(fileName), len);
            logger.info("文件保存记录成功：" + fileId);
        } else if (Constant.ORDINARY_SERVER.equals(serverType)) {
            logger.debug("##上传文件..##");
            byte[] fileBuff = null;
            try {
                fileBuff = Base64.decodeBase64(request.getBase64Str());
//                String fileName = FileStringUtil.getRandomNumLetter(10);
                String ext = Files.getFileExtension(fileName);
                FastDFSFile file = new FastDFSFile(fileName, fileBuff, ext);
                fileId = fileUploadUtils.uploadFile(file);
                logger.info("文件上传fastdfs成功：" + fileId);
                //dfsFileService.save(fileId, fileName, ext, fileBuff.length);
                logger.info("文件保存记录成功：" + fileId);
            } catch (Exception e) {
                e.printStackTrace();
                //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
            }
        }
        return fileId;
    }

    @Override
    public UploadBase64ArrayRes upLoadArray(UploadBase64ArrayReq request) throws IOException{
        String fileId = "";
        UploadBase64ArrayRes res = new UploadBase64ArrayRes();
        String[] fileIds = new String[request.getBase64Strs().length];
        if (Constant.OSS_SERVER.equals(serverType)) {
            for (int i = 0; i < request.getBase64Strs().length; i++) {
                InputStream inputStream = new ByteArrayInputStream(Base64.decodeBase64(request.getBase64Strs()[i]));
                int len = inputStream.available();
                fileId = aliyunOSSUtil.upLoad(inputStream, request.getFileNames()[i]);
                fileIds[i] = fileId;
                logger.info("文件上传fastdfs成功：" + fileId);
                //dfsFileService.save(fileId, request.getFileNames()[i], Files.getFileExtension(request.getFileNames()[i]), len);
                logger.info("文件保存记录成功：" + fileId);
            }
        } else if (Constant.ORDINARY_SERVER.equals(serverType)) {
            logger.debug("##上传文件..##");
            byte[] fileBuff = null;
            try {
                for (int i = 0; i < request.getBase64Strs().length; i++) {
                    fileBuff = Base64.decodeBase64(request.getBase64Strs()[i]);
                    String ext = Files.getFileExtension(request.getFileNames()[i]);
                    FastDFSFile fastDFSFile = new FastDFSFile(request.getFileNames()[i], fileBuff, ext);
                    fileId =  fileUploadUtils.uploadFile(fastDFSFile);
                    fileIds[i] = fileId;
                    logger.info("文件上传fastdfs成功：" + fileId);
                    //dfsFileService.save(fileId, request.getFileNames()[i], ext, fileBuff.length);
                    logger.info("文件保存记录成功：" + fileId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
            }
        }
        res.setFileIds(fileIds);
        return res;
    }

    @Override
    public UploadVedioRes uploadAndCompressFile(MultipartFile file) throws IOException {
        UploadVedioRes uploadVedioRes = new UploadVedioRes();
        String fileId = "";
        if (Constant.OSS_SERVER.equals(serverType)) {
//            try {
//                String fileName = file.getOriginalFilename();
//                int len = file.getInputStream().available();
//                fileId = aliyunOSSUtil.upLoad(file.getInputStream(), FileStringUtil.getFileNameByPath(fileName));
//                logger.info("文件上传fastdfs成功：" + fileId);
//                dfsFileService.save(fileId, FileStringUtil.getFileNameByPath(fileName), Files.getFileExtension(fileName), len);
//                logger.info("文件保存记录成功：" + fileId);
//            } catch (Exception e) {
//                Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
//            }
        } else if (Constant.ORDINARY_SERVER.equals(serverType)) {
            logger.info("##上传文件..##");
            InputStream inputStream = null;
            byte[] fileBuff = null;
            //创建随机文件夹隔离线程
            String tempDir = new StringBuilder().append(DateUtils.getUnixTimestamp()).append(FileStringUtil.getRandomNumLetter(10)).toString();
            String dealDir = decompressDir + File.separator + tempDir;
            try {
                if (file == null || file.getOriginalFilename() == null) {
                    //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, "文件对象或文件名为空");
                }
                logger.info("获取文件名：" + file.getOriginalFilename());
                String ext = Files.getFileExtension(file.getOriginalFilename());
                inputStream = file.getInputStream();
                int len = inputStream.available();
                if (len <= 0) {
                    //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, "文件流为空");
                }
                fileBuff = new byte[len];
                inputStream.read(fileBuff);
                FastDFSFile fastDFSFile = new FastDFSFile(file.getOriginalFilename(), fileBuff, ext);
                fileId =  fileUploadUtils.uploadFile(fastDFSFile);
                logger.info("文件上传fastdfs成功：" + fileId);
                //dfsFileService.save(fileId, FileStringUtil.getFileNameByPath(file.getOriginalFilename()), ext, len);
                logger.info("文件保存记录成功：" + fileId);

                //截取第5帧的原图和缩略图
                String vedioPath = sysConfigurationPropertiesBean.getRootPath() + File.separator + fileId;
                Map<String, String> map = FileByUtils.cutPhotoFromVedio(dealDir, vedioPath);
                String srcImagePath = map.get("srcImagePath");
                String thumbImagePath = map.get("thumbImagePath");
                map.clear();
                String srcImageFileId = fileUploadUtils.moveFile(srcImagePath);
                String thumbImageFileId = fileUploadUtils.moveFile(thumbImagePath);
                uploadVedioRes.setVedioFileId(fileId);
                uploadVedioRes.setSrcImageFileId(srcImageFileId);
                uploadVedioRes.setSrcImageName(FileStringUtil.getFileNameByPath(srcImagePath));
                uploadVedioRes.setThumbnailFileId(thumbImageFileId);
                uploadVedioRes.setThumbnailName(FileStringUtil.getFileNameByPath(thumbImagePath));
            } catch (Exception e) {
                //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
                    }
                }
                //删除临时文件夹
                FileUtils.forceDelete(new File(dealDir));
            }
        }
        return uploadVedioRes;
    }

    @Override
    public UploadThumbnailRes uploadThumbnailFile(MultipartFile file) throws IOException {
        UploadThumbnailRes uploadThumbnailRes = new UploadThumbnailRes();
        String fileId = "";
        if (Constant.OSS_SERVER.equals(serverType)) {
//            try {
//                String fileName = file.getOriginalFilename();
//                int len = file.getInputStream().available();
//                fileId = aliyunOSSUtil.upLoad(file.getInputStream(), FileStringUtil.getFileNameByPath(fileName));
//                logger.info("文件上传fastdfs成功：" + fileId);
//                dfsFileService.save(fileId, FileStringUtil.getFileNameByPath(fileName), Files.getFileExtension(fileName), len);
//                logger.info("文件保存记录成功：" + fileId);
//            } catch (Exception e) {
//                Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
//            }
        } else if (Constant.ORDINARY_SERVER.equals(serverType)) {
            logger.info("##上传文件..##");
            InputStream inputStream = null;
            byte[] fileBuff = null;
            //创建随机文件夹隔离线程
            String tempDir = new StringBuilder().append(DateUtils.getUnixTimestamp()).append(FileStringUtil.getRandomNumLetter(10)).toString();
            String dealDir = decompressDir + File.separator + tempDir;
            try {
                if (file == null || file.getOriginalFilename() == null) {
                    //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, "文件对象或文件名为空");
                }
                logger.info("获取文件名：" + file.getOriginalFilename());
                String ext = Files.getFileExtension(file.getOriginalFilename());
                if (!ext.matches("^[(jpg)|(png)|(gif)]+$")) {
                   // Shift.fatalOnlyDetail(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, "请输入png,jpg,gif格式的图片");
                }
                inputStream = file.getInputStream();
                int len = inputStream.available();
                if (len <= 0) {
                   // Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, "文件流为空");
                }
                fileBuff = new byte[len];
                inputStream.read(fileBuff);
                FastDFSFile fastDFSFile = new FastDFSFile(file.getOriginalFilename(), fileBuff, ext);
                fileId = fileUploadUtils.uploadFile(fastDFSFile);
                logger.info("文件上传fastdfs成功：" + fileId);
                //dfsFileService.save(fileId, FileStringUtil.getFileNameByPath(file.getOriginalFilename()), ext, len);
                logger.info("文件保存记录成功：" + fileId);

                //截取第5帧的原图和缩略图
//                String vedioPath = fileConfig.getRootPath() + File.separator + fileId;
                String thumbImagePath = FileByUtils.compressFromImage(dealDir, file);
                String thumbImageFileId = fileUploadUtils.moveFile(thumbImagePath);
                uploadThumbnailRes.setFileId(fileId);
                uploadThumbnailRes.setThumbnailFileId(thumbImageFileId);
                uploadThumbnailRes.setThumbnailName(FileStringUtil.getFileNameByPath(thumbImagePath));
            } catch (Exception e) {
               //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
                    }
                }
                //删除临时文件夹
                FileUtils.forceDelete(new File(dealDir));
            }
        }
        return uploadThumbnailRes;
    }

    @Override
    public String breakPointUpload(long totalSize, long fileOffset, String fileId, MultipartFile file) {
        String fileIdTemp = "";
        if (Constant.OSS_SERVER.equals(serverType)) {
//            String fileName = multipartFile.getOriginalFilename();
//            int len = multipartFile.getInputStream().available();
//            fileId = aliyunOSSUtil.upLoad(multipartFile.getInputStream(), FileStringUtil.getFileNameByPath(fileName));
//            logger.info("文件上传成功：" + fileId);
//            dfsFileService.save(fileId, FileStringUtil.getFileNameByPath(fileName), Files.getFileExtension(fileName), len);
        } else if (Constant.ORDINARY_SERVER.equals(serverType)) {
            logger.info("##上传文件..##");
            InputStream inputStream = null;
            byte[] fileBuff = null;
            try {
                inputStream = file.getInputStream();
                int len = inputStream.available();
                if (len <= 0) {
                   // Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, "文件流为空");
                }
                fileBuff = new byte[len];
                inputStream.read(fileBuff);
                if (Strings.isNullOrEmpty(fileId)) {
                    String ext = Files.getFileExtension(file.getOriginalFilename());
                    FastDFSFile fastDFSFile = new FastDFSFile(FileStringUtil.getFileNameByPath(file.getOriginalFilename()), fileBuff, ext);
                    fileIdTemp = fileUploadUtils.uploadFile(fastDFSFile);
                    logger.info("文件上传成功：" + fileIdTemp);
                    //dfsFileService.save(fileIdTemp, FileStringUtil.getFileNameByPath(file.getOriginalFilename()), ext, (int) totalSize);
                    logger.info("文件保存记录fastdfs成功：" + fileId);
                } else {
                    FastDFSFile fastDFSFile = new FastDFSFile(FileStringUtil.getFileNameByPath(file.getOriginalFilename()), fileBuff, fileId, fileOffset);
                    fileUploadUtils.uploadAppenderFile(fastDFSFile);
                }
            } catch (Exception e) {
                //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, e.getMessage());
                    }
                }
            }
        }
        return fileIdTemp;

    }

    @Override
    public void delFile(DownloadReq request) throws Exception {
//        if (dfsFileService.findByFileId(request.getFileId()) == null) {
//            Shift.fatal(HttpStatusCodesEnum.FASTDFS_REQUEST_ERR, "文件不存在，fileId:" + request.getFileId());
//        }
        if (Constant.OSS_SERVER.equals(serverType)) {
            aliyunOSSUtil.delete(request.getFileId());
        } else if (Constant.ORDINARY_SERVER.equals(serverType)) {
            fileUploadUtils.deleteFile(request.getFileId());
        }
        //dfsFileService.delDfsFile(request.getFileId());
    }

    @Override
    public void downloadUserFile(DownloadReq request, HttpServletResponse response) throws UnsupportedEncodingException {
        String fileName = FileStringUtil.getFileNameByPath(request.getFileId());
        System.out.println(fileName);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("content-type", "application/octet-stream");
        //获取组织名称
//        Long orgId = request.getOrgId();
        fileName = "我们1231.jpg";
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        try {
            if (Constant.OSS_SERVER.equals(serverType)) {
                //下载oss
//                aliyunOSSUtil.download(request.getFileId(), response);
            } else if (Constant.ORDINARY_SERVER.equals(serverType)) {
                fileUploadUtils.downloadFile(request.getFileId(), response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
