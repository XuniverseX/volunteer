package com.volunteer.controller;

import com.volunteer.dto.Result;
import com.volunteer.entity.Recruit;
import com.volunteer.service.IRecruitService;
import com.volunteer.utils.UserHolder;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recruit")
@Api("招募操作")
public class RecruitController {

    @Resource
    private IRecruitService recruitService;

    @PostMapping
    public Result<Long> saveRecruit(@RequestBody Recruit recruit) {
        if (!UserHolder.getUser().isAuth()) {
            return Result.fail("您不是管理员");
        }
        String openid = UserHolder.getUser().getOpenid();
        recruit.setUserId(openid);
        recruitService.save(recruit);
        return Result.success(recruit.getId());
    }

    @GetMapping("/list")
    public Result<List<Recruit>> queryRecruitList(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        return recruitService.queryRecruitList(current);
    }

    @GetMapping("/{id}")
    public Result<Recruit> queryRecruitById(@PathVariable Long id) {
        Recruit recruit = recruitService.getById(id);
        return Result.success(recruit);
    }
}
