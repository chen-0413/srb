package com.atguigu.srb.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/11~11:43~星期二
 * @description:
 */
public interface OssService {
    /*
    文件上传
     */
    String upload(InputStream inputStream, String module, String fileName);

    /*
    根据路径删除文件
     */
    void removeFile(String url);







}
