package com.volunteer.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class WechatUtil {

    // 通过wx.login获取的code获得openId和sessionKey
    public static JSONObject getSessionKeyOrOpenId(String code) {
        // GET
        // https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String requestUrl = url.replace("APPID", WechatConstants.APPID).replace("SECRET", WechatConstants.APP_SECRET).replace("JSCODE", code);
        return JSONUtil.parseObj(HttpUtil.get(requestUrl));
    }
}
