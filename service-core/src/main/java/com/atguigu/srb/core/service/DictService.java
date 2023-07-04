package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;

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
     * @param inputStream
    * @return:
    */
    void importData(InputStream inputStream);

}
