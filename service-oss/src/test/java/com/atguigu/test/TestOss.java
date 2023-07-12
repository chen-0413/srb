package com.atguigu.test;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.srb.oss.util.OssProperties;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.atguigu.srb.oss.util.OssProperties.*;


/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/9~17:15~星期日
 * @description: 阿里云OSS文件上传
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestOss {
  /*  String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAI5tDHWHvG8TNEBV1qLKh3";
    String accessKeySecret = "lB3ikdgDGHw45Cb5yCF0e4whjHfO6m";
    String bucketName = "srb-file-0625-2";*/

    // 测试远程连接OSS并且创建一个新的 Bucket
    @Test
    public void testCreateBucket() {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, KEY_ID, KEY_SECRET);

        // 创建存储空间。
        ossClient.createBucket(BUCKET_NAME);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public void tets(){
        // TODO 获取OSS的值

    }




        // 测试获取OSS服务的文件列表
   /* @Test
    public void testExist() {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        boolean exists = ossClient.doesBucketExist(bucketName);
        System.out.println(exists);

        // 关闭OSSClient。
        ossClient.shutdown();
    }*/

}

