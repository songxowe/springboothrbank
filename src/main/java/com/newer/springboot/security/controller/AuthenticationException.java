package com.newer.springboot.security.controller;

/**
 * 授权异常类
 *
 * @author SONG
 */
public class AuthenticationException extends RuntimeException {
  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
