package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author ChenXuJiang
 * @since 2023-07-02
 */
public interface DictService extends IService<Dict> {

    /**
    * @Description: 批量导入数据到数据库中
    */
    void importData(InputStream inputStream);

    /**
    * @Description: 批量导出数据 到Excel
    */
    List<ExcelDictDTO> listDictData();

    /**
    * @Description: 根据 parentID 查询下级数据
    */
    List<Dict> listByParentId(Long parentId);



}
