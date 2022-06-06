package com.volunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.dto.Result;
import com.volunteer.entity.TopicComments;

import java.util.List;

public interface ITopicCommentsService extends IService<TopicComments> {
    Result<List<TopicComments>> queryTopicCommentsByTopicId(Long id);
}
