package com.volunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.entity.User;
import com.volunteer.dto.Result;

public interface IUserService extends IService<User> {
    Result login(String code);

    Result sign();

    Result signCount();
}
