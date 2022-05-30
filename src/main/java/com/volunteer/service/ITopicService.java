package com.volunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.dto.Result;
import com.volunteer.entity.Topic;

public interface ITopicService extends IService<Topic> {
    Result likeTopic(Long id);

    Result queryHotTopic(Integer current);

    Result queryTopicById(Long id);
}
