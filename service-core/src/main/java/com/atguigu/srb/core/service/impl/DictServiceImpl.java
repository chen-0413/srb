package com.atguigu.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.srb.core.listener.ExcelDictDTOListener;
import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.mapper.DictMapper;
import com.atguigu.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author ChenXuJiang
 * @since 2023-07-02
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Transactional(rollbackFor = {Exception.class})  // 开启事务  防止在数据导入数据库时中途导出现错误，二次导入数据时需要把第一次导入的数据删除，才能进行二次数据导入数据库
    @Override
    public void importData(InputStream inputStream) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet().doRead();
        log.info("importData finished");
    }
}
