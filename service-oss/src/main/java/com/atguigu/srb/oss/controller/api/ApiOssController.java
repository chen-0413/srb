package com.atguigu.srb.oss.controller.api;

import com.atguigu.common.exception.BusinessException;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.srb.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/11~11:33~星期二
 * @description:
 */
@Api(tags = "阿里云文件服务管理")
@RestController
@RequestMapping("/api/oss/")
public class ApiOssController {
    @Resource
    private OssService ossService;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "模块", required = true)
            @RequestParam("module") String module,
            @ApiParam(value = "文件", required = true)
            @RequestParam("file") MultipartFile file ) {

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();

            String uploadUrl = ossService.upload(inputStream, module, originalFilename);

            //返回r对象
            return R.ok().message("文件上传成功").data("url", uploadUrl);
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("根据url删除文件")
    @DeleteMapping("/remove")
    public R removeFile(
            @ApiParam(value = "要删除文件的路径", required = true)
            @RequestParam("url") String url) {
        ossService.removeFile(url);
        return R.ok().message("文件删除成功");
    }






}


