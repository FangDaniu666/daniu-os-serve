package com.daniu.mapper;

import com.daniu.domain.dto.UserPageDto;
import com.daniu.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper
 *
 * @author FangDaniu
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询
     *
     * @param page     分页
     * @param username 用户名
     * @param gender   性别
     * @param enable   状态
     * @return 分页结果
     */
    IPage<UserPageDto> pageDetail(IPage<User> page, @Param("username") String username, @Param("gender") Integer gender,
                                  @Param("enable")
                                  Boolean enable);

}
