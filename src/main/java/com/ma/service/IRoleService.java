package com.ma.service;

import com.ma.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ma
 * @since 2022-11-16
 */
public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer reloId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer reloId);
}
