package com.daniu.mapper;

import com.daniu.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Role Mapper
 *
 * @author FangDaniu
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取角色列表.
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<Role> findRolesByUserId(@Param("userId") Long userId);

}
