package com.guli.ucenter.service.impl;

import com.guli.ucenter.entity.EduUser;
import com.guli.ucenter.mapper.EduUserMapper;
import com.guli.ucenter.service.EduUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-12-11
 */
@Service
public class EduUserServiceImpl extends ServiceImpl<EduUserMapper, EduUser> implements EduUserService {

    @Override
    public Integer getRegistCount(String day) {
        Integer registDay = this.baseMapper.getRegistCount(day);
        return registDay;
    }
}
