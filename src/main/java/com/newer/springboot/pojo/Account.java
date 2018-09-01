package com.newer.springboot.pojo;

import java.io.Serializable;

/**
 * 账户实体类
 *
 * @author SONG
 */
public class Account implements Serializable {
  private static final long serialVersionUID = -1698077829902230203L;
  private Integer id;
  private String accountId;

  private String password;
  private Double remaining;

  public Account() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Double getRemaining() {
    return remaining;
  }

  public void setRemaining(Double remaining) {
    this.remaining = remaining;
  }
}
