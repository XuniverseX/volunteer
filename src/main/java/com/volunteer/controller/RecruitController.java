package com.volunteer.controller;

import com.volunteer.dto.Result;
import com.volunteer.entity.Recruit;
import com.volunteer.service.IRecruitService;
import com.volunteer.utils.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author Xuni
 * @since 2022-6-24
 */
@Slf4j
@RestController
@RequestMapping("/recruit")
@Api("招募操作")
public class RecruitController {

    @Resource
    private IRecruitService recruitService;

    @PostMapping
    @ApiOperation("发布招募")
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
    @ApiOperation("分页查询招募信息")
    public Result<List<Recruit>> queryRecruitList(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        return recruitService.queryRecruitList(current);
    }

    @GetMapping("/{id}")
    @ApiOperation("通过id查询招募信息")
    public Result<Recruit> queryRecruitById(@PathVariable Long id) {
        Recruit recruit = recruitService.getById(id);
        return Result.success(recruit);
    }

    @PutMapping("/{id}/{userId}")
    @ApiOperation("报名")
    public Result<Object> apply(@PathVariable Long id, @PathVariable String userId) {
        return recruitService.apply(id, userId);
    }
}
