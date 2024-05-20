package com.daniu.pms.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 存储音乐数据的表
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "music")
public class Music {
    /**
     * 唯一标识符
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 歌曲标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 艺术家
     */
    @TableField(value = "artist")
    private String artist;

    /**
     * 音频文件链接
     */
    @TableField(value = "src")
    private String src;

    /**
     * 音乐封面图片路径
     */
    @TableField(value = "pic")
    private String pic;
}