package com.newer.springboot.controller;

import com.newer.springboot.pojo.Role;
import com.newer.springboot.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author SONG
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserRoleController {
  @Autowired
  private UserRoleService userRoleService;

  @PostMapping("userRole_grantOrRevoke")
  public ResponseEntity<?> grantOrRevoke(
      @RequestParam(value = "isAuth") Integer isAuth,
      @RequestParam(value = "roleId") Integer roleId,
      @RequestParam(value = "userId") Integer userId
  ) {
    userRoleService.addUserRoles(userId, roleId, isAuth);
    return ResponseEntity.ok(1);
  }

  @PostMapping("userRole_findRole")
  public ResponseEntity<?> findRole(@RequestParam(value = "userId") Integer userId) {
    List<Role> userRoles = userRoleService.find(userId);
    return new ResponseEntity<>(userRoles, HttpStatus.OK);
  }
}
