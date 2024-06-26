package com.daniu.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户与角色关系表
 *
 * @author FangDaniu
 */
@Data
@TableName("user_roles_role")
public class UserRole {

    @TableField("userId")
    private Long userId;

    @TableField("roleId")
    private Long roleId;

}
