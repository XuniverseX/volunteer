package com.volunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.dto.Result;
import com.volunteer.entity.TopicComments;

public interface ITopicCommentsService extends IService<TopicComments> {
    Result queryTopicCommentsById(Long id);
}
