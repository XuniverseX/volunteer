package com.volunteer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.dto.Result;
import com.volunteer.entity.TopicComments;
import com.volunteer.mapper.TopicCommentsMapper;
import com.volunteer.service.ITopicCommentsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicCommentsServiceImpl extends ServiceImpl<TopicCommentsMapper, TopicComments> implements ITopicCommentsService {

    @Override
    public Result queryTopicCommentsByTopicId(Long id) {
        List<TopicComments> topicComments = query().eq("topicId", id).list();
        return Result.ok(topicComments);
    }


}
