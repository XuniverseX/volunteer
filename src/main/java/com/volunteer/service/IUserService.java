package com.volunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.dto.TokenUserDTO;
import com.volunteer.entity.User;
import com.volunteer.dto.Result;

public interface IUserService extends IService<User> {
    Result<TokenUserDTO> login(String code);

    Result<Object> sign();

    Result<Integer> signCount();
}
