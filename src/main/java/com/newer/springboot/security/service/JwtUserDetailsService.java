package com.newer.springboot.security.service;

import com.newer.springboot.pojo.User;
import com.newer.springboot.mapper.UserMapper;
import com.newer.springboot.security.domain.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详细信息的服务类
 *
 * @author SONG
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
  @Autowired
  private UserMapper userMapper;

  /**
   * 从用户名可以查到用户
   *
   * @param username
   * @return
   * @throws UsernameNotFoundException
   */
  @Cacheable(value = "user.loadUserByUsername")
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userMapper.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
    } else {
      return JwtUserFactory.create(user);
    }
  }
}
