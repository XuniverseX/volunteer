package com.volunteer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.volunteer.dto.Result;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.Topic;
import com.volunteer.service.ITopicService;
import com.volunteer.utils.SystemConstants;
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
 * @since 2022-5-17
 */
@Slf4j
@RestController
@RequestMapping("/topic")
@Api("话题操作")
public class TopicController {

    @Resource
    private ITopicService topicService;

    @PostMapping
    @ApiOperation("保存话题")
    public Result<Long> saveTopic(@RequestBody Topic topic) {
        UserDTO user = UserHolder.getUser();
        topic.setUserId(user.getOpenid());
        topicService.save(topic);
        return Result.success(topic.getId());
    }

    @GetMapping("like/{id}")
    @ApiOperation("根据话题id点赞话题")
    public Result<Object> likeTopic(@PathVariable("id") Long id) {
        return topicService.likeTopic(id);
    }

    @GetMapping("/of/me")
    @ApiOperation("查询我的话题")
    public Result<List<Topic>> queryMyTopic(@RequestParam(name = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 根据用户查询
        Page<Topic> page = topicService.query()
                .eq("user_id", user.getOpenid()).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Topic> records = page.getRecords();
        return Result.success(records);
    }

    @GetMapping("/hot")
    @ApiOperation("查询首页热门话题")
    public Result<List<Topic>> queryHotTopic(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        return topicService.queryHotTopic(current);
    }

    @GetMapping("/{id}")
    @ApiOperation("通过话题Id查询话题")
    public Result<Topic> queryTopicById(@PathVariable("id") Long id) {
        return topicService.queryTopicById(id);
    }
}
