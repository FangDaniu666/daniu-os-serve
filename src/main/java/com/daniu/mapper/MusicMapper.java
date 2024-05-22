package com.daniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daniu.domain.entity.Music;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MusicMapper extends BaseMapper<Music> {
    int updateBatch(List<Music> list);

    int batchInsert(@Param("list") List<Music> list);
}