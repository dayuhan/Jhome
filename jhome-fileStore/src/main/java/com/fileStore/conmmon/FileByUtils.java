package com.fileStore.conmmon;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


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
 * @description: 文件上传
 * @author: Daxv
 * @create: 2020-09-12 12:44
 **/
public class FileByUtils implements Serializable{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileByUtils.class);
    /****
     * 将content 写入文件
     * @param fileDir
     * @param content
     */
    public static void writeInFile(String fileDir,String content){
        File f=new File(fileDir);
        FileOutputStream fos=null;
        try {
            if(!f.exists()){
                f.createNewFile();
            }
            fos=new FileOutputStream(f);
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /****
     * 从fileDir 读取文件
     * @param fileDir
     */
    public static String readFileByLine(String fileDir){
        String s="";
        File f=new File(fileDir);
        BufferedReader br=null;
        try{
            br=new BufferedReader(new FileReader(f));
            String temp;
            while((temp=br.readLine())!=null){
                s+=temp;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    public static void createIfNotExist(String fileDir) throws IOException {
        File f=new File(fileDir);
        if(!f.exists()){
            f.createNewFile();
        }
    }

    public static MultipartFile createMultiPartFile(InputStream inputStream, String fileName) {
        try {
            return new MockMultipartFile("file", fileName, "text/plain", inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("create MultiPartFile err" + fileName);
        }

    }

    /**
     * 解压到指定目录
     * @param dealDir
     * @param file
     */
    public static void unZip(String dealDir, MultipartFile file) {
        Charset charset = Charset.forName("GBK");//默认UTF-8
        try (InputStream fis = file.getInputStream();
             ZipInputStream zis = new ZipInputStream(fis, charset)) {
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                File destFile = new File(dealDir, entry.getName());
                if (entry.isDirectory()) {
                    FileUtils.forceMkdir(destFile);
                } else {
                    try (OutputStream os = new FileOutputStream(new File(dealDir, entry.getName()))) {
                        FileUtils.touch(destFile);
                        IOUtils.copy(zis, os);
                    }catch (Exception e) {
                        //Shift.fatal(BasicRestStatusEnum.UNZIP_ERR, e.getMessage());
                    }finally {
                        zis.closeEntry();
                    }
                }
            }
        } catch (Exception e) {
            //Shift.fatal(BasicRestStatusEnum.UNZIP_ERR, e.getMessage());
        }
    }

    /**
     * 按文件名称排序
     * @param files
     */
    public static void sort(File[] files) {
        List fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile()) {
                    return -1;
                }
                if (o1.isFile() && o2.isDirectory()) {
                    return 1;
                }else {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
    }

    /**
     * 截取视频的帧，形成视频的预览图片(jpg)
     * @param dealDir 存放处理目录
     * @param videoPath 视频路径
     */
    public static Map<String, String> cutPhotoFromVedio(String dealDir, String videoPath) {
        LOGGER.info("截取视频 临时目录dealDir:"+dealDir+",视频源videoPath:"+videoPath);
        Map<String, String> map = Maps.newHashMap();
        FFmpegFrameGrabber ff = null;
        String srcImgPath = dealDir + File.separator + FileStringUtil.getNameWithoutExtension(videoPath) + ".jpg";
        String thumbImagePath = dealDir + File.separator + FileStringUtil.getNameWithoutExtension(videoPath) + "Thumb.jpg";
        try {
            ff = new FFmpegFrameGrabber(videoPath);
            ff.start();
            int lenght = ff.getLengthInFrames();
            int i = 0;
            Frame f = null;
            while (i < lenght) {
                // 过滤前4帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabFrame();
                if ((i > 4) && (f.image != null)) {
                    break;
                }
                i++;
            }
            // 截取的帧图片
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage srcImage = converter.getBufferedImage(f);
            int srcImageWidth = srcImage.getWidth();
            int srcImageHeight = srcImage.getHeight();
            // 对截图进行等比例缩放(缩略图)
            int width = 480;
            int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
            BufferedImage thumbImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            thumbImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            File srcImageOut = getAbsoluteFile(srcImgPath);
            File thumbImageOut = getAbsoluteFile(thumbImagePath);
            ImageIO.write(srcImage, "jpg", srcImageOut);
            ImageIO.write(thumbImage, "jpg", thumbImageOut);
            map.put("thumbImagePath", thumbImagePath);
            map.put("srcImagePath", srcImgPath);
        } catch (Exception e) {
            //Shift.fatal(BasicRestStatusEnum.VIDEO_FRAME_ERR, e.getMessage());
        } finally {
            try {
                if (ff != null) {
                    ff.stop();
                }
            } catch (FrameGrabber.Exception e) {
               // Shift.fatal(BasicRestStatusEnum.VIDEO_FRAME_ERR, e.getMessage());
            }
        }
        return map;
    }

    /**
     * 压缩图片
     * @param dealDir
     * @param file
     * @return
     */
    public static String compressFromImage(String dealDir, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String thumbImagePath = dealDir + File.separator + FileStringUtil.getFileNameByPath(fileName);
        String ext = Files.getFileExtension(fileName);
        InputStream inputStream = null;
        Map<String, String> map = Maps.newHashMap();
        try {
            inputStream = file.getInputStream();
            Image image = ImageIO.read(inputStream);
            int srcWidth = image.getWidth(null);
            int srcHeight = image.getHeight(null);
            // 对截图进行等比例缩放(缩略图)
            int width = 480;
            int height = (int) (((double) width / srcWidth) * srcHeight);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bufferedImage.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            File thumbImageOut = getAbsoluteFile(thumbImagePath);
            ImageIO.write(bufferedImage, ext, thumbImageOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //Shift.fatal(BasicRestStatusEnum.VIDEO_FRAME_ERR, e.getMessage());
                }
            }
        }
        return thumbImagePath;
    }

    public static final File getAbsoluteFile(String uploadDir) throws IOException {
        File desc = new File(uploadDir);
        if (!desc.getParentFile().exists()) {
            if(!desc.getParentFile().mkdirs()) {
                throw new IllegalStateException("can not mkdir:"+desc.getParentFile().getPath());
            }
        }
        if (!desc.exists()) {
            if(!desc.createNewFile()) {
                throw new IllegalStateException("can not createNewFile:"+desc.getAbsolutePath());
            }
        }
        return desc;
    }
}
