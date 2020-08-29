package com.gczx.application.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gczx.application.entity.DemoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDemoMapper extends BaseMapper<DemoEntity> {
    /**
     * 分页查询demo信息
     * @param offset 当前页
     * @param pageSize 每页多少项
     * @param name demo的名字
     * @param serial demo的序号
     * @return demo实体信息
     * @author guofu
     */
    List<DemoEntity> getDemo(@Param("offset") int offset,
                             @Param("pageSize") int pageSize,
                             @Param("name") String name,
                             @Param("serial") int serial);
}
