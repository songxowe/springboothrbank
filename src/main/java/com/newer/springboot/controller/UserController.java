package com.newer.springboot.controller;

import com.newer.springboot.pojo.CustomMessage;
import com.newer.springboot.pojo.User;
import com.newer.springboot.security.JwtTokenUtil;
import com.newer.springboot.security.domain.JwtUser;
import com.newer.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 获取已授权用户信息
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
public class UserController {
  @Value("${jwt.header}")
  private String tokenHeader;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  @Qualifier("jwtUserDetailsService")
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public JwtUser getAuthenticatedUser(HttpServletRequest request) {
    String token = request.getHeader(tokenHeader).substring(7);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
    return user;
  }

  @RequestMapping(value = "changepassword", method = RequestMethod.POST)
  public ResponseEntity<?> changePassword(
      @RequestParam("password") String password,
      HttpServletRequest request
  ) {
    password = passwordEncoder.encode(password);

    String token = request.getHeader(tokenHeader).substring(7);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
    int count = userService.changePassword(user.getId(), password);
    return ResponseEntity.ok(count);
  }

  // -- REST CRUD -----------------------------------------
  @RequestMapping(value = "/users", method = RequestMethod.GET)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getUsers() {
    List<User> users = userService.find();

    if (users.isEmpty()) {
      return new ResponseEntity<>(new CustomMessage(400, "没有匹配的结果!"), HttpStatus.OK);
    }

    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getUser(@PathVariable("id") Integer id) {
    User user = userService.find(id);

    if (user == null) {
      return new ResponseEntity<>(new CustomMessage(400, id + " 没有匹配的结果!"), HttpStatus.OK);
    }

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @RequestMapping(value = "/users", method = RequestMethod.POST)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> add(@RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setLastPasswordResetDate(new Date());
    int count = userService.add(user);
    return new ResponseEntity<>(count, HttpStatus.OK);
  }

  @RequestMapping(value = "/users", method = RequestMethod.PUT)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> modify(@RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    int count = userService.modify(user);
    return new ResponseEntity<>(count, HttpStatus.OK);
  }

  @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> remove(@PathVariable("id") Integer id) {
    int count = userService.remove(id);
    return new ResponseEntity<>(count, HttpStatus.OK);
  }
}
