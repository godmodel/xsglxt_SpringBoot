package com.ma.mapper;

import com.ma.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ma.entity.dto.UserPasswordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ma
 * @since 2022-09-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Update("update sys_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);

}
