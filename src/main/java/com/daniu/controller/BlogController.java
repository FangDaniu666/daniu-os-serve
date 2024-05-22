package com.daniu.controller;

import com.daniu.common.constant.Constants;
import com.daniu.common.exception.BusinessException;
import com.daniu.common.response.Result;
import com.daniu.service.MapDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 桌面文件控制器
 *
 * @author FangDaniu
 * @since 2024/05/22
 */
@RestController
@RequestMapping("/docs")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "桌面文件管理")
public class BlogController {

    @Resource
    private MapDataService mapDataService;

    /**
     * 查询单个文件
     *
     * @param name 文件名，不包含路径，文件类型应为JSON。
     * @return {@link Result }
     * @throws IOException IOException
     */
    @GetMapping("selectOne")
    public Result selectOne(String name) throws IOException {
        // 构造文件完整路径
        String filePath = Constants.DOC_PATH + name;
        // 读取文件全部内容
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        ObjectMapper objectMapper = new ObjectMapper();
        // 将内容解析为JSON节点
        JsonNode jsonNode = objectMapper.readTree(content);

        return Result.success("查询成功", jsonNode);
    }

    /**
     * 上传文件
     *
     * @param file 用户上传的文件，必须是 .md 格式。
     * @return {@link Result }
     * @apiNote 上传单个markdown文件到服务器，并处理该文件。
     */
    @PostMapping("/insertOne")
    public Result insertOne(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new BusinessException("请上传文件");
        mapDataService.insertOrUpdateMapData(file);
        return Result.success("文件上传成功");
    }


    /**
     * 删除文件
     *
     * @param id   id
     * @param name 文件名
     * @return {@link Result }
     * @throws IOException IOException
     */
    @DeleteMapping("/deleteOne")
    public Result deleteOne(Integer id, String name) throws IOException {
        String filePath = Constants.DOC_PATH + name;

        boolean removed = mapDataService.removeById(id);
        if (!removed) throw new BusinessException("删除失败");

        Files.deleteIfExists(Paths.get(filePath));
        return Result.success("删除成功");
    }

}
