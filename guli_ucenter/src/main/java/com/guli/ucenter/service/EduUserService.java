package com.guli.ucenter.service;

import com.guli.ucenter.entity.EduUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-12-11
 */
public interface EduUserService extends IService<EduUser> {
    /**
     * 统计注册人数
     * @return
     */
    Integer getRegistCount(String day);
}
