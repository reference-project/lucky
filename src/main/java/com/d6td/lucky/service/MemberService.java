package com.d6td.lucky.service;

import com.d6td.lucky.model.Member;

import java.util.List;

/**
 * @author xuxinlong
 * @version 2017年12月04日
 */
public interface MemberService {

    /**
     * 根据openId查询会员信息
     * @param openId
     * @return
     */
    Member getMemberByOpenId(String openId);

    /**
     * 注册或更新会员信息
     * @param member
     */
    void saveMember(Member member);

    /**
     * 主键查询会员
     * @param memberId
     * @return
     */
    Member getMemberByMemberId(Integer memberId);

    /**
     * 获取会员人数
     * @return
     */
    int getMemberCount();

    /**
     * 随机取多少个会员数据
     * @param num
     * @return
     */
    List<Member> getMemberListLimitNum(Integer num);

    /**
     * 将会员更新为中奖用户
     * @param memberId
     */
    void luckyMember(Integer memberId);

    /**
     * 特殊参与者开启下次必中
     * @param memberId
     */
    void willBeCheck(Integer memberId);
}
