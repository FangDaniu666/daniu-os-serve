package com.daniu.service.impl;

import com.daniu.domain.dto.PermissionDto;
import com.daniu.domain.entity.Permission;
import com.daniu.mapper.PermissionMapper;
import com.daniu.domain.request.CreatePermissionRequest;
import com.daniu.service.PermissionService;
import com.daniu.util.PermissionUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限服务类的实现类，主要负责权限相关的处理
 *
 * @author FangDaniu
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService {

    @Override
    public List<Permission> findByRoleId(Long roleId) {
        return getBaseMapper().findByRoleId(roleId);
    }

    @Override
    public void create(CreatePermissionRequest request) {
        Permission permission = request.convert(Permission.class);
        this.save(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBatch(List<CreatePermissionRequest> request) {
        List<Permission> list = request.stream().map(dto -> dto.convert(Permission.class))
            .toList();
        this.saveBatch(list);
    }

    @Override
    public List<PermissionDto> findAllMenu() {
        return lambdaQuery().eq(Permission::getType, "MENU")
            .list()
            .stream()
            .map(permission -> permission.convert(PermissionDto.class))
            .toList();
    }

    @Override
    public List<Tree<Long>> findAllMenuTree() {
        List<Permission> permissions = lambdaQuery().eq(Permission::getType, "MENU")
            .orderByAsc(Permission::getOrder)
            .list()
            .stream()
            .toList();
        return PermissionUtil.toTreeNode(permissions, null);
    }

    @Override
    public List<Permission> findButtonAndApi(Long parentId) {
        return lambdaQuery().in(Permission::getType, "BUTTON", "API")
            .orderByAsc(Permission::getOrder)
            .list()
            .stream()
            .toList();
    }

    @Override
    public boolean validateMenuPath(String path) {
        return lambdaQuery().eq(Permission::getPath, path).exists();
    }

}
