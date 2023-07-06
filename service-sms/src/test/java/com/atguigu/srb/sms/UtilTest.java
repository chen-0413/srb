package com.atguigu.srb.sms;

import com.atguigu.srb.sms.util.SmsProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/6~15:50~星期四
 * @description: 测试从util里面是否能够获取对应的 自定义属性
 */
@SpringBootTest
@RunWith(SpringRunner.class) //指定 SmsProperties的配置文件的路径，以及自定义的数据
public class UtilTest {

    @Test
    public void test() {
        System.out.println(SmsProperties.REGION_Id);
        System.out.println(SmsProperties.KEY_ID);
        System.out.println(SmsProperties.KEY_SECRET);
        System.out.println(SmsProperties.TEMPLATE_CODE);
        System.out.println(SmsProperties.SIGN_NAME);
    }
}
