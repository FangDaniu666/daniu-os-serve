package com.daniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daniu.domain.entity.Music;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MusicService extends IService<Music>{


    int updateBatch(List<Music> list);

    int batchInsert(List<Music> list);

    Music insertOne(MultipartFile file) throws IOException;
}
