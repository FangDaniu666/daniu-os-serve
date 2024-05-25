package com.daniu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.common.constant.Constants;
import com.daniu.common.exception.BusinessException;
import com.daniu.domain.entity.MapData;
import com.daniu.mapper.MapDataMapper;
import com.daniu.service.MapDataService;
import com.daniu.util.MarkdownProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

/**
 * 地图数据服务impl
 *
 * @author FangDaniu
 * @since 2024/05/25
 */
@Service
public class MapDataServiceImpl extends ServiceImpl<MapDataMapper, MapData> implements MapDataService {

    @Override
    public int updateBatch(List<MapData> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<MapData> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public void insertOrUpdateMapData(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName != null && !fileName.endsWith(".md")) {
            throw new BusinessException("请上传 .md 格式的文件");
        }

        String uploadDir = Constants.DOC_PATH + File.separator + fileName;
        Files.copy(file.getInputStream(), Paths.get(uploadDir), StandardCopyOption.REPLACE_EXISTING);

        Map<String, Object> fileInfo = MarkdownProcessor.processMarkdownFile(uploadDir, Constants.DOC_PATH);

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

        MapData existingData = this.getOne(queryWrapper);
        if (existingData == null) {
            boolean save = this.save(mapData);
            if (!save) throw new BusinessException("文件上传失败");
        } else {
            mapData.setId(existingData.getId());
            this.updateById(mapData);
        }
    }

}
