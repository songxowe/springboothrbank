package com.newer.springboot.mapper;

import java.util.List;

import com.newer.springboot.pojo.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 菜单映射器
 *
 * @author SONG
 */
public interface MenuMapper {

  @Select("SELECT DISTINCT MR.MENU_ID FROM SYS_MENU_ROLE MR,SYS_ROLES R,SYS_USER_ROLE UR WHERE MR.ROLE_ID=R.ID AND R.ID=UR.ROLE_ID AND UR.USER_ID=#{userId}")
  List<Integer> loadUserMenus(@Param("userId") Integer userId);

  @Select("SELECT ID,PARENT_ID parentId,SEQ,NAME,DESCN,LINK_URL linkUrl,STATUS FROM SYS_MENUS WHERE PARENT_ID IS NULL ORDER BY SEQ")
  List<Menu> loadTopMenus();

  @Select("SELECT ID,PARENT_ID parentId,SEQ,NAME,DESCN,LINK_URL linkUrl,STATUS FROM SYS_MENUS WHERE PARENT_ID=#{parentId} ORDER BY SEQ")
  List<Menu> loadChildMenus(@Param("parentId") Integer parentId);

  // ** CRUD *************************************************************
  @Insert("insert into SYS_MENUS(PARENT_ID,SEQ,NAME,DESCN,LINK_URL,STATUS) values(#{parentId,jdbcType=INTEGER},#{seq},#{name},#{descn},#{linkUrl},#{status})")
  int add(Menu menu);

  @Update("update SYS_MENUS set PARENT_ID=#{parentId,jdbcType=INTEGER},SEQ=#{seq},NAME=#{name},DESCN=#{descn},LINK_URL=#{linkUrl},STATUS=#{status} where ID=#{id}")
  int modify(Menu menu);

  @Delete("delete from SYS_MENUS where ID=#{id}")
  int remove(Integer id);

  @Select("SELECT ID,ifnull(PARENT_ID,0) parentId,SEQ,NAME,DESCN,ifnull(LINK_URL,'') linkUrl,STATUS FROM SYS_MENUS WHERE ID=#{id}")
  Menu findById(Integer id);

  @Select("SELECT ID,ifnull(PARENT_ID,0) parentId,SEQ,NAME,DESCN,ifnull(LINK_URL,'') linkUrl,STATUS FROM SYS_MENUS")
  List<Menu> find();

  @Delete("delete from SYS_MENU_ROLE where MENU_ID=#{menuId} and ROLE_ID=#{roleId}")
  void removeMenuRole(@Param("menuId") Integer menuId, @Param("roleId") Integer roleId);

  @Insert("insert into SYS_MENU_ROLE(MENU_ID,ROLE_ID) values(#{menuId},#{roleId})")
  void addMenuRole(@Param("menuId") Integer menuId, @Param("roleId") Integer roleId);
}
