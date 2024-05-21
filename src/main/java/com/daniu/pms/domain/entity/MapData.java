package com.daniu.pms.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于存储文件信息的表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "files")
public class MapData {
    /**
     * 每个文件的唯一标识符
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 文件路径
     */
    @TableField(value = "`path`")
    private String path;

    /**
     * 文件大小（字节）
     */
    @TableField(value = "`size`")
    private Integer size;

    /**
     * 文件名
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 最后编辑时间（毫秒数）
     */
    @TableField(value = "lastedittime")
    private Long lastedittime;

    /**
     * 文件摘要或简介
     */
    @TableField(value = "introduction")
    @JsonProperty("abstract")
    private String introduction;

    /**
     * 文件标题
     */
    @TableField(value = "title")
    private String title;
}