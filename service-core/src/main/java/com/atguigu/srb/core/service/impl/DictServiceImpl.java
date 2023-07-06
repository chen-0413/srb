package com.atguigu.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.srb.core.listener.ExcelDictDTOListener;
import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.mapper.DictMapper;
import com.atguigu.srb.core.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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


    /**
    * @Description: 批量导入数据到数据库中
     * @param inputStream
    * @return:
    */
    @Transactional(rollbackFor = {Exception.class})  // 开启事务  防止在数据导入数据库时中途导出现错误，二次导入数据时需要把第一次导入的数据删除，才能进行二次数据导入数据库
    @Override
    public void importData(InputStream inputStream) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet().doRead();
        log.info("Excel导入成功");
    }

    /**
    * @Description: 批量导出数据 到Excel  TODO
     * @param
    * @return: {@link List< ExcelDictDTO>}
    */
    @Override
    public List<ExcelDictDTO> listDictData() {

        List<Dict> dictList = baseMapper.selectList(null);
        // 创建ExcelDictDTO列表，将Dict列表转换成ExcelDictDTO列表
        ArrayList< ExcelDictDTO> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {

            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });

        return excelDictDTOList;
    }

  /*  @Override
    public List<Dict> listByParentId(Long parentId) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
       dictQueryWrapper.eq("parent_id", parentId);
       List<Dict> dictList = baseMapper.selectList(dictQueryWrapper);
       dictList.forEach(dict -> {
           boolean hasChildren = this.hasChildren(dict.getId());
           dict.setHasChildren(hasChildren);
       });
        return dictList;
    }*/


    /**
    * @Description: 数据查询 并且存入redis中
     * @param null
    * @return: {@link null}
    */
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public List<Dict> listByParentId(Long parentId) {

        // 先查询redis中是否存在数据列表
        List<Dict> dictList = null;

        try {
            dictList  = (List<Dict>) redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            if (dictList != null) {
                log.info("从redis中取值");
                return dictList;
            }
        } catch (Exception e) {
           log.info("redis服务器异常：" +  ExceptionUtils.getStackTrace(e));
        }

        log.info("从数据库中取值");
         dictList = baseMapper.selectList(new QueryWrapper<Dict>().eq("parent_id", parentId));
         dictList.forEach(dict -> {
             // 如果有子节点，则是非叶子节点
             boolean hasChildren = this.hasChildren(dict.getId());
             dict.setHasChildren(hasChildren);
         });

        try {
            // 将数据存入redis 并且设置数据过期时间为10分钟  具体的过期时间 可以根据项目需要自行设置
            redisTemplate.opsForValue().set("srb:core:dictList:"+parentId, dictList,10, TimeUnit.MINUTES);
            log.info("数据存入redis");
        } catch (Exception e) {
            log.info("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }
        return dictList;

    }

   /*
    *判断该节点是否有子节点
    */
    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().eq("parent_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count.intValue() > 0) {
            return true;
        }
        return false;
    }

}
