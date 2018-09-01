package com.newer.springboot.controller;

import com.newer.springboot.pojo.Account;
import com.newer.springboot.pojo.CustomMessage;
import com.newer.springboot.service.AccountService;
import com.newer.springboot.util.MD5;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class AccountController {
  @Autowired
  private AccountService accountService;

  // POST: http://127.0.0.1:8086/api/accounts_login 两个表单参数
  @PostMapping("accounts_login")
  public ResponseEntity<?> login(
      @RequestParam("accountId") String accountId,
      @RequestParam("password") String password) {
    // 使用 MD5 加密
    password = MD5.getInstance().getMD5ofStr(password);
    Account account = accountService.login(accountId, password);
    if (account != null) {
      // 不显示明文密码
      account.setPassword(replaceAction(account.getPassword(), "\\w(?=\\w{0})"));
    }

    return new ResponseEntity<>(account, HttpStatus.OK);
  }

  /**
   * String的替换,以达到保密效果
   *
   * @param str
   * @param regular
   * @return
   */
  private static String replaceAction(String str, String regular) {
    return str.replaceAll(regular, "*");
  }

  // POST: http://127.0.0.1:8086/api/accounts_trans 三个表单参数
  @PostMapping("accounts_trans")
  public ResponseEntity<?> trans(
      @RequestParam("fromId") Integer fromId,
      @RequestParam("toAccountId") String toAccountId,
      @RequestParam("money") Double money) {
    CustomMessage customMessage = accountService.trans(fromId, toAccountId, money);
    return new ResponseEntity<>(customMessage, HttpStatus.OK);
  }

  // GET: http://127.0.0.1:8086/api/accounts/10
  @RequestMapping(value = "accounts/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
    Account account = accountService.find(id);
    if (account != null) {
      // 不显示明文密码
      account.setPassword(replaceAction(account.getPassword(), "\\w(?=\\w{0})"));
    }

    return new ResponseEntity<>(account, HttpStatus.OK);
  }

  // POST: http://127.0.0.1:8086/api/accounts Account 中的属性
  @RequestMapping(value = "accounts", method = RequestMethod.POST)
  public ResponseEntity<?> add(@RequestBody Account account) {
    CustomMessage customMessage = accountService.add(account);
    return new ResponseEntity<>(customMessage, HttpStatus.OK);
  }

  // PUT: http://127.0.0.1:8086/api/accounts 三个表单参数
  @RequestMapping(value = "accounts", method = RequestMethod.PUT)
  public ResponseEntity<?> modify(
      @RequestParam("id") Integer id,
      @RequestParam("money") Double money,
      @RequestParam("tradeType") String tradeType) {
    CustomMessage customMessage = accountService.modify(id, money, tradeType);
    return new ResponseEntity<>(customMessage, HttpStatus.OK);
  }
}
