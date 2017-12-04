package com.d6td.lucky.controller;

import com.d6td.lucky.model.Member;
import com.d6td.lucky.model.RequestMessage;
import com.d6td.lucky.model.ResponseMessage;
import com.d6td.lucky.service.MemberService;
import com.d6td.lucky.util.MessageUtils;
import com.d6td.lucky.util.SessionUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 微信游戏模块
 *
 * @author xuxinlong
 * @version 2017年07月20日
 */
@RestController
@RequestMapping(value = "/game")
public class GameController {
    @Resource
    private WxMpService wxMpService;
    @Resource
    private MemberService memberService;

    @Value("${wechat.mp.host}")
    private String host;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 微信游戏首页
     *
     * @param request
     * @throws IOException
     */
    @GetMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("index");
        String code = request.getParameter("code");
        if (code == null) {
            Member member = SessionUtils.getMember();
            if (member == null) {
                // 构造获取授权链接
                String url = wxMpService.oauth2buildAuthorizationUrl(
                        host + "/game/index",
                        WxConsts.OAuth2Scope.SNSAPI_USERINFO, "");
                return new ModelAndView("redirect:" + url);
            }
        } else {
            try {
                WxMpOAuth2AccessToken oauth2AccessToken = wxMpService.oauth2getAccessToken(code);
                // 授权信息获取用户
                WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(oauth2AccessToken, "zh_CN");
                //新用户注册
                Member member = memberService.getMemberByOpenId(wxMpUser.getOpenId());
                if (member == null) {
                    member = new Member();
                    member.setOpenId(wxMpUser.getOpenId());
                }
                member.setName(wxMpUser.getNickname());
                member.setHeadImg(wxMpUser.getHeadImgUrl());
                memberService.saveMember(member);
                SessionUtils.setMember(memberService.getMemberByMemberId(member.getMemberId()));
                mav.addObject("member", member);
            } catch (WxErrorException e) {
                this.logger.error("\n接收到来自微信登录认证失败：" + e.getMessage());
            }
        }
        return mav;
    }

    /**
     * 获取参与人数
     *
     * @return
     */
    @GetMapping(value = "/getParticipants")
    public HashMap<String, Object> getParticipants() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            retMap.put("num", memberService.getMemberCount());
        } catch (Exception e) {
            retMap = MessageUtils.error(e.getMessage());
        }
        return retMap;
    }

    /**
     * 根据数字限制随机获取参与人员列表
     *
     * @param num
     * @return
     */
    @GetMapping(value = "/getMemberList")
    public HashMap<String, Object> getMemberList(@RequestParam(defaultValue = "9999") Integer num) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            List<Member> memberList = memberService.getMemberListLimitNum(num);
            retMap.put("list", memberList);
        } catch (Exception e) {
            retMap = MessageUtils.error(e.getMessage());
        }
        return retMap;
    }

    /**
     * 抽中中奖用户更新标识
     *
     * @param memberId
     * @return
     */
    @GetMapping(value = "/luckyMember")
    public HashMap<String, Object> luckyMember(@RequestParam Integer memberId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            memberService.luckyMember(memberId);
        } catch (Exception e) {
            retMap = MessageUtils.error(e.getMessage());
        }
        return retMap;
    }

    /**
     * 特殊参与者开启下次必中
     * @param memberId
     * @return
     */
    @PostMapping(value = "/willBeCheck")
    public HashMap<String, Object> willBeCheck(@RequestParam Integer memberId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member member = memberService.getMemberByMemberId(memberId);
            if (!member.getSpecial()){
                retMap = MessageUtils.error("您暂无权限设置");
            } else if (member.getLucky()){
                retMap = MessageUtils.error("您已经中奖啦");
            } else {
                memberService.willBeCheck(memberId);
            }
        } catch (Exception e) {
            retMap = MessageUtils.error(e.getMessage());
        }
        return retMap;
    }


    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage say(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }

    @GetMapping("ws")
    public ModelAndView ws() {
        return new ModelAndView("ws");
    }
}
