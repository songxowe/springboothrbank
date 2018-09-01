package com.newer.springboot.controller;

import com.newer.springboot.pojo.Role;
import com.newer.springboot.service.MenuService;
import com.newer.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SONG
 */
@RestController
@RequestMapping("/api")
public class MenuRoleController {
  @Autowired
  private RoleService roleService;

  @Autowired
  private MenuService menuService;

  @RequestMapping("/menuRole")
  public ResponseEntity<?> getRoles() {
    List<Role> roles = roleService.find();
    return new ResponseEntity<>(roles, HttpStatus.OK);
  }

  @RequestMapping(value = "/menuRole_findMenu", method = RequestMethod.POST)
  public ResponseEntity<?> findMenu(@RequestParam(required = true, value = "roleId") Integer roleId) {
    // 根据选中的角色id查询显示对应的menu
    List<Integer> menuIds = roleService.findMenuRole(roleId);
    return new ResponseEntity<>(menuIds, HttpStatus.OK);
  }

  @PostMapping("/menuRole_save")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> save(
      @RequestParam(required = true, value = "roleId") Integer roleId,
      @RequestParam(required = true, value = "menuIds") String menuIds) {

    List<Integer> oldMenuIds = roleService.findMenuRole(roleId);
    menuService.addMenuRole(roleId, menuIds, oldMenuIds);

    return ResponseEntity.ok(1);
  }
}
