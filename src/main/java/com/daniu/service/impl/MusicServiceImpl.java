package com.daniu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.common.constant.Constants;
import com.daniu.common.exception.BusinessException;
import com.daniu.domain.dto.MusicDto;
import com.daniu.domain.entity.Music;
import com.daniu.mapper.MusicMapper;
import com.daniu.service.MusicService;
import com.daniu.util.FileNameUtils;
import com.daniu.util.FileUploader;
import com.daniu.util.Mp3MetadataExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MusicDto insertOne(MultipartFile file) throws IOException {

        // 获取上传文件的原始名称
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new BusinessException("文件名不能为空");

        String title = FileNameUtils.removeFileExtension(fileName);
        String src = "/song/" + fileName;
        String musicFile = Constants.ASSETS_PATH + "/song/" + fileName;
        String picFile = Constants.ASSETS_PATH + "/musiccovers/";

        // 保存文件到本地
        FileUploader.saveMultipartFileToLocalFile(file, Constants.ASSETS_PATH + "/song/", fileName);

        String artist = Mp3MetadataExtractor.extractArtistFromMp3(musicFile);
        String pic = "/musiccovers/" + Mp3MetadataExtractor.extractAndSaveCoverImage(musicFile, picFile, title);

        Music music = Music.builder()
                .title(title)
                .artist(artist)
                .src(src)
                .pic(pic).build();

        QueryWrapper<Music> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("src", src);

        Music existingMusic = baseMapper.selectOne(queryWrapper);

        boolean isInsert = ObjectUtil.isEmpty(existingMusic);
        if (isInsert) {
            int insertResult = baseMapper.insert(music);
            if (insertResult == 0) throw new BusinessException("文件上传失败");
        } else {
            music.setId(existingMusic.getId());
            int updateResult = baseMapper.updateById(music);
            if (updateResult == 0) throw new BusinessException("文件更新失败");
        }
        MusicDto musicDto = music.convert(MusicDto.class);
        musicDto.setInsert(isInsert);
        return musicDto;
    }

}
