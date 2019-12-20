package com.guli.ucenter.mapper;

import com.guli.ucenter.entity.EduUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-12-11
 */
public interface EduUserMapper extends BaseMapper<EduUser> {
    Integer getRegistCount(String day);
}
