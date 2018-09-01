package com.newer.springboot;

import com.newer.springboot.pojo.Account;
import com.newer.springboot.pojo.CustomMessage;
import com.newer.springboot.pojo.Trade;
import com.newer.springboot.service.AccountService;
import com.newer.springboot.service.TradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

  @Autowired
  private AccountService accountService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TradeService tradeService;

  @Test
  public void redisCache() {
    Integer accountId = 3;
    String tradeType = null;
    Date beginDate = null;
    Date endDate = null;
    List<Trade> trades = tradeService.find(accountId, tradeType, beginDate, endDate);

    trades.forEach(trade -> {
      System.out.println(trade.getTradeType() + " " + trade.getMoney());
    });

    for (int i = 0; i < 3; i++) {
      System.out.println(tradeService.find(accountId, tradeType, beginDate, endDate).size());;
    }
  }

  @Test
  public void getPassword() {
    /**
     * 任何应用考虑到安全,绝不能明文的方式保存密码。
     * 密码应该通过哈希算法进行加密。
     * 有很多标准的算法比如SHA或者MD5,结合salt(盐)是一个不错的选择。
     * Spring Security 提供了BCryptPasswordEncoder类,
     * 实现Spring的PasswordEncoder接口使用BCrypt强哈希方法来加密密码。
     *
     * BCrypt强哈希方法:每次加密的结果都不一样。
     */
    System.out.println("-" + passwordEncoder.encode("4302") + "-");
  }

  @Test
  public void trans() {
    Integer fromId = 3;
    String toAccountId = "4301";
    Double money = 100d;

    CustomMessage customMessage = accountService.trans(fromId, toAccountId, money);
    System.out.println(customMessage.getCode() + " " + customMessage.getMessage());
  }

  @Test
  public void addAccount() {
    Account account = new Account();
    account.setAccountId("4303");
    account.setPassword("4303");
    account.setRemaining(30000d);
    CustomMessage customMessage = accountService.add(account);
    if (customMessage.getCode() == 200) {
      System.out.println("开户成功");
    } else {
      System.out.println("开户失败");
    }
  }

}
