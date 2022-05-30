package com.volunteer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.dto.TokenUserDTO;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.User;
import com.volunteer.mapper.UserMapper;
import com.volunteer.service.IUserService;
import com.volunteer.dto.Result;
import com.volunteer.utils.RedisConstants;
import com.volunteer.utils.UserHolder;
import com.volunteer.utils.WechatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.volunteer.utils.RedisConstants.LOGIN_USER_TTL;
import static com.volunteer.utils.SystemConstants.USER_NICK_NAME_PREFIX;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(String code) {
        // 开发者服务器通过登录凭证获得 sessionId
        JSONObject jsonObject = WechatUtil.getSessionKeyOrOpenId(code);
        // 根据微信接口，获得返回的参数
        String openid = jsonObject.getStr("openid");
        String sessionKey = jsonObject.getStr("session_key");
        if (StrUtil.isBlank(openid) || StrUtil.isBlank(sessionKey)) {
            return Result.fail("SessionId获取失败");
        }

        // 根据openId从数据库查询用户
        User user = query().eq("openid", openid).one();
        // 判断用户是否存在
        if (user == null) {
            // 存入用户
            user = createUserWithOpenId(openid, sessionKey);
        }
        // 随机生成token作为用户令牌
        String token = UUID.randomUUID().toString();
        // 将User对象转为Hash存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreCase(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldName.toString()));
        // 将User存入redis
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 返回token
        return Result.ok(new TokenUserDTO(userDTO, token));
    }

    @Override
    public Result sign() {
        String openid = UserHolder.getUser().getOpenid();
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "sign:" + openid + keySuffix;
        int dayOfMonth = now.getDayOfMonth();
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return Result.ok();
    }

    @Override
    public Result signCount() {
        String openid = UserHolder.getUser().getOpenid();
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "sign:" + openid + keySuffix;
        int dayOfMonth = now.getDayOfMonth();
        List<Long> longs = stringRedisTemplate.opsForValue().bitField(
                key,
                BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );
        if (longs == null || longs.isEmpty()) {
            return Result.ok(0);
        }
        Long num = longs.get(0);
        if (num == null || num == 0) {
            return Result.ok(0);
        }
        int count = 0;
        while (true) {
            if ((num & 1) == 0) {
                break;
            } else {
                count++;
            }
            num >>>= 1;
        }
        return Result.ok(count);
    }

    private User createUserWithOpenId(String openid, String sessionKey) {
        User user = new User();
        user.setOpenid(openid);
        user.setSessionKey(sessionKey);
        user.setNickname(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        user.setCreateTime(LocalDateTime.now());
        save(user);
        return user;
    }
}
