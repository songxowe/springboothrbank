package com.newer.springboot.security.domain;

import java.io.Serializable;

/**
 * 已授权用户(令牌)响应给客户端
 *
 * @author SONG
 */
public class JwtAuthenticationResponse implements Serializable {
  private static final long serialVersionUID = -6939373639646474228L;
  private final String token; // 令牌

  public JwtAuthenticationResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
