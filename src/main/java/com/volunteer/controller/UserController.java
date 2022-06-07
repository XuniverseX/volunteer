package com.volunteer.controller;

import com.volunteer.dto.TokenUserDTO;
import com.volunteer.dto.UserDTO;
import com.volunteer.service.IUserService;
import com.volunteer.dto.Result;
import com.volunteer.utils.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 *
 * @author Xuni
 * @since 2022-5-16
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api("用户操作")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("/login/{code}")
    @ApiOperation("登录")
    public Result<TokenUserDTO> login(@PathVariable String code) {
        return userService.login(code);
    }

    @PostMapping("/logout")
    @ApiOperation("登出")
    public Result<Object> logout() {
        // TODO 实现登出功能
        return Result.fail("功能未完成");
    }

    @GetMapping("/me")
    @ApiOperation("返回当前登录的用户")
    public Result<UserDTO> me(){
        // 获取当前登录的用户并返回
        UserDTO user = UserHolder.getUser();
        return Result.success(user);
    }

    @PostMapping("/sign")
    @ApiOperation("签到")
    public Result<Object> sign() {
        return userService.sign();
    }

    @GetMapping("/sign/count")
    @ApiOperation("当月签到次数")
    public Result<Integer> signCount() {
        return userService.signCount();
    }
}
