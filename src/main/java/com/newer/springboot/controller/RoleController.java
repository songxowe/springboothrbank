package com.newer.springboot.controller;

import com.newer.springboot.pojo.CustomMessage;
import com.newer.springboot.pojo.Role;
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
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {
  @Autowired
  private RoleService roleService;

  @RequestMapping(value = "/roles", method = RequestMethod.GET)
  public ResponseEntity<?> getRoles() {
    List<Role> roles = roleService.find();

    if (roles.isEmpty()) {
      return new ResponseEntity<>(new CustomMessage(400, "没有匹配的结果!"), HttpStatus.OK);
    }

    return new ResponseEntity<>(roles, HttpStatus.OK);
  }

  @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getRole(@PathVariable("id") Integer id) {
    Role role = roleService.find(id);

    if (role == null) {
      return new ResponseEntity<>(new CustomMessage(400, id + " 没有匹配的结果!"), HttpStatus.OK);
    }

    return new ResponseEntity<>(role, HttpStatus.OK);
  }

  @RequestMapping(value = "/roles", method = RequestMethod.POST)
  public ResponseEntity<?> add(@RequestBody Role role) {
    int count = roleService.add(role);
    return new ResponseEntity<>(count, HttpStatus.OK);
  }

  @RequestMapping(value = "/roles", method = RequestMethod.PUT)
  public ResponseEntity<?> modify(@RequestBody Role role) {
    int count = roleService.modify(role);
    return new ResponseEntity<>(count, HttpStatus.OK);
  }

  @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> remove(@PathVariable("id") Integer id) {
    int count = roleService.remove(id);
    return new ResponseEntity<>(count, HttpStatus.OK);
  }
}
