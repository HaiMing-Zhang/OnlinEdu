package com.guli.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.ucenter.entity.EduUser;

public interface WxService extends IService<EduUser> {
    /**
     *向数据库中添加微信用户信息
     * @return
     */
    String saveWxUser(String openid,String nickName,String headImgUrl,Double sex);
}
