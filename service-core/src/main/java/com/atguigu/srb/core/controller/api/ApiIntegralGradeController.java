package com.atguigu.srb.core.controller.api;

import com.atguigu.common.result.R;
import com.atguigu.srb.core.pojo.entity.IntegralGrade;
import com.atguigu.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/3~16:02~星期一
 * @description: 用户积分等级管理
 */
@Api(tags = "用户积分等级管理接口")
@CrossOrigin // 解决前后端连调 跨域问题
@RestController
@RequestMapping("/api/core/integralGrade")
public class ApiIntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;

    @ApiOperation(value = "获取所有用户积分等级信息")
    @GetMapping("/list")
    public R listAll() {

        List<IntegralGrade> list = integralGradeService.list();
        return R.ok().data("rows", list);
    }

    @ApiOperation("根据id删除积分等级")
    @DeleteMapping("/remove/{id}")
    public R removeById(
            @ApiParam(value = "数据id", required = true, example = "1")
            @PathVariable Long id) {
        boolean result = integralGradeService.removeById(id);
        if(result) {
            return R.ok().message("删除成功!");
        }else {
            return R.error().message("删除失败!");
        }
    }

    @ApiOperation("保存或更新积分等级")
    @PostMapping("/save")
    public R save(@RequestBody IntegralGrade integralGrade) {
        boolean save = integralGradeService.save(integralGrade);
        if (save) {
            return R.ok().message("新增成功!");
        } else {
            return R.error().message("新增失败!");
        }
    }

    @ApiOperation("根据id获取对应的积分等级")
    @GetMapping("/get/{id}")
    public R getById(
            @ApiParam(value = "数据id", required = true, example = "1")
            @PathVariable Long id) {

        IntegralGrade integralGrade = integralGradeService.getById(id);
        if (integralGrade != null){
            return R.ok().data("record", integralGrade);
        }else{
            return R.error().message("数据不存在!");
        }

    }

    @ApiOperation("根据id修改(或者更新)积分等级信息")
    @PutMapping("/update")
    public R updateById(
            @ApiParam(value = "要更新的参数", required = true)
            @RequestBody IntegralGrade integralGrade) {
        boolean updateById = integralGradeService.updateById(integralGrade);
        if (updateById) {
            return R.ok().message("修改成功!");
        } else {
            return R.error().message("修改失败!");
        }
    }


}
