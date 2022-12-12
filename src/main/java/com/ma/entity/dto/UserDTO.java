package com.ma.entity.dto;

import com.ma.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * DTO承载前端传到后端的参数数据，比如等于页面用户输入的账号和密码
 * */
@Data
public class UserDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * token
     * */
    private String token;

    private String role;
    private List<Menu> menus;
}
