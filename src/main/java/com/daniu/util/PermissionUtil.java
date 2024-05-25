package com.daniu.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.daniu.domain.entity.Permission;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * 权限相关工具类
 *
 * @author FangDaniu
 * @since 2024/05/25
 */
@UtilityClass
public class PermissionUtil {

    /**
     * 生成权限树
     *
     * @param permissions 权限列表
     * @return 权限树
     */
    public List<Tree<Long>> toTreeNode(List<Permission> permissions, Long parent) {
        List<TreeNode<Long>> nodes = permissions.stream().map(permission -> {
            TreeNode<Long> treeNode = new TreeNode<>();
            treeNode.setId(permission.getId());
            treeNode.setParentId(permission.getParentId());
            treeNode.setWeight(permission.getOrder());
            treeNode.setName(permission.getName());
            treeNode.setExtra(BeanUtil.beanToMap(permission));
            return treeNode;
        }).toList();
        return TreeUtil.build(nodes, parent);
    }
}

