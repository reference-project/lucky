package com.d6td.lucky.util;

import com.d6td.lucky.model.Member;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SessionUtils {

    private static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把会员信息存放到session中
     *
     * @param member
     */
    public static void setMember(Member member) {
        getRequest().getSession().setAttribute("participator", member);
    }

    /**
     * 获取当前登录的会员
     */
    public static Member getMember() {
        return (Member) getRequest().getSession().getAttribute("participator");
    }
}
 