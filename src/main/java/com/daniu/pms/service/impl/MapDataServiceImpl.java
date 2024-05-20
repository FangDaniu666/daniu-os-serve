package com.daniu.pms.service.impl;

import com.daniu.pms.domain.MapData;
import com.daniu.pms.mapper.MapDataMapper;
import com.daniu.pms.service.MapDataService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class MapDataServiceImpl extends ServiceImpl<MapDataMapper, MapData> implements MapDataService {

    @Override
    public int updateBatch(List<MapData> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<MapData> list) {
        return baseMapper.batchInsert(list);
    }
}
