package com.atguigu.srb.core.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/4~20:27~星期二
 * @description: 数字字典的实体类
 */
@Data
public class ExcelDictDTO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("上级id")
    private Long parentId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("值")
    private Integer value;

    @ExcelProperty("编码")
    private String dictCode;


}
