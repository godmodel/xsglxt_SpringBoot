package com.ma.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ma.common.Constants;
import com.ma.entity.Menu;
import com.ma.entity.User;
import com.ma.entity.dto.UserDTO;
import com.ma.entity.dto.UserPasswordDTO;
import com.ma.exception.ServiceException;
import com.ma.mapper.RoleMapper;
import com.ma.mapper.RoleMenuMapper;
import com.ma.mapper.UserMapper;
import com.ma.service.IMenuService;
import com.ma.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ma
 * @since 2022-09-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    private static final Log LOG= Log.get();
    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",userDTO.getUsername());
        wrapper.eq("password",userDTO.getPassword());
        User one;
            try {
                one = getOne(wrapper);
            }catch (Exception e){
                LOG.error(e);
                throw new ServiceException(Constants.CODE_500,"系统错误");
            }
            if (one !=null){
                BeanUtil.copyProperties(one,userDTO,true);
                //设置token
                String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
                userDTO.setToken(token);

                String role = one.getRole();


                List<Menu> roleMenus = getRoleMenus(role);
                userDTO.setMenus(roleMenus);
                return userDTO;
            }else {
                throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
            }
    }

    @Override
    public User register(UserDTO userDTO) {
        //先检查数据库中是否已经存在此用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",userDTO.getUsername());
        wrapper.eq("password",userDTO.getPassword());
        User one;
        try {
            one = getOne(wrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if (one == null){
            one= new User();
            BeanUtil.copyProperties(userDTO,one,true);
            save(one);
        }else {
            throw new ServiceException(Constants.CODE_500,"用户已存在");
        }
        return one;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {

        int update=userMapper.updatePassword(userPasswordDTO);
        if (update<1){
            throw new ServiceException(Constants.CODE_600,"密码错误");
        }
    }
    private List<Menu> getRoleMenus(String roleFlag){
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        List<Menu> menus = menuService.findMenus("");
        ArrayList<Menu> roleMenus = new ArrayList<>();

        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}
