package com.pubInterFace;

import com.alibaba.fastjson.JSONObject;
import com.bracket.common.Bus.ResponseJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
 * @description: 文件上传接口
 * @author: Daxv
 * @create: 2021-05-12 17:04
 **/
public interface FileStorageInterface {
    /**
     * 通用型上传组件
     * @param fileDirectoryId [ 1 普通类型文件存储 2 oss文件存储 3,fastdfs文件系统]
     * @param file
     * @return
     */
    @RequestMapping(value="/jhome/file/upload/uploadFile",produces = {MediaType.APPLICATION_JSON_VALUE},method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    JSONObject upload(@RequestParam(value = "fileDirectoryId",required = false) String fileDirectoryId, @RequestPart(value="file", required = false) MultipartFile file);
}
