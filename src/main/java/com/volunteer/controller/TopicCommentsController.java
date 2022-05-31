package com.volunteer.controller;

import com.volunteer.dto.Result;
import com.volunteer.entity.TopicComments;
import com.volunteer.service.ITopicCommentsService;
import com.volunteer.utils.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author Xuni
 * @since 2022-5-30
 */
@RestController
@RequestMapping("/topic-comments")
@Api("话题评论操作")
public class TopicCommentsController {

    @Resource
    private ITopicCommentsService topicCommentsService;

    @PostMapping("/save")
    @ApiOperation("保存评论")
    public Result saveTopicComments(@RequestBody TopicComments topicComments) {
        String openid = UserHolder.getUser().getOpenid();
        topicComments.setUserId(openid);
        topicCommentsService.save(topicComments);
        return Result.ok(topicComments.getId());
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询评论")
    public Result queryTopicCommentsById(@PathVariable Long id) {
        return topicCommentsService.queryTopicCommentsByTopicId(id);
    }



}
