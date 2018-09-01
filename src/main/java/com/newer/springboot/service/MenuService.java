package com.newer.springboot.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.newer.springboot.pojo.Menu;
import com.newer.springboot.mapper.MenuMapper;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SONG
 */
@Service("menuService")
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class MenuService {
  @Autowired
  private MenuMapper menuMapper;

  /**
   * 读取用户所有的菜单ID
   *
   * @param userId
   * @return
   */
  public List<Integer> loadUserMenus(Integer userId) {
    return menuMapper.loadUserMenus(userId);
  }

  /**
   * 读取所有顶层菜单
   *
   * @return
   */
  public List<Menu> loadTopMenus() {
    return menuMapper.loadTopMenus();
  }

  /**
   * 读取指定模块的顶层菜单
   *
   * @param parentId 模块ID号
   * @return
   */
  public List<Menu> loadChildMenus(Integer parentId) {
    return menuMapper.loadChildMenus(parentId);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int add(Menu menu) {
    return menuMapper.add(menu);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int modify(Menu menu) {
    return menuMapper.modify(menu);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int remove(Integer id) {
    return menuMapper.remove(id);
  }

  public Menu find(Integer id) {
    return menuMapper.findById(id);
  }

  public List<Menu> find() {
    return menuMapper.find();
  }

  /**
   * 保存菜单/角色
   *
   * @param roleId
   * @param menuIds
   * @param oldMenuIds
   */
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void addMenuRole(Integer roleId, String menuIds, List<Integer> oldMenuIds) {

    String[] menuIdAry = menuIds.split(",");
    List<String> ids = Arrays.asList(menuIdAry);
    // 去除重复的menuId
    HashSet<String> idsSet = new HashSet<>(ids);
    String[] itemlist = new String[idsSet.size()];
    idsSet.toArray(itemlist);

    if (itemlist != null) {
      // 删除原有菜单/角色
      if (oldMenuIds.size() > 0) {
        for (Integer menuId : oldMenuIds) {
          // System.out.println(">>>>>> " + menuId + " " + roleId);
          menuMapper.removeMenuRole(menuId, roleId);
        }
      }

      // 保存新菜单/角色
      for (int k = 0; k < itemlist.length; k++) {
        Integer menuId = NumberUtils.createInteger(itemlist[k]);
        // System.out.println("....... " + menuId + " " + roleId);
        menuMapper.addMenuRole(menuId, roleId);
      }
    }
  }
}
