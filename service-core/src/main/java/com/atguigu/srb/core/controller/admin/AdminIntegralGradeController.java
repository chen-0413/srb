package com.atguigu.srb.core.controller.admin;


import com.atguigu.common.exception.Assert;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.srb.core.pojo.entity.IntegralGrade;
import com.atguigu.srb.core.service.IntegralGradeService;
import com.atguigu.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author ChenXuJiang
 * @since 2023-07-02
 *  测试 从数据库中获取数据 并且展示数据
 */
@Api(tags = "积分等级管理")
@CrossOrigin // 解决前后端连调 跨域问题
@RestController
@Slf4j
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;

   /* @ApiOperation("获取积分等级列表")
    @GetMapping("/list")
    public List<IntegralGrade> finAll() {
        return integralGradeService.list();
    }*/
    /**
    * @Description: 查询积分全部数据 并且展示
     * @param
    * @return: {@link R}
    */
    @ApiOperation("获取全部积分等级列表")
    @GetMapping("/list")
    public R listAll() {

        log.info("hi i'm helen");
        log.warn("warning!!!");
        log.error("it's a error");
        List<IntegralGrade> list = integralGradeService.list();
        return R.ok().data("list", list);

    }

    /*@ApiOperation(value = "根据id删除积分等级", notes = "逻辑删除")
    @DeleteMapping("/remove/{id}")
    public boolean removeById(
            // @ApiParam(value = "数据id", readOnly = true, example = "100")
            @PathVariable Long id ) {
        return integralGradeService.removeById(id);
    }*/

    /**
    * @Description: 根据id删除积分等级
     * @param id
    * @return: {@link R}
    */
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

    /**
    * @Description: 保存积分等级 并且返回新增之后的对象
     * @param integralGrade
    * @return: {@link R}
    */
    @ApiOperation("新增积分等级")
    @PostMapping("/save")
    public R save(
            @ApiParam(value = "积分等级对象", required = true)
            @RequestBody IntegralGrade integralGrade) {

        //如果借款额度为空就手动抛出一个自定义的异常！
        /*if(integralGrade.getBorrowAmount() == null){
            // 在ResponseEnum类中自定义一个异常处理方法
            // BORROW_AMOUNT_NULL_ERROR(-201, "借款额度不能为空"),
            throw new BusinessException(ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        }*/
        Assert.notNull(integralGrade.getBorrowAmount(),ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        boolean result = integralGradeService.save(integralGrade);
        if (result) {
            return R.ok().message("新增保存成功!");
        } else {
            return R.error().message("新增保存失败!");
        }
    }

    /**
    * @Description: 根据id获取积分等级
     * @param id
    * @return: {@link R}
    */
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

    @ApiOperation("修改(更新)积分等级")
    @PutMapping("/update")
    public R updateById(
            @ApiParam(value = "积分等级对象", required = true)
            @RequestBody IntegralGrade integralGrade) {

        boolean result = integralGradeService.updateById(integralGrade);
        if (result) {
            return R.ok().message("修改成功!");
        }else {
            return R.error().message("修改失败!");
        }

    }


}

