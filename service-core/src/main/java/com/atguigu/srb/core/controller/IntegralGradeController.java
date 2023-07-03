package com.atguigu.srb.core.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author ChenXuJiang
 * @since 2023-07-02
 */
@Api(tags = "网站积分等级管理")
@RestController
@RequestMapping("/web/core/integralGrade")
public class IntegralGradeController {

    @ApiOperation("测试接口Api")
    @GetMapping("/test")
    public void test() {
        return;
    }

}

