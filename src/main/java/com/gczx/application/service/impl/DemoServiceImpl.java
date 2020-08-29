package com.gczx.application.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gczx.application.common.exception.ResponseCodeEnum;
import com.gczx.application.common.exception.UserException;
import com.gczx.application.entity.DemoEntity;
import com.gczx.application.service.IDemoService;
import com.gczx.application.service.dao.IDemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoServiceImpl extends ServiceImpl<IDemoMapper, DemoEntity> implements IDemoService {
    @Resource
    private IDemoMapper demoMapper;

    @Override
    public Page<DemoEntity> getDemo(int offset, int pageSize, String name, int serial) {
        try {
            Page<DemoEntity> page = new Page<>(offset, pageSize);
            List<DemoEntity> demoEntityList = demoMapper.getDemo((offset - 1) * pageSize, pageSize, name, serial);
            page.setRecords(demoEntityList);
            return page;
        } catch (Exception e) {
            throw new UserException(ResponseCodeEnum.GET_DEMO_INFO_FAILED);
        }
    }

    @Override
    public int addDemo(String name, int serial, int order) {
        try {
            DemoEntity demoEntity = new DemoEntity();
            demoEntity.setName(name);
            demoEntity.setSerial(serial);
            demoEntity.setOrder(order);
            return demoMapper.insert(demoEntity);
        } catch (Exception e) {
            throw new UserException(ResponseCodeEnum.ADD_DEMO_INFO_FAILED);
        }
    }

    @Override
    public int updateDemo(Long id, String name, int serial, int order) {
        try {
            DemoEntity demoEntity = new DemoEntity();
            demoEntity.setId(id);
            if (null != name && !name.equals("")) {
                demoEntity.setName(name);
            }
            if (!("0".equals(String.valueOf(serial)) || "null".equals(String.valueOf(serial)) || serial <= 0)) {
                demoEntity.setSerial(serial);
            }
            if (!("0".equals(String.valueOf(order)) || "null".equals(String.valueOf(order)) || order <= 0)) {
                demoEntity.setOrder(order);
            }
            return demoMapper.updateById(demoEntity);
        } catch (Exception e) {
            throw new UserException(ResponseCodeEnum.UPDATE_DEMO_INFO_FAILED);
        }
    }

    @Override
    public int deleteDemo(Long id) {
        try {
            return demoMapper.deleteById(id);
        } catch (Exception e) {
            throw new UserException(ResponseCodeEnum.DELETE_DEMO_INFO_FAILED);
        }
    }
}
