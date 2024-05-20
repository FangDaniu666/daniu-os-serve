package com.daniu.pms.mapper;

import com.daniu.pms.domain.Music;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MusicMapper extends BaseMapper<Music> {
    int updateBatch(List<Music> list);

    int batchInsert(@Param("list") List<Music> list);
}