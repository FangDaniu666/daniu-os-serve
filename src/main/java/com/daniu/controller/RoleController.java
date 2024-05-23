package com.daniu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.daniu.common.auth.RoleType;
import com.daniu.common.auth.Roles;
import com.daniu.common.auth.SaTokenConfigure;
import com.daniu.common.response.Page;
import com.daniu.common.response.R;
import com.daniu.domain.dto.PermissionDto;
import com.daniu.domain.dto.RoleDto;
import com.daniu.domain.dto.RolePageDto;
import com.daniu.domain.entity.Role;
import com.daniu.domain.request.*;
import com.daniu.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色Controller
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Tag(name = "角色")
public class RoleController {

    private final RoleService roleService;

    /**
     * 新建角色
     *
     * @return R
     */
    @PostMapping
    @Roles(RoleType.SUPER_ADMIN)
    @Operation(summary = "新增角色")
    public R<Void> create(@RequestBody @Validated CreateRoleRequest request) {
        roleService.createRole(request);
        return R.ok();
    }

    /**
     * 获取所有角色
     *
     * @return R
     */
    @GetMapping
    @Operation(summary = "获取所有角色")
    public R<List<RoleDto>> findAll(
            @RequestParam(value = "enable", required = false) Boolean enable
    ) {
        List<RoleDto> roleDtoList = roleService.lambdaQuery().eq(ObjectUtil.isNotNull(enable), Role::getEnable, enable)
                .list()
                .stream()
                .map(role -> role.convert(RoleDto.class))
                .toList();
        return R.ok(roleDtoList);
    }

    /**
     * 分页
     *
     * @return R
     */
    @GetMapping("/page")
    @Operation(summary = "分页")
    public R<Page<RolePageDto>> findPagination(RolePageRequest request) {
        Page<RolePageDto> ret = roleService.queryPage(request);
        return R.ok(ret);
    }

    /**
     * 查询角色权限
     *
     * @return R
     */
    @GetMapping("/permissions")
    @Operation(summary = "查询角色权限")
    public R<List<PermissionDto>> findRolePermissions(Long id) {
        List<PermissionDto> permissionDtoList = roleService.findRolePermissions(id);
        return R.ok(permissionDtoList);
    }


    /**
     * 根据id获取
     *
     * @return R
     */
    @GetMapping("{id}")
    @Roles(RoleType.SUPER_ADMIN)
    @Operation(summary = "根据id获取")
    public R<RoleDto> findOne(@PathVariable Long id) {
        RoleDto roleDto = roleService.getById(id).convert(RoleDto.class);
        return R.ok(roleDto);
    }


    /**
     * 根据id更新
     *
     * @return R
     */
    @PatchMapping("{id}")
    @Roles({RoleType.SUPER_ADMIN, RoleType.SYS_ADMIN, RoleType.ROLE_USER})
    @Operation(summary = "根据id更新")
    public R<Void> update(@PathVariable Long id, @RequestBody UpdateRoleRequest request) {
        roleService.updateRole(id, request);
        return R.ok();
    }


    /**
     * 根据id删除
     *
     * @return R
     */
    @DeleteMapping("{id}")
    @Roles({RoleType.SUPER_ADMIN})
    @Operation(summary = "根据id删除")
    public R<Void> remove(@PathVariable Long id) {
        roleService.removeRole(id);
        return R.ok();
    }


    /**
     * 给角色添加权限
     *
     * @return R
     */
    @PostMapping("/permissions/add")
    @Roles({RoleType.SUPER_ADMIN})
    @Operation(summary = "给角色添加权限")
    public R<Void> addRolePermissions(@RequestBody @Validated AddRolePermissionsRequest request) {
        roleService.addRolePermissions(request);
        return R.ok();
    }

    /**
     * 角色的权限树
     *
     * @return R
     */
    @GetMapping("/permissions/tree")
    @Operation(summary = "角色的权限树")
    public R<List<Tree<Long>>> permissionTree() {
        String roleCode = (String) StpUtil.getExtra(SaTokenConfigure.JWT_CURRENT_ROLE_KEY);
        List<Tree<Long>> treeList = roleService.findRolePermissionsTree(roleCode);
        return R.ok(treeList);
    }


    /**
     * 给角色分配用户
     *
     * @return R
     */
    @PatchMapping("/users/add/{roleId}")
    @Roles({RoleType.SUPER_ADMIN})
    @Operation(summary = "给角色分配用户")
    public R<Void> addRoleUsers(@PathVariable Long roleId, @RequestBody AddRoleUsersRequest request) {
        roleService.addRoleUsers(roleId, request);
        return R.ok();
    }


    @PatchMapping("/users/remove/{roleId}")
    @Roles({RoleType.SUPER_ADMIN})
    @Operation(summary = "移除角色")
    public R<Void> removeRoleUsers(@PathVariable Long roleId, @RequestBody RemoveRoleUsersRequest request) {
        roleService.removeRoleUsers(roleId, request);
        return R.ok();
    }

}
