package com.newer.springboot.service;

import com.newer.springboot.mapper.AccountMapper;
import com.newer.springboot.mapper.TradeMapper;
import com.newer.springboot.pojo.Account;
import com.newer.springboot.pojo.CustomMessage;
import com.newer.springboot.pojo.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 账户业务逻辑
 *
 * @author SONG
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class AccountService {

  @Autowired
  private AccountMapper accountMapper;
  @Autowired
  private TradeMapper tradeMapper;

  /**
   * 转账
   *
   * @param fromId      转出账户 id
   * @param toAccountId 转入账号
   * @param money       转账金额
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  @CachePut(cacheNames = "trade.find")
  public CustomMessage trans(Integer fromId, String toAccountId, Double money) {
    int count = 0;
    CustomMessage customMessage = new CustomMessage();

    // 1.转入账户是否有效
    Account toAccount = accountMapper.findByAccountId(toAccountId);
    if (toAccount == null) {
      customMessage.setCode(400);
      customMessage.setMessage("转入账户不存在!");
      return customMessage;
    }

    // 2.计算手续费 0.005 [2 ~ 50]
    Double fee = money * 0.005;
    if (fee < 2d) {
      fee = 2d;
    } else if (fee > 50d) {
      fee = 50d;
    }

    // 3.转出账户余额是否足够 (余额 - 转账金额 - 手续费)
    Account fromAccount = this.find(fromId);
    if (fromAccount.getRemaining() < money + fee) {
      customMessage.setCode(400);
      customMessage.setMessage("余额不足!");
      return customMessage;
    }

    // 4.更新转出账户余额
    fromAccount.setRemaining(fromAccount.getRemaining() - money - fee);
    accountMapper.modify(fromAccount);

    // 5.更新转入账户余额
    toAccount.setRemaining(toAccount.getRemaining() + money);
    accountMapper.modify(toAccount);

    // 6.新增转出账户的交易明细
    Trade trade = new Trade();
    trade.setAccountId(fromAccount.getId()); // 设置外键
    trade.setTradeType("转账"); // 开户存款
    trade.setMoney(money);
    trade.setTradeTime(new Date());
    trade.setRemark("转出: 接收账户 - " + toAccount.getAccountId());
    count = tradeMapper.add(trade);

    // 7.新增转入账户的交易明细
    trade = new Trade();
    trade.setAccountId(toAccount.getId()); // 设置外键
    trade.setTradeType("转账"); // 开户存款
    trade.setMoney(money);
    trade.setTradeTime(new Date());
    trade.setRemark("转入: 来自账户 - " + fromAccount.getAccountId());
    count = tradeMapper.add(trade);

    customMessage.setCode(200);
    customMessage.setMessage("转账成功!");
    return customMessage;
  }

  /**
   * 开户
   *
   * @param account
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  @CachePut(cacheNames = "trade.find")
  public CustomMessage add(Account account) {
    int count = 0;
    CustomMessage customMessage = new CustomMessage();

    // 1.开户 - 新增账户
    count = accountMapper.add(account);
    if (count == 0) {
      customMessage.setCode(400);
      customMessage.setMessage("新增账户失败!");
      return customMessage;
    }

    // 2.交易 - 新增一条交易记录
    Trade trade = new Trade();
    trade.setAccountId(account.getId()); // 设置外键
    trade.setTradeType("存款"); // 开户存款
    trade.setMoney(account.getRemaining());
    trade.setTradeTime(new Date());
    trade.setRemark("开户存款");
    count = tradeMapper.add(trade);
    if (count == 0) {
      customMessage.setCode(400);
      customMessage.setMessage("开户时新增交易记录失败!");
      return customMessage;
    }

    customMessage.setCode(200);
    customMessage.setMessage("开户成功!");
    return customMessage;
  }

  /**
   * 账户的存款 | 取款操作
   *
   * @param id        当前账户 id
   * @param money     金额
   * @param tradeType 存款 | 取款
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @CachePut(cacheNames = "trade.find")
  public CustomMessage modify(Integer id, Double money, String tradeType) {
    int count = 0;
    CustomMessage customMessage = new CustomMessage();

    Account account = this.find(id);

    if ("存款".equals(tradeType)) {
      account.setRemaining(account.getRemaining() + money);
    } else {
      if (account.getRemaining() < money) {
        customMessage.setCode(400);
        customMessage.setMessage("账户余额不足!");
        return customMessage;
      }
      account.setRemaining(account.getRemaining() - money);
    }
    count = accountMapper.modify(account);

    Trade trade = new Trade();
    trade.setAccountId(account.getId()); // 设置外键
    trade.setTradeType(tradeType);
    trade.setMoney(money);
    trade.setTradeTime(new Date());
    trade.setRemark(tradeType);
    count = tradeMapper.add(trade);

    customMessage.setCode(200);
    customMessage.setMessage(tradeType + " 操作成功!");
    return customMessage;
  }

  public Account find(Integer id) {
    return accountMapper.findById(id);
  }

  public Account login(String accountId, String password) {
    return accountMapper.login(accountId, password);
  }
}
