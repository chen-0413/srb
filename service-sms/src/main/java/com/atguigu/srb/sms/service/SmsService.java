package com.atguigu.srb.sms.service;

import java.util.Map;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/6~17:02~星期四
 * @description:
 */
public interface SmsService {

    /*
     * mobile 手机号
     * templateCode 模板(阿里云短信模板码)
     * param 给模板中添加值( 阿里云里面的Code )
     */
    void send(String mobile, String templateCode, Map<String,Object> param);

}
