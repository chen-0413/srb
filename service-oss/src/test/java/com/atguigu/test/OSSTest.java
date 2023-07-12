package com.atguigu.test;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.Test;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/9~17:15~星期日
 * @description: 阿里云OSS文件上传
 */
public class OSSTest {
    String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAI5tDHWHvG8TNEBV1qLKh3";
    String accessKeySecret = "lB3ikdgDGHw45Cb5yCF0e4whjHfO6m";
    String bucketName = "srb-file-0625-1";

    // 测试远程连接OSS并且创建一个新的 Bucket
    @Test
    public void testCreateBucket() {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    // 测试获取OSS服务的文件列表
    @Test
    public void testExist() {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        boolean exists = ossClient.doesBucketExist(bucketName);
        System.out.println(exists);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}

