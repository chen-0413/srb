package com.atguigu.srb.core.mapper;

import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author ChenXuJiang
 * @since 2023-07-02
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
    * @Description: 批量插入数据
     * @param list
    * @return:
    */
    void insertBatch(List<ExcelDictDTO> list);


}
