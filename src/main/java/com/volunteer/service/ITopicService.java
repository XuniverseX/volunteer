package com.volunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.dto.Result;
import com.volunteer.entity.Topic;

import java.util.List;

public interface ITopicService extends IService<Topic> {
    Result<Object> likeTopic(Long id);

    Result<List<Topic>> queryHotTopic(Integer current);

    Result<Topic> queryTopicById(Long id);
}
