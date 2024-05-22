package com.daniu.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.common.auth.RoleType;
import com.daniu.common.exception.BadRequestException;
import com.daniu.common.response.Page;
import com.daniu.domain.dto.PermissionDto;
import com.daniu.domain.dto.RolePageDto;
import com.daniu.domain.entity.Permission;
import com.daniu.domain.entity.Role;
import com.daniu.domain.entity.RolePermission;
import com.daniu.domain.entity.UserRole;
import com.daniu.domain.request.*;
import com.daniu.mapper.RoleMapper;
import com.daniu.service.PermissionService;
import com.daniu.service.RolePermissionService;
import com.daniu.service.RoleService;
import com.daniu.service.UserRoleService;
import com.daniu.util.PermissionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * RoleServiceImpl
 *
 * @author FangDaniu
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final PermissionService permissionService;

    private final RolePermissionService rolePermissionService;

    private final UserRoleService userRoleService;

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        return getBaseMapper().findRolesByUserId(userId);
    }

    @Override
    public List<Tree<Long>> findRolePermissionsTree(String roleCode) {
        Role role = this.findByCode(roleCode);
        if (role == null) {
            throw new BadRequestException("当前角色不存在或者已删除");
        }
        List<Permission> permissions =
                RoleType.SUPER_ADMIN.equals(roleCode) ? permissionService.list()
                        : permissionService.findByRoleId(role.getId());

        return PermissionUtil.toTreeNode(permissions, null);
    }

    @Override
    public Role findByCode(String roleCode) {
        return lambdaQuery().eq(Role::getCode, roleCode).one();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(CreateRoleRequest request) {
        boolean exists = lambdaQuery().eq(Role::getCode, request.getCode())
                .or()
                .eq(Role::getName, request.getName())
                .exists();

        if (exists) {
            throw new BadRequestException("角色已存在（角色名和角色编码不能重复）");
        }

        Role role = request.convert(Role.class);
        save(role);
        List<RolePermission> permissionList = request.getPermissionIds().stream()
                .map(permId -> {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(role.getId());
                    rolePermission.setPermissionId(permId);
                    return rolePermission;
                }).toList();
        this.rolePermissionService.saveBatch(permissionList);
    }

    @Override
    public Page<RolePageDto> queryPage(RolePageRequest request) {
        IPage<Role> qp = request.toPage();
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(request.getName()), Role::getName, request.getName())
                .or()
                .eq(ObjectUtil.isNotNull(request.getEnable()), Role::getEnable, request.getEnable());
        IPage<RolePageDto> pageRet = this.page(qp, queryWrapper)
                .convert(role -> {
                    RolePageDto dto = role.convert(RolePageDto.class);
                    List<Long> permissionList = rolePermissionService.lambdaQuery().select(RolePermission::getPermissionId)
                            .eq(RolePermission::getRoleId, role.getId())
                            .list().stream().map(RolePermission::getPermissionId)
                            .toList();
                    dto.setPermissionIds(permissionList);
                    return dto;
                });

        return Page.convert(pageRet);
    }

    @Override
    public List<PermissionDto> findRolePermissions(Long id) {
        return permissionService.findByRoleId(id)
                .stream()
                .map(permission -> permission.convert(PermissionDto.class))
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Long id, UpdateRoleRequest request) {
        Role role = getById(id);
        if (role == null) {
            throw new BadRequestException("角色不存在或者已删除");
        }
        if (RoleType.SUPER_ADMIN.equals(role.getCode())) {
            throw new BadRequestException("不允许修改超级管理员");
        }
        if (StrUtil.isNotBlank(request.getName())) {
            role.setName(request.getName());
        }
        if (ObjectUtil.isNotNull(request.getEnable())) {
            role.setEnable(request.getEnable());
        }
        updateById(role);
        if (request.getPermissionIds() != null) {
            rolePermissionService.lambdaUpdate().eq(RolePermission::getRoleId, id).remove();
            if (!request.getPermissionIds().isEmpty()) {
                List<RolePermission> permissionList = request.getPermissionIds().stream()
                        .map(permId -> {
                            RolePermission rolePermission = new RolePermission();
                            rolePermission.setRoleId(id);
                            rolePermission.setPermissionId(permId);
                            return rolePermission;
                        }).toList();
                rolePermissionService.saveBatch(permissionList);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRole(Long id) {
        Role role = getById(id);
        if (role == null) {
            throw new BadRequestException("角色不存在或者已删除");
        }
        if (RoleType.SUPER_ADMIN.equals(role.getCode())) {
            throw new BadRequestException("不允许修改超级管理员");
        }
        removeById(id);
        userRoleService.lambdaUpdate().eq(UserRole::getRoleId, id).remove();
        rolePermissionService.lambdaUpdate().eq(RolePermission::getRoleId, id).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRolePermissions(AddRolePermissionsRequest request) {
        Role role = getById(request.getId());
        if (role == null) {
            throw new BadRequestException("角色不存在或者已删除");
        }
        if (RoleType.SUPER_ADMIN.equals(role.getCode())) {
            throw new BadRequestException("无需给超级管理员授权");
        }
        List<Long> list = rolePermissionService.lambdaQuery()
                .select(RolePermission::getPermissionId)
                .eq(RolePermission::getRoleId, role.getId())
                .list()
                .stream()
                .map(RolePermission::getPermissionId).toList();

        CollUtil.removeWithAddIf(request.getPermissionIds(), list::contains);
        List<RolePermission> permissionList = request.getPermissionIds()
                .stream()
                .map(permId -> {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(role.getId());
                    rolePermission.setPermissionId(permId);
                    return rolePermission;
                }).toList();
        rolePermissionService.saveBatch(permissionList);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoleUsers(Long roleId, AddRoleUsersRequest request) {
        boolean exists = lambdaQuery().eq(Role::getId, roleId).exists();
        if (!exists) {
            throw new BadRequestException("角色不存在或者已删除");
        }
        List<Long> list = userRoleService.lambdaQuery()
                .select(UserRole::getUserId)
                .eq(UserRole::getRoleId, roleId)
                .list()
                .stream()
                .map(UserRole::getUserId).toList();

        CollUtil.removeWithAddIf(request.getUserIds(), list::contains);
        List<UserRole> permissionList = request.getUserIds()
                .stream()
                .map(userId -> {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(userId);
                    return userRole;
                }).toList();
        userRoleService.saveBatch(permissionList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRoleUsers(Long roleId, RemoveRoleUsersRequest request) {
        boolean exists = lambdaQuery().eq(Role::getId, roleId).exists();
        if (!exists) {
            throw new BadRequestException("角色不存在或者已删除");
        }
        userRoleService.lambdaUpdate()
                .eq(UserRole::getRoleId, roleId)
                .in(UserRole::getUserId, request.getUserIds())
                .remove();
    }

}
