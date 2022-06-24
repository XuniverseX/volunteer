package com.volunteer.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.dto.Result;
import com.volunteer.entity.Recruit;
import com.volunteer.mapper.RecruitMapper;
import com.volunteer.service.IRecruitService;
import com.volunteer.utils.SystemConstants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitServiceImpl extends ServiceImpl<RecruitMapper, Recruit> implements IRecruitService {
    @Override
    public Result<List<Recruit>> queryRecruitList(Integer current) {
        Page<Recruit> page = query().page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        return Result.success(page.getRecords());
    }
}
