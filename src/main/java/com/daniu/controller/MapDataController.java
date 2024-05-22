package com.daniu.controller;

import com.daniu.common.response.Result;
import com.daniu.domain.entity.MapData;
import com.daniu.service.MapDataService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 用于存储map信息的表控制层
 *
 * @author FangDaniu
 */
@RestController
@RequestMapping("/maps")
public class MapDataController {
    /**
     * 服务对象
     */
    @Resource
    private MapDataService mapDataService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Result selectOne(Integer id) {
        Optional<MapData> optById = mapDataService.getOptById(id);
        if (optById.isPresent()) {
            return Result.success("查询成功", optById.get());
        }
        return Result.error("没有数据");
    }

    /**
     * 查询并返回所有MapData数据。
     *
     * @return R对象，包含查询结果的状态和数据。如果查询成功且有数据，则返回数据列表；如果没有数据，则返回错误信息。
     */
    @GetMapping("selectAll")
    public Result selectAll() {
        // 调用服务层方法，获取所有MapData数据
        List<MapData> list = mapDataService.list();
        if (!list.isEmpty()) {
            // 如果查询结果不为空，返回成功状态和数据列表
            return Result.success("查询成功", list);
        }
        // 如果查询结果为空，返回错误状态和消息
        return Result.error("没有数据");
    }

}
