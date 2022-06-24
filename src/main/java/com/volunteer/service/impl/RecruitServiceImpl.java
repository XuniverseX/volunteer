package com.volunteer.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.dto.Result;
import com.volunteer.entity.RU;
import com.volunteer.entity.Recruit;
import com.volunteer.mapper.RecruitMapper;
import com.volunteer.service.IRUService;
import com.volunteer.service.IRecruitService;
import com.volunteer.service.IUserService;
import com.volunteer.utils.SystemConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecruitServiceImpl extends ServiceImpl<RecruitMapper, Recruit> implements IRecruitService {

    @Resource
    private IRUService ruService;

    @Resource
    private IUserService userService;

    @Override
    public Result<List<Recruit>> queryRecruitList(Integer current) {
        Page<Recruit> page = query().page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        return Result.success(page.getRecords());
    }

    @Override
    public Result<Object> apply(Long id, String userId) {
        int UCount = userService.query().eq("user_id", userId).count();
        int RCount = query().eq("id", id).count();
        if (UCount == 0 || RCount == 0) {
            return Result.fail("报名失败");
        }
        int count = ruService.query().eq("user_id", userId).eq("recruit_id", id).count();
        if (count > 0) {
            return Result.fail("不可重复报名");
        }
        RU ru = new RU();
        ru.setUserId(userId);
        ru.setRecruitId(id);
        ruService.save(ru);
        return Result.success("报名成功");
    }
}
