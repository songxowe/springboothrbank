package com.newer.springboot.pojo;

import java.io.Serializable;

/**
 * 自定义信息
 *
 * @author SONG
 */
public class CustomMessage implements Serializable {
  private static final long serialVersionUID = 314549928434875151L;
  private Integer code;
  private String message;

  public CustomMessage() {
  }

  public CustomMessage(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
