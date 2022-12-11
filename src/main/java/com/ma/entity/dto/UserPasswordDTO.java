package com.ma.entity.dto;

import lombok.Data;

/**
 * DTO承载前端传到后端的参数数据，比如等于页面用户输入的账号和密码
 * */
@Data
public class UserPasswordDTO {
    private String username;
    private String phone;
    private String password;
    private String newPassword;
}
