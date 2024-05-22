package com.daniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daniu.domain.entity.MapData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MapDataService extends IService<MapData>{


    int updateBatch(List<MapData> list);

    int batchInsert(List<MapData> list);

    void insertOrUpdateMapData(MultipartFile file) throws IOException;
}
