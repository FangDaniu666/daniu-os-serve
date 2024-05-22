package com.daniu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daniu.common.response.Result;
import com.daniu.domain.entity.Music;
import com.daniu.service.MusicService;
import com.daniu.util.FileNameUtils;
import com.daniu.util.FileUploader;
import com.daniu.util.Mp3MetadataExtractor;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.daniu.common.constant.Constants.ASSETS_PATH;

/**
 * 存储音乐数据的表(music)表控制层
 *
 * @author FangDaniu
 */
@RestController
@RequestMapping("/music")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "音乐管理")
public class MusicController {

    @Resource
    private MusicService musicService;

    @GetMapping("selectOne")
    public Result selectOne(Integer id) {
        Music music = musicService.getById(id);
        if (music != null) {
            return Result.success("查询成功", music);
        }
        return Result.error("没有数据");
    }

    @GetMapping("selectAll")
    public Result selectAll() {
        List<Music> musicList = musicService.list();
        if (!musicList.isEmpty()) {
            return Result.success("查询成功", musicList);
        }
        return Result.error("没有数据");
    }

    @PostMapping("/insertOne")
    public Result insertOne(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请上传文件");
        }
        // 获取上传文件的原始名称
        String fileName = file.getOriginalFilename();
        String title = FileNameUtils.removeFileExtension(fileName);
        String src = "/song/" + fileName;
        String musicFile = ASSETS_PATH + "/song/" + fileName;
        String picFile = ASSETS_PATH + "/musiccovers/";


        try {
            FileUploader.saveMultipartFileToLocalFile(file, ASSETS_PATH + "/song/", fileName);
        } catch (IOException e) {
            log.info("文件保存失败");
        }
        String artist = Mp3MetadataExtractor.extractArtistFromMp3(musicFile);
        String pic = "/musiccovers/" + Mp3MetadataExtractor.extractAndSaveCoverImage(musicFile, picFile, title);

        Music music = new Music().builder().title(title).artist(artist).src(src).pic(pic).build();

        QueryWrapper<Music> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("src", src);

        Music existingMusic = musicService.getOne(queryWrapper);
        //musicService.saveOrUpdate(music);
        if (existingMusic == null) {
            boolean save = musicService.save(music);
            if (!save) {
                return Result.error("文件上传失败");
            }
        } else {
            music.setId(existingMusic.getId());
            musicService.updateById(music);
            return Result.success("文件更新成功");
        }
        return Result.success("文件上传成功", music);
    }

    @DeleteMapping("/deleteOne")
    public Result deleteOne(Integer id, String src,String pic) {
        String filePath = ASSETS_PATH + src;
        String picPath = ASSETS_PATH + pic;
        try {
            boolean removed = musicService.removeById(id);
            if (removed) {
                Files.deleteIfExists(Paths.get(filePath));
                Files.deleteIfExists(Paths.get(picPath));
            } else {
                return Result.error("删除失败");
            }
            return Result.success("删除成功");
        } catch (IOException e) {
            return Result.error("删除失败");
        }
    }

}
