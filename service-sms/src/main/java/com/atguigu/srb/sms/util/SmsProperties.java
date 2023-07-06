package com.atguigu.srb.sms.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/6~15:44~星期四
 * @description: 测试自定义的Application.yml里面的阿里云配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties implements InitializingBean {

   /* aliyun:
    sms:
    region-id: cn-hangzhou
    key-id: LTAI5tDHWHvG8TNEBV1qLKh3
    key-secret: lB3ikdgDGHw45Cb5yCF0e4whjHfO6m
    template-code: SMS_461880684  #你的短信模板code
    sign-name: 正航*/

    private String regionId;
    private String keyId;
    private String keySecret;
    private String templateCode;
    private String signName;

    public static String REGION_Id;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String TEMPLATE_CODE;
    public static String SIGN_NAME;


    @Override
    public void afterPropertiesSet() throws Exception {

        REGION_Id = regionId;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        TEMPLATE_CODE = templateCode;
        SIGN_NAME = signName;
    }
}
