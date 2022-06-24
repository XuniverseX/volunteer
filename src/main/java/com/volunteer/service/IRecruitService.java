package com.volunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.dto.Result;
import com.volunteer.entity.Recruit;

import java.util.List;

public interface IRecruitService extends IService<Recruit> {
    Result<List<Recruit>> queryRecruitList(Integer current);

    Result<Object> apply(Long id, String userId);
}
