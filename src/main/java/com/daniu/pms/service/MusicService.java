package com.daniu.pms.service;

import com.daniu.pms.domain.entity.Music;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MusicService extends IService<Music>{


    int updateBatch(List<Music> list);

    int batchInsert(List<Music> list);

}
