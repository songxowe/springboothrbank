package com.newer.springboot.security.domain;

import com.newer.springboot.pojo.Role;
import com.newer.springboot.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT 用户工厂类
 *
 * @author SONG
 */
public final class JwtUserFactory {
  private JwtUserFactory() {

  }

  public static JwtUser create(User user) {
    return new JwtUser(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getEmail(),
        mapToGrantedAuthority(user.getRoles()),
        user.getStatus().equals("1") ? true : false,
        user.getLastPasswordResetDate()
    );
  }

  private static List<GrantedAuthority> mapToGrantedAuthority(
      List<Role> roles
  ) {

    return roles.stream().
        map(role -> new SimpleGrantedAuthority(role.getCode())).
        collect(Collectors.toList());
  }
}
