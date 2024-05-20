package com.daniu.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daniu.common.response.Result;
import com.daniu.pms.domain.MapData;
import com.daniu.pms.service.MapDataService;
import com.daniu.pms.util.MarkdownProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import static com.daniu.common.constant.Constants.DOC_PATH;


@RestController
@Slf4j
@RequestMapping("/docs")
public class BlogController {

    @Resource
    private MapDataService mapDataService;

    /**
     * 根据提供的文件名从指定路径读取文件内容，并将其解析为JSON节点返回。
     *
     * @param name 文件名，不包含路径，文件类型应为JSON。
     * @return R 返回一个结果对象，成功时包含查询到的JSON节点，失败时返回错误信息。
     */
    @GetMapping("selectOne")
    public Result selectOne(String name) {
        // 构造文件完整路径
        String filePath = DOC_PATH + name;
        try {
            // 读取文件全部内容
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            ObjectMapper objectMapper = new ObjectMapper();
            // 将内容解析为JSON节点
            JsonNode jsonNode = objectMapper.readTree(content);

            // 返回成功结果，包含解析得到的JSON节点
            return Result.success("查询成功", jsonNode);
        } catch (Exception e) {
            // 处理异常，打印错误信息
            log.error("An error occurred: " + e.getMessage());
        }
        // 如果出现异常或文件不存在，返回错误结果
        return Result.error("没有数据");
    }

    /**
     * 上传单个markdown文件到服务器，并处理该文件。
     *
     * @param file 用户上传的文件，必须是 .md 格式。
     * @return 返回一个结果对象，表示文件上传是否成功及其原因。
     */
    @PostMapping("/insertOne")
    public Result insertOne(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请上传文件");
        }
        try {
            // 获取上传文件的原始名称
            String fileName = file.getOriginalFilename();

            // 校验文件格式是否为 .md
            if (fileName != null && !fileName.endsWith(".md")) {
                return Result.error("请上传 .md 格式的文件");
            }

            // 定义文件保存路径并保存文件
            String uploadDir = DOC_PATH + File.separator + fileName;
            Files.copy(file.getInputStream(), Paths.get(uploadDir),
                    StandardCopyOption.REPLACE_EXISTING);

            // 处理 Markdown 文件，获取相关信息
            Map<String, Object> fileInfo = MarkdownProcessor.processMarkdownFile(uploadDir, DOC_PATH);

            // 构建文件信息数据对象，并保存到数据库
            MapData mapData = MapData.builder()
                    .path((String) fileInfo.get("path"))
                    .size(Integer.parseInt(fileInfo.get("size").toString()))
                    .name((String) fileInfo.get("name"))
                    .lastedittime((Long) fileInfo.get("lastedittime"))
                    .introduction((String) fileInfo.get("abstract"))
                    .title((String) fileInfo.get("title"))
                    .build();

            QueryWrapper<MapData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("path", fileInfo.get("path"));

            MapData existingData = mapDataService.getOne(queryWrapper);
            if (existingData == null) {
                boolean save = mapDataService.save(mapData);
                if (!save) {
                    return Result.error("文件上传失败");
                }
            } else {
                mapData.setId(existingData.getId());
                mapDataService.updateById(mapData);
                return Result.success("文件更新成功");
            }
            return Result.success("文件上传成功", mapData);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败");
        }
    }


    @DeleteMapping("/deleteOne")
    public Result deleteOne(Integer id, String name) {
        String filePath = DOC_PATH + name;
        try {
            boolean removed = mapDataService.removeById(id);
            if (removed) {
                Files.deleteIfExists(Paths.get(filePath));
            } else {
                return Result.error("删除失败");
            }
            return Result.success("删除成功");
        } catch (IOException e) {
            return Result.error("删除失败");
        }
    }

}
