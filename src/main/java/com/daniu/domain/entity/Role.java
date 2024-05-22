package com.daniu.domain.entity;

import cn.dhbin.mapstruct.helper.core.Convert;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色
 *
 * @author FangDaniu
 */
@Data
@TableName("role")
public class Role implements Convert {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String name;

    private Boolean enable;

}
