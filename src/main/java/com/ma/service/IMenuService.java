package com.ma.service;

import com.ma.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ma
 * @since 2022-11-22
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
