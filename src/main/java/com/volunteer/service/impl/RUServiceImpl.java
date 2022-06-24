package com.volunteer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.entity.RU;
import com.volunteer.mapper.RUMapper;
import com.volunteer.service.IRUService;
import org.springframework.stereotype.Service;

@Service
public class RUServiceImpl extends ServiceImpl<RUMapper, RU> implements IRUService {
}
