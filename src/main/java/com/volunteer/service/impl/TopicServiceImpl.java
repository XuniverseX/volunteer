package com.volunteer.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.dto.Result;
import com.volunteer.entity.Topic;
import com.volunteer.entity.User;
import com.volunteer.mapper.TopicMapper;
import com.volunteer.service.ITopicService;
import com.volunteer.service.IUserService;
import com.volunteer.utils.RedisConstants;
import com.volunteer.utils.SystemConstants;
import com.volunteer.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IUserService userService;

    @Override
    public Result<Object> likeTopic(Long id) {
        String userId = UserHolder.getUser().getOpenid();

        String key = RedisConstants.TOPIC_LIKED_KEY + id;
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, userId);
        if (BooleanUtil.isFalse(isMember)) {
            boolean isSuccess = update().setSql("liked = liked + 1").eq("id", id).update();

            if (isSuccess) {
                stringRedisTemplate.opsForSet().add(key, userId);
            }
        } else {
            boolean isSuccess = update().setSql("liked = liked - 1").eq("id", id).update();

            if (isSuccess) {
                stringRedisTemplate.opsForSet().remove(key, userId);
            }
        }
        return Result.success();
    }



    @Override
    public Result<List<Topic>> queryHotTopic(Integer current) {
        Page<Topic> page = query().orderByDesc("liked").page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        List<Topic> records = page.getRecords();
        records.forEach(topic -> {
            this.queryTopicUser(topic);
            this.isTopicLiked(topic);
        });
        return Result.success(records);
    }

    @Override
    public Result<Topic> queryTopicById(Long id) {
        Topic topic = getById(id);
        if (topic == null) {
            return Result.fail("博客不存在");
        }
        this.queryTopicUser(topic);
        this.isTopicLiked(topic);
        return Result.success(topic);
    }

    private void isTopicLiked(Topic topic) {
        String userId = UserHolder.getUser().getOpenid();

        String key = RedisConstants.TOPIC_LIKED_KEY + topic.getId();
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, userId);
        topic.setIsLike(BooleanUtil.isTrue(isMember));
    }

    private void queryTopicUser(Topic topic) {
        String userId = topic.getUserId();
        User user = userService.getById(userId);
        topic.setName(user.getNickname());
        topic.setIcon(user.getIcon());
    }
}
