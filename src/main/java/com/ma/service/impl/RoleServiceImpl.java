package com.ma.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ma.entity.Menu;
import com.ma.entity.Role;
import com.ma.entity.RoleMenu;
import com.ma.mapper.RoleMapper;
import com.ma.mapper.RoleMenuMapper;
import com.ma.service.IMenuService;
import com.ma.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ma
 * @since 2022-11-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;
    @Override
    @Transactional
    public void setRoleMenu(Integer reloId, List<Integer> menuIds) {
        roleMenuMapper.deleteByRoleId(reloId);

        List<Integer> menuIdsCopy= CollUtil.newArrayList(menuIds);
        for (Integer menuId : menuIds) {
            Menu menu= menuService.getById(menuId);
            if (menu.getPid()!=null && menuIds.contains(menu.getPid())){

            }else {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(reloId);
                roleMenu.setRoleId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(reloId);
            roleMenu.setRoleId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer reloId) {
        return roleMenuMapper.selectByRoleId(reloId);
    }
}
