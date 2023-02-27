package com.peels.ossService.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {
    @Value("${aliyun.oss.file.endpoint}")
    public String endPoint;
    @Value("${aliyun.oss.file.keyid}")
    public String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    public String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    public String bucketName;

    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;


    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = this.endPoint;
        KEY_ID = this.keyId;
        KEY_SECRET = this.keySecret;
        BUCKET_NAME = this.bucketName;
    }
}
