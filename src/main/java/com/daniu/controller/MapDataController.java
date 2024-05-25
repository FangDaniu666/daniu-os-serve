package com.daniu.controller;

import com.daniu.common.response.R;
import com.daniu.domain.entity.MapData;
import com.daniu.service.MapDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * map信息的控制器
 *
 * @author FangDaniu
 * @since 2024/05/22
 */
@RestController
@RequestMapping("/maps")
@RequiredArgsConstructor
@Tag(name = "map信息")
public class MapDataController {

    private final MapDataService mapDataService;

    /**
     * 通过id查询单条map数据
     *
     * @param id id
     * @return map数据
     */
    @GetMapping("selectOne")
    @Operation(summary = "根据id查询")
    public R<MapData> selectOne(Integer id) {
        Optional<MapData> optById = mapDataService.getOptById(id);
        return optById.map(mapData -> R.success("查询成功", mapData))
                .orElseGet(() -> R.error("没有数据"));
    }

    /**
     * 查询并返回所有MapData数据。
     *
     * @return {@link R }<{@link List }<{@link MapData }>>
     */
    @GetMapping("selectAll")
    @Operation(summary = "查询全部MapData数据")
    public R<List<MapData>> selectAll() {
        // 调用服务层方法，获取所有MapData数据
        List<MapData> list = mapDataService.list();
        // 如果查询结果为空，返回错误状态和消息
        if (list.isEmpty()) throw new RuntimeException("没有数据");

        return R.success("查询成功", list);
    }

}
