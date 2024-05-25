package com.daniu.controller;

import com.daniu.common.exception.BusinessException;
import com.daniu.common.response.R;
import com.daniu.domain.dto.MusicDto;
import com.daniu.domain.entity.Music;
import com.daniu.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    private final MusicService musicService;

    @GetMapping("selectOne")
    @Operation(summary = "根据id查询")
    public R<Music> selectOne(Integer id) {
        Music music = musicService.getById(id);
        if (music == null) throw new BusinessException("没有数据");
        return R.success("查询成功", music);
    }

    @GetMapping("selectAll")
    @Operation(summary = "查询全部")
    public R<List<Music>> selectAll() {
        List<Music> musicList = musicService.list();
        if (musicList.isEmpty()) throw new BusinessException("没有数据");
        return R.success("查询成功", musicList);
    }

    @PostMapping("/insertOne")
    @Operation(summary = "上传音乐文件")
    public R<MusicDto> insertOne(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new BusinessException("请上传文件");

        MusicDto music = musicService.insertOrUpdateMusic(file);
        if (music.isInsert()) {
            return R.success("文件上传成功", music);
        } else {
            return R.success("文件更新成功", music);
        }
    }

    @DeleteMapping("/deleteOne")
    @Operation(summary = "删除音乐文件")
    public R<String> deleteOne(Integer id, String src, String pic) throws IOException {
        musicService.deleteMusicFile(id, src, pic);
        return R.ok("删除成功");
    }

}
