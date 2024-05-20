package com.daniu.pms.service.impl;

import com.daniu.pms.domain.Music;
import com.daniu.pms.mapper.MusicMapper;
import com.daniu.pms.service.MusicService;
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
