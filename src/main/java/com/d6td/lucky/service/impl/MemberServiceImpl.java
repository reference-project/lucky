package com.d6td.lucky.service.impl;

import com.d6td.lucky.dao.MemberMapper;
import com.d6td.lucky.model.Member;
import com.d6td.lucky.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author xuxinlong
 * @version 2017年12月04日
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    /**
     * 根据openId查询会员信息
     *
     * @param openId
     * @return
     */
    @Override
    public Member getMemberByOpenId(String openId) {
        return memberMapper.getMemberByOpenId(openId);
    }

    /**
     * 注册或更新会员信息
     *
     * @param member
     */
    @Override
    public void saveMember(Member member) {
        if (member.getMemberId() != null) {
            memberMapper.updateByPrimaryKeySelective(member);
        } else {
            //注册会员
            member.setCreateTime(new Date());
            String headImg = member.getHeadImg();
            member.setHeadImg(headImg.substring(0, headImg.length()-2));
            memberMapper.insertSelective(member);
        }
    }

    /**
     * 主键查询会员
     *
     * @param memberId
     * @return
     */
    @Override
    public Member getMemberByMemberId(Integer memberId) {
        return memberMapper.selectByPrimaryKey(memberId);
    }

    /**
     * 获取会员人数
     *
     * @return
     */
    @Override
    public int getMemberCount() {
        return memberMapper.getMemberCount();
    }

    /**
     * 随机取多少个会员数据
     *
     * @param num
     * @return
     */
    @Override
    public List<Member> getMemberListLimitNum(Integer num) {
        return memberMapper.getMemberListLimitNum(num);
    }

    /**
     * 将会员更新为中奖用户
     *
     * @param memberId
     */
    @Override
    public void luckyMember(Integer memberId) {
        memberMapper.luckyMember(memberId);
    }

    /**
     * 特殊参与者开启下次必中
     *
     * @param memberId
     */
    @Override
    public void willBeCheck(Integer memberId) {
        memberMapper.willBeCheck(memberId);
    }
}
