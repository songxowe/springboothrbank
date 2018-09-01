package com.newer.springboot.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 指定系统用户的菜单
 */
public class MenuUser implements Serializable {
  private static final long serialVersionUID = 3784585666354259200L;
  private Menu topMenu;
  private List<Menu> childMenus;

  public MenuUser() {
  }

  public Menu getTopMenu() {
    return topMenu;
  }

  public void setTopMenu(Menu topMenu) {
    this.topMenu = topMenu;
  }

  public List<Menu> getChildMenus() {
    return childMenus;
  }

  public void setChildMenus(List<Menu> childMenus) {
    this.childMenus = childMenus;
  }
}
