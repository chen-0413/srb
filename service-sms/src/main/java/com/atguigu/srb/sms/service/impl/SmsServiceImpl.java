package com.atguigu.srb.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.common.exception.Assert;
import com.atguigu.common.exception.BusinessException;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.srb.sms.service.SmsService;
import com.atguigu.srb.sms.util.SmsProperties;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.event.WindowFocusListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/6~17:05~星期四
 * @description: 阿里云短信功能接口实现类
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Override
    public void send(String mobile, String templateCode, Map<String, Object> param) {

        // 创建远程连接客户端对象
        DefaultProfile profile = DefaultProfile.getProfile(
                SmsProperties.REGION_Id,
                SmsProperties.KEY_ID,
                SmsProperties.KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        // 创建远程连接的请求参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST); // 访问请求POST
        request.setSysDomain("dysmsapi.aliyuncs.com"); // 设置请求的域名
        request.setSysVersion("2017-05-25"); // 版本号
        request.setSysAction("SendSms"); // 表示发送短信
        request.putQueryParameter("RegionId",SmsProperties.REGION_Id); // 短信服务所在的地理区域
        request.putQueryParameter("PhoneNumbers", mobile); // 指定要发送短信的手机号码 应该为一个变量
        request.putQueryParameter("SignName", SmsProperties.SIGN_NAME); // 你阿里云配置的短信模板签名
        request.putQueryParameter("TemplateCode", templateCode); // 你阿里云配置的短信模板code

        // 转化一个json对象
        Gson gson = new Gson();
        String json = gson.toJson(param);
        request.putQueryParameter("TemplateParam", json);

        // 使用客户端对象携带请求对象发送请求并得到 响应结果
        try {
            CommonResponse response = client.getCommonResponse(request);
            // 通信失败的处理
            boolean success = response.getHttpResponse().isSuccess();
             // ALIYUN_RESPONSE_FAIL(-501, "阿里云响应失败"),
            Assert.isTrue(success, ResponseEnum.ALIYUN_RESPONSE_FAIL);

            // 获取响应结果
            String data = response.getData();
            HashMap<String, String> resultMap = gson.fromJson(data, HashMap.class);
            String code = resultMap.get("Code");
            String message = resultMap.get("Message");
            log.info("阿里云短信发送响应结果： ");
            log.info("code: "+code);
            log.info("message: " + message);

            //ALIYUN_SMS_LIMIT_CONTROL_ERROR(-502, "短信发送过于频繁"),//业务限流
            Assert.notEquals("isv.BUSINESS_LIMIT_CONTROL", code, ResponseEnum.ALIYUN_SMS_LIMIT_CONTROL_ERROR);
            //ALIYUN_SMS_ERROR(-503, "短信发送失败"),//其他失败
            Assert.equals("OK", code, ResponseEnum.ALIYUN_SMS_ERROR);

        } catch (ServerException e) {
            log.error("阿里云短信发送SDK调用失败: ");
            log.error("ErrorCode= " + e.getErrCode());
            log.error("ErrorMessage= "+e.getErrMsg());
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR, e);
           // throw new RuntimeException(e);

        }catch (ClientException e) {
            log.error("阿里云短信发送SDK调用失败: ");
            log.error("ErrorCode= "+e.getErrCode());
            log.error("ErrorMessage= "+e.getErrMsg());
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR, e);
        }
    }

}
