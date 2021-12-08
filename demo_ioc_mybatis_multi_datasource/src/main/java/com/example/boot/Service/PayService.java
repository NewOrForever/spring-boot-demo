package com.example.boot.Service;

import com.example.boot.entity.CapitalAccount;
import com.example.boot.entity.RedPacketAccount;
import com.example.boot.mapper.account.mapper.CapitalAccountMapper;
import com.example.boot.mapper.redAccount.mapper.RedPacketAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName:PayService
 * Package:com.example.boot.Service
 * Description:
 *
 * @Date:2021/12/3 14:10
 * @Author:qs@1.com
 */
@Service
public class PayService {

    @Autowired
    private CapitalAccountMapper capitalAccountMapper;

    @Autowired
    private RedPacketAccountMapper redPacketAccountMapper;

    /**
     * 账户余额减钱
     * @param userId
     * @param account
     */
    @Transactional(rollbackFor = {Exception.class})
    public void payAccount(int userId, int account) {
        CapitalAccount capitalAccount = new CapitalAccount();
        capitalAccount.setUserId(userId);
        CapitalAccount capitalAccountDTO = capitalAccountMapper.selectOne(capitalAccount);
        capitalAccountDTO.setBalanceAmount(capitalAccountDTO.getBalanceAmount() - account);
        capitalAccountMapper.updateByPrimaryKey(capitalAccountDTO);
    }

    /**
     * 红包余额加钱
     * @param userId
     * @param account
     */
    @Transactional(rollbackFor = {Exception.class})
    public void payRedPacket(int userId, int account) {
        RedPacketAccount ra = new RedPacketAccount();
        ra.setUserId(userId);
        RedPacketAccount redPacketDTO = redPacketAccountMapper.selectOne(ra);
        redPacketDTO.setBalanceAmount(redPacketDTO.getBalanceAmount() + account);
        redPacketAccountMapper.updateByPrimaryKey(redPacketDTO);

        //int i = 9/0;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void pay(int fromUserId, int toUserId, int account) {
        // 账户余额 减钱
        payAccount(fromUserId, account);
        // 红包余额 加钱
        payRedPacket(toUserId, account);
    }

}
