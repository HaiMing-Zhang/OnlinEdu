package com.guli.ucenter.controller;

import com.google.gson.Gson;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.entity.Result;
import com.guli.common.exception.GuliException;
import com.guli.ucenter.service.WxService;
import com.guli.ucenter.utils.ConstantPropertiesUtil;
import com.guli.ucenter.utils.HttpClientUtils;
import com.guli.ucenter.utils.JwtUtils;
import com.sun.deploy.net.URLEncoder;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
@Api(tags = "微信登陆模块")
@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxController {
    @Autowired
    private WxService wxService;
    @GetMapping("callback")
    public String callback(String code,String state,HttpSession session){
        //得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("获取 state = " + state);
        // 判断state是否合法
        String stateStr = (String)session.getAttribute("wx-open-state");
        System.out.println("存储 state = " + stateStr);
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(state) || !state.equals(state)) {
            throw new GuliException("state不合法");
        }
        // 通过code获取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code
        );
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        //将access_token和openId从json字符串中去除来
        Gson gson = new Gson();
        Map map = gson.fromJson(result, Map.class);
        String access_token = (String) map.get("access_token");
        String openId = (String) map.get("openid");

        //从微信获取用户信息
        //获取用户基本信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        String userInfoUrl = String.format(baseUserInfoUrl, access_token, openId);
        String userInfo = null;
        //判断拼接后的字符串是否为空,若不为空则利用工具类获取用户信息
        if(!StringUtils.isEmpty(userInfoUrl)){
            try {
                userInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //获取微信用户的openid,nickname,headimgurl并存入数据库
        Map vxUserInfo = gson.fromJson(userInfo, Map.class);
        String openid = (String) vxUserInfo.get("openid");
        String nickName = (String)vxUserInfo.get("nickname");
        String headImgUrl = (String)vxUserInfo.get("headimgurl");
        Double sex = (Double) vxUserInfo.get("sex");

        String token = wxService.saveWxUser(openid,nickName,headImgUrl,sex);
        if(!StringUtils.isEmpty(token)){
            return "redirect:http://localhost:3000?token="+token;
        }else {
            throw new GuliException("微信登陆错误");
        }
    }

    @ApiOperation(value = "解析jwt")
    @PostMapping("/parseJwt")
    @ResponseBody
    public Result parseJwt(@RequestBody String jwtToken){
        Claims claims = JwtUtils.checkJWT(jwtToken);
        String id = (String) claims.get("id");
        String nickName = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        System.err.println(id);
        System.err.println(nickName);
        System.err.println(avatar);
        return Result.ok().data("id",id).data("avatar",avatar).data("nickname",nickName);
    }


    @GetMapping("login")
    public String genQrConnect(HttpSession session){
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException("编码错误");
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        String state = "gulilogin";
        session.setAttribute("wx-open-state",state);
        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state
        );

        return "redirect:" + qrcodeUrl;
    }
}
