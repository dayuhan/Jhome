package com.translate.conmmon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ConstantConfig {
    @Value("${lximage.endpoint}")
    private  String LXIMAGE_END_POINT;
    @Value("${lximage.keyid}")
    private  String LXIMAGE_ACCESS_KEY_ID;
    @Value("${lximage.keysecret}")
    private  String LXIMAGE_ACCESS_KEY_SECRET;
    @Value("${lximage.filehost}")
    private  String LXIMAGE_FILE_HOST;
    @Value("${lximage.bucketname1}")
    private  String LXIMAGE_BUCKET_NAME1;

    public String getLXIMAGE_END_POINT() {
        return LXIMAGE_END_POINT;
    }

    public void setLXIMAGE_END_POINT(String LXIMAGE_END_POINT) {
        this.LXIMAGE_END_POINT = LXIMAGE_END_POINT;
    }

    public String getLXIMAGE_ACCESS_KEY_ID() {
        return LXIMAGE_ACCESS_KEY_ID;
    }

    public void setLXIMAGE_ACCESS_KEY_ID(String LXIMAGE_ACCESS_KEY_ID) {
        this.LXIMAGE_ACCESS_KEY_ID = LXIMAGE_ACCESS_KEY_ID;
    }

    public String getLXIMAGE_ACCESS_KEY_SECRET() {
        return LXIMAGE_ACCESS_KEY_SECRET;
    }

    public void setLXIMAGE_ACCESS_KEY_SECRET(String LXIMAGE_ACCESS_KEY_SECRET) {
        this.LXIMAGE_ACCESS_KEY_SECRET = LXIMAGE_ACCESS_KEY_SECRET;
    }

    public String getLXIMAGE_FILE_HOST() {
        return LXIMAGE_FILE_HOST;
    }

    public void setLXIMAGE_FILE_HOST(String LXIMAGE_FILE_HOST) {
        this.LXIMAGE_FILE_HOST = LXIMAGE_FILE_HOST;
    }

    public String getLXIMAGE_BUCKET_NAME1() {
        return LXIMAGE_BUCKET_NAME1;
    }

    public void setLXIMAGE_BUCKET_NAME1(String LXIMAGE_BUCKET_NAME1) {
        this.LXIMAGE_BUCKET_NAME1 = LXIMAGE_BUCKET_NAME1;
    }
}
