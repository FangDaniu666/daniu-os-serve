package com.daniu.controller;

import com.daniu.common.exception.BusinessException;
import com.daniu.common.response.Result;
import com.daniu.domain.entity.Music;
import com.daniu.service.MusicService;
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
 * 音乐数据的控制器
 *
 * @author FangDaniu
 * @since 2024/05/22
 */
@RestController
@RequestMapping("/music")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "音乐文件管理")
public class MusicController {

    @Resource
    private MusicService musicService;

    @GetMapping("selectOne")
    public Result selectOne(Integer id) {
        Music music = musicService.getById(id);
        if (music == null) throw new BusinessException("没有数据");
        return Result.success("查询成功", music);
    }

    @GetMapping("selectAll")
    public Result selectAll() {
        List<Music> musicList = musicService.list();
        if (musicList.isEmpty()) throw new BusinessException("没有数据");
        return Result.success("查询成功", musicList);
    }

    @PostMapping("/insertOne")
    public Result insertOne(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new BusinessException("请上传文件");

        Music music = musicService.insertOne(file);
        return Result.success("文件上传成功", music);
    }

    @DeleteMapping("/deleteOne")
    public Result deleteOne(Integer id, String src, String pic) throws IOException {
        String filePath = ASSETS_PATH + src;
        String picPath = ASSETS_PATH + pic;

        boolean removed = musicService.removeById(id);
        if (!removed) throw new BusinessException("删除失败");
        Files.deleteIfExists(Paths.get(filePath));
        Files.deleteIfExists(Paths.get(picPath));
        return Result.success("删除成功");
    }

}
