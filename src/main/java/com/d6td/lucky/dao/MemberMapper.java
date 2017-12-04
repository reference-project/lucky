package com.d6td.lucky.dao;

import com.d6td.lucky.model.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuxinlong
 */
@Repository
public interface MemberMapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    /**
     * 根据openId查询会员
     * @param openId
     * @return
     */
    Member getMemberByOpenId(String openId);

    /**
     * 获取会员人数
     * @return
     */
    int getMemberCount();

    /**
     * 随机取几条数据
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