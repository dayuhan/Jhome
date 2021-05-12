package com.account.common.feign;

import com.alibaba.fastjson.JSONObject;
import com.domain.common.PermissionContext;
import org.springframework.web.multipart.MultipartFile;

public class ServiceBFeignClientFallback  implements FileServiceRemoteInterface{

    @Override
    public JSONObject upload(String fileDirectoryId, MultipartFile file) {
        return null;
    }
}
