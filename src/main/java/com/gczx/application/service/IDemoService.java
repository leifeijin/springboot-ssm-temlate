package com.gczx.application.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gczx.application.entity.DemoEntity;

public interface IDemoService extends IService<DemoEntity> {
    /**
     * 分页查询demo信息
     * @param offset 当前页
     * @param pageSize 每页多少项
     * @param name demo的名字
     * @param serial demo的序号
     * @return demo实体信息
     * @author guofu
     */
    Page<DemoEntity> getDemo(int offset, int pageSize, String name, int serial);

    /**
     * 新增demo信息
     * @param name demo的名字
     * @param serial demo的序号
     * @param order demo的order
     * @return demo实体信息
     * @author guofu
     */
    int addDemo(String name, int serial, int order);

    /**
     * 更新demo信息
     * @param id demo的id
     * @param name demo的名字
     * @param serial demo的序号
     * @param order demo的order
     * @return demo实体信息
     * @author guofu
     */
    int updateDemo(Long id, String name, int serial, int order);

    /**
     * 根据demo的id删除其信息
     * @param id demo的id
     * @return 删除后的信息
     * @author guofu
     */
    int deleteDemo(Long id);
}
