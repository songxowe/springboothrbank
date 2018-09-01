package com.newer.springboot.logback;

import java.io.Serializable;
import java.util.Date;

/**
 * @author SONG
 */
public class MyLog implements Serializable {
  private static final long serialVersionUID = -3327154185821755736L;
  private String id;
  private Date ts;
  private String level;
  private String msg;
  private String thread;

  public MyLog() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getTs() {
    return ts;
  }

  public void setTs(Date ts) {
    this.ts = ts;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getThread() {
    return thread;
  }

  public void setThread(String thread) {
    this.thread = thread;
  }
}
