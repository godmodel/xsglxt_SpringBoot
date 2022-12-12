package com.ma.service;

import com.ma.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ma.entity.dto.UserDTO;
import com.ma.entity.dto.UserPasswordDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ma
 * @since 2022-09-25
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}
