package com.daniu.service;

import com.daniu.domain.dto.PermissionDto;
import com.daniu.domain.entity.Permission;
import com.daniu.domain.request.CreatePermissionRequest;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 权限服务类
 *
 * @author FangDaniu
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据角色id获取所有权限
     *
     * @param roleId 角色id
     * @return 权限
     */
    List<Permission> findByRoleId(Long roleId);


    /**
     * 新建权限
     *
     * @param request 请求
     */
    void create(CreatePermissionRequest request);

    /**
     * 批量创建
     *
     * @param request req
     */
    void createBatch(List<CreatePermissionRequest> request);

    /**
     * 查询所有菜单权限
     *
     * @return 菜单权限
     */
    List<PermissionDto> findAllMenu();

    /**
     * 查询所有菜单权限，并构建树
     *
     * @return 菜单权限树
     */
    List<Tree<Long>> findAllMenuTree();

    /**
     * 查询按钮、api
     *
     * @param parentId 父节点id
     * @return 权限列表
     */
    List<Permission> findButtonAndApi(Long parentId);

    /**
     * 校验 path 存不存在menu资源内
     *
     * @param path path
     * @return 是否存在
     */
    boolean validateMenuPath(String path);
}
