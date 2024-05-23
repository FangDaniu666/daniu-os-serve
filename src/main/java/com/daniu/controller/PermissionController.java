package com.daniu.controller;

import com.daniu.common.preview.Preview;
import com.daniu.common.response.R;
import com.daniu.domain.dto.PermissionDto;
import com.daniu.domain.entity.Permission;
import com.daniu.domain.request.CreatePermissionRequest;
import com.daniu.domain.request.UpdatePermissionRequest;
import com.daniu.service.PermissionService;
import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限Controller
 *
 * @author FangDaniu
 * @since 2024/05/23
 */
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@Tag(name = "权限")
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 新建权限
     *
     * @param request 请求
     * @return {@link R }<{@link Void }>
     */
    @PostMapping
    @Preview
    @Operation(summary = "新增权限")
    public R<Void> create(@RequestBody @Validated CreatePermissionRequest request) {
        permissionService.create(request);
        return R.ok();
    }


    /**
     * 批量创建权限
     *
     * @param request 请求
     * @return {@link R }<{@link Void }>
     */
    @PostMapping("/batch")
    @Preview
    @Operation(summary = "批量新增权限")
    public R<Void> batchCreate(@RequestBody @Validated List<CreatePermissionRequest> request) {
        permissionService.createBatch(request);
        return R.ok();
    }

    /**
     * 获取所有权限
     *
     * @return {@link R }<{@link List }<{@link PermissionDto }>>
     */
    @GetMapping
    @Operation(summary = "获取所有权限")
    public R<List<PermissionDto>> findAll() {
        List<PermissionDto> menu = permissionService.findAllMenu();
        return R.ok(menu);
    }

    /**
     * 获取所有权限树
     *
     * @return {@link R }<{@link List }<{@link Tree }<{@link Long }>>>
     */
    @GetMapping("/tree")
    @Operation(summary = "获取所有权限树")
    public R<List<Tree<Long>>> findAllTree() {
        List<Tree<Long>> tree = permissionService.findAllMenuTree();
        return R.ok(tree);
    }

    /**
     * 获取菜单树
     *
     * @return {@link R }<{@link List }<{@link Tree }<{@link Long }>>>
     */
    @GetMapping("menu/tree")
    @Operation(summary = "获取菜单树")
    public R<List<Tree<Long>>> findMenuTree() {
        List<Tree<Long>> tree = permissionService.findAllMenuTree();
        return R.ok(tree);
    }

    /**
     * 根据id获取
     *
     * @param id id
     * @return {@link R }<{@link PermissionDto }>
     */
    @GetMapping("{id}")
    @Operation(summary = "根据id获取")
    public R<PermissionDto> findOne(@PathVariable Long id) {
        PermissionDto permissionDto = permissionService.getById(id).convert(PermissionDto.class);
        return R.ok(permissionDto);
    }

    @GetMapping("/button/{id}")
    public R<Long> findOneButton(@PathVariable Long id) {
        return R.ok(id);
    }

    /**
     * 根据id更新
     *
     * @param id      id
     * @param request 请求
     * @return {@link R }<{@link Object }>
     */
    @PatchMapping("{id}")
    @Operation(summary = "根据id更新")
    public R<Object> update(@PathVariable Long id, @RequestBody UpdatePermissionRequest request) {
        Permission permission = request.convert(Permission.class);
        permission.setId(id);
        permissionService.updateById(permission);
        return R.ok();
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return {@link R }<{@link Object }>
     */
    @DeleteMapping("{id}")
    @Operation(summary = "根据id删除")
    public R<Object> remove(@PathVariable Long id) {
        permissionService.removeById(id);
        return R.ok();
    }

    /**
     * 获取
     *
     * @param parentId 父id
     * @return {@link R }<{@link List }<{@link Permission }>>
     */
    @GetMapping("/button-and-api/{parentId}")
    @Operation(summary = "根据父id获取权限列表")
    public R<List<Permission>> findButtonAndApi(@PathVariable Long parentId) {
        List<Permission> permissions = permissionService.findButtonAndApi(parentId);
        return R.ok(permissions);
    }

    /**
     * 校验 path 存不存在menu资源内
     *
     * @param path 路径
     * @return {@link R }<{@link Object }>
     */
    @GetMapping("/menu/validate")
    @Operation(summary = "校验path存不存在menu资源内")
    public R<Object> validateMenuPath(String path) {
        boolean b = permissionService.validateMenuPath(path);
        return R.ok(b);
    }

}
