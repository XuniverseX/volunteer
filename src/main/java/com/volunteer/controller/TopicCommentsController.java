package com.volunteer.controller;

import com.volunteer.dto.Result;
import com.volunteer.entity.TopicComments;
import com.volunteer.service.ITopicCommentsService;
import com.volunteer.utils.UserHolder;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result saveTopicComments(TopicComments topicComments) {
        String openid = UserHolder.getUser().getOpenid();
        topicComments.setUserId(openid);
        topicCommentsService.save(topicComments);
        return Result.ok(topicComments.getId());
    }

}
