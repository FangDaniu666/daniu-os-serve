package com.daniu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.common.exception.BusinessException;
import com.daniu.domain.dto.MusicDto;
import com.daniu.domain.entity.Music;
import com.daniu.mapper.MusicMapper;
import com.daniu.service.MusicService;
import com.daniu.util.FileNameUtils;
import com.daniu.util.FileToLocalSaver;
import com.daniu.util.Mp3MetadataExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.daniu.common.constant.Constants.ASSETS_PATH;

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
    public MusicDto insertOrUpdateMusic(MultipartFile file) throws IOException {
        // 获取上传文件的原始名称
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new BusinessException("文件名不能为空");

        String title = FileNameUtils.removeFileExtension(fileName);
        String src = "/song/" + fileName;
        String musicFile = ASSETS_PATH + "/song/" + fileName;
        String picFile = ASSETS_PATH + "/musiccovers/";

        Music music = new Music();
        MusicDto musicDto;
        boolean isInsert = false;

        try {
            // 保存文件到本地
            FileToLocalSaver.saveMultipartFileToLocalFile(file, ASSETS_PATH + "/song/", fileName);

            String artist = Mp3MetadataExtractor.extractArtistFromMp3(musicFile);
            String pic = "/musiccovers/" + Mp3MetadataExtractor.extractAndSaveCoverImage(musicFile, picFile, title);

            music = Music.builder()
                    .title(title)
                    .artist(artist)
                    .src(src)
                    .pic(pic).build();

            QueryWrapper<Music> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("src", src);

            Music existingMusic = baseMapper.selectOne(queryWrapper);

            isInsert = ObjectUtil.isEmpty(existingMusic);
            if (isInsert) {
                baseMapper.insert(music);
            } else {
                music.setId(existingMusic.getId());
                baseMapper.updateById(music);
            }
            musicDto = music.convert(MusicDto.class);
            musicDto.setInsert(isInsert);
        } catch (Exception exception) {
            // 删除已保存的文件
            if (isInsert) {
                this.deleteMusicFile(music.getId(), music.getSrc(), music.getPic());
                throw new BusinessException("文件上传失败");
            } else {
                throw new BusinessException("文件更新失败");
            }
        }

        return musicDto;
    }

    @Override
    public void deleteMusicFile(Integer id, String src, String pic) throws IOException {
        String filePath = ASSETS_PATH + src;
        String picPath = ASSETS_PATH + pic;

        boolean removed = removeById(id);
        Files.deleteIfExists(Paths.get(filePath));
        Files.deleteIfExists(Paths.get(picPath));
        if (!removed) throw new BusinessException("删除失败");
    }

}
