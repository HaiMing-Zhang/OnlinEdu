package com.guli.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.ucenter.entity.EduUser;
import com.guli.ucenter.mapper.EduUserMapper;
import com.guli.ucenter.service.EduUserService;
import com.guli.ucenter.service.WxService;
import com.guli.ucenter.utils.JwtUtils;
import org.springframework.stereotype.Service;

@Service
public class WxServiceImpl extends ServiceImpl<EduUserMapper, EduUser> implements WxService {

    /**
     * 向数据库中添加微信用户信息
     * @param openid
     * @param nickName
     * @param headImgUrl
     * @param sex
     * @return
     */
    @Override
    public String saveWxUser(String openid, String nickName, String headImgUrl, Double sex) {
        QueryWrapper<EduUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        EduUser eduUser = this.baseMapper.selectOne(wrapper);
        if(eduUser == null){
            EduUser eduUser1 = new EduUser();
            eduUser1.setOpenid(openid);
            eduUser1.setNickname(nickName);
            eduUser1.setAvatar(headImgUrl);
            eduUser1.setSex(sex.intValue());
            this.save(eduUser1);
            return JwtUtils.generateJWT(eduUser1);
        }else{
            return JwtUtils.generateJWT(eduUser);
        }
    }
}
