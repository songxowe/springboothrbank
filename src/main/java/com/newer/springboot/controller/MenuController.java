package com.newer.springboot.controller;

import com.newer.springboot.pojo.CustomMessage;
import com.newer.springboot.pojo.Menu;
import com.newer.springboot.pojo.MenuNode;
import com.newer.springboot.pojo.MenuUser;
import com.newer.springboot.security.JwtTokenUtil;
import com.newer.springboot.security.domain.JwtUser;
import com.newer.springboot.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SONG
 */
@RestController
@RequestMapping("/api")
public class MenuController {
  @Value("${jwt.header}")
  private String tokenHeader;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  @Qualifier("jwtUserDetailsService")
  private UserDetailsService userDetailsService;

  @Autowired
  private MenuService menuService;

  /**
   * 读取指定用户的所有菜单项
   */
  @RequestMapping(value = "/menu_index", method = RequestMethod.GET)
  public ResponseEntity<?> index(HttpServletRequest request) {
    String token = request.getHeader(tokenHeader).substring(7);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

    List<MenuUser> menuUsers = new ArrayList<>();

    // 用户菜单
    List<Integer> list = menuService.loadUserMenus(user.getId());

    // 得到所有顶层菜单
    List<Menu> topMenus = menuService.loadTopMenus();
    // 得到指定MenuId下的所有子菜单
    for (Menu menu : topMenus) {
      if (list.contains(menu.getId())) {
        List<Menu> childMenuList = new ArrayList<>();
        List<Menu> chileMenus = menuService.loadChildMenus(menu.getId());
        for (Menu childMenu : chileMenus) {
          if (list.contains(childMenu.getId())) {
            childMenuList.add(childMenu);
          }
        }
        MenuUser menuUser = new MenuUser();
        menuUser.setTopMenu(menu);
        menuUser.setChildMenus(childMenuList);
        menuUsers.add(menuUser);
      }
    }
    return new ResponseEntity<>(menuUsers, HttpStatus.OK);
  }

  /**
   * 新增 | 修改时菜单树
   *
   * @param id
   * @return
   */
  @RequestMapping(value = "/menu_indexAllEdit", method = RequestMethod.POST)
  public ResponseEntity<?> indexAllEdit(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id) {
    List<Menu> menus = null;
    if (id > 0) {
      // 得到指定id下的所有子菜单
      menus = menuService.loadChildMenus(id);
    } else {
      // 得到所有顶层菜单
      menus = menuService.loadTopMenus();

      // 创建一个菜单,用于在查询框显示"所有菜单"
      Menu topAllMenu = new Menu();
      topAllMenu.setId(0);
      topAllMenu.setName("无父级菜单");
      menus.add(topAllMenu);
    }

    // 得到当前用户的所有MenuID
    List<MenuNode> nodeList = new ArrayList<>();
    for (Menu menu : menus) {
      MenuNode node = convertMenuToNode(menu);
      nodeList.add(node);
    }
    return new ResponseEntity<>(nodeList, HttpStatus.OK);
  }

  /**
   * 设置菜单角色时菜单树
   *
   * @param id
   * @return
   */
  @RequestMapping(value = "/menu_indexAll", method = RequestMethod.POST)
  public ResponseEntity<?> indexAll(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id) {
    List<Menu> menus = null;
    if (id > 0) {
      // 得到指定id下的所有子菜单
      menus = menuService.loadChildMenus(id);
    } else {
      // 得到所有顶层菜单
      menus = menuService.loadTopMenus();
    }

    // 得到当前用户的所有MenuID
    List<MenuNode> nodeList = new ArrayList<>();
    for (Menu menu : menus) {
      MenuNode node = convertMenuToNode(menu);
      nodeList.add(node);
    }
    return new ResponseEntity<>(nodeList, HttpStatus.OK);
  }

  /**
   * 将Menu转换成满足easyui的格式命名
   *
   * @param menu
   * @return
   */
  public MenuNode convertMenuToNode(Menu menu) {
    MenuNode node = new MenuNode();
    node.setId(menu.getId());
    node.setText(menu.getName());
    node.setState("closed");

    List<Menu> menus = menuService.loadChildMenus(menu.getId());
    if (menus.size() == 0)
      node.setState("open");
    if (menu.getParentId() != null)
      node.setParentId(menu.getParentId());
    node.setUrl(menu.getLinkUrl());
    return node;
  }

  // -- REST CRUD ---------------------------------------
  @RequestMapping(value = "/menus", method = RequestMethod.GET)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getMenus() {
    List<Menu> menus = menuService.find();

    if (menus.isEmpty()) {
      return new ResponseEntity<>(new CustomMessage(400, "没有匹配的结果!"), HttpStatus.OK);
    }

    return new ResponseEntity<>(menus, HttpStatus.OK);
  }

  @RequestMapping(value = "/menus/{id}", method = RequestMethod.GET)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getMenu(@PathVariable("id") Integer id) {
    Menu menu = menuService.find(id);

    if (menu == null) {
      return new ResponseEntity<>(new CustomMessage(400, id + " 没有匹配的结果!"), HttpStatus.OK);
    }

    return new ResponseEntity<>(menu, HttpStatus.OK);
  }

  @RequestMapping(value = "/menus", method = RequestMethod.POST)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> add(@RequestBody Menu menu) {
    int count = menuService.add(menu);
    return new ResponseEntity<>(count, HttpStatus.OK);
  }

  @RequestMapping(value = "/menus", method = RequestMethod.PUT)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> modify(@RequestBody Menu menu) {
    int count = menuService.modify(menu);
    return new ResponseEntity<>(count, HttpStatus.OK);
  }

  @RequestMapping(value = "/menus/{id}", method = RequestMethod.DELETE)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> remove(@PathVariable("id") Integer id) {
    int count = menuService.remove(id);
    return new ResponseEntity<>(count, HttpStatus.OK);
  }
}
