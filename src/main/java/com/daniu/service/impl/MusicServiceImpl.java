package com.daniu.service.impl;

import com.daniu.mapper.MusicMapper;
import com.daniu.domain.entity.Music;
import com.daniu.service.MusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Override
    public int updateBatch(List<Music> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<Music> list) {
        return baseMapper.batchInsert(list);
    }
}
