package com.daniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daniu.domain.entity.MapData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MapDataMapper extends BaseMapper<MapData> {
    int updateBatch(List<MapData> list);

    int batchInsert(@Param("list") List<MapData> list);
}