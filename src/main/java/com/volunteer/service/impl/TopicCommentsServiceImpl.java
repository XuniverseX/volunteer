package com.volunteer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.entity.TopicComments;
import com.volunteer.mapper.TopicCommentsMapper;
import com.volunteer.service.ITopicCommentsService;
import org.springframework.stereotype.Service;

@Service
public class TopicCommentsServiceImpl extends ServiceImpl<TopicCommentsMapper, TopicComments> implements ITopicCommentsService {

}
