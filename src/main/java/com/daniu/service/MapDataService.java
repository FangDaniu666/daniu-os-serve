package com.daniu.service;

import java.util.List;

import com.daniu.domain.entity.MapData;
import com.baomidou.mybatisplus.extension.service.IService;
public interface MapDataService extends IService<MapData>{


    int updateBatch(List<MapData> list);

    int batchInsert(List<MapData> list);

}
