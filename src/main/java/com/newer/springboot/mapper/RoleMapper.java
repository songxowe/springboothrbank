package com.newer.springboot.mapper;

import java.util.List;

import com.newer.springboot.pojo.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 角色映射器
 *
 * @author SONG
 */
public interface RoleMapper {

  @Insert("insert into SYS_ROLES(NAME,CODE,DESCN) values(#{name},#{code},#{descn})")
  int add(Role role);

  @Update("update SYS_ROLES set NAME=#{name},CODE=#{code},DESCN=#{descn} where ID=#{id}")
  int modify(Role role);

  @Delete("delete from SYS_ROLES where ID=#{id}")
  int remove(Integer id);

  @Select("select ID,NAME,CODE,DESCN from SYS_ROLES where ID=#{id}")
  Role findById(Integer id);

  @Select("select ID,NAME,CODE,DESCN from SYS_ROLES order by ID")
  List<Role> find();

  /**
   * 根据选中的角色id查询显示对应的menu
   *
   * @param roleId
   * @return
   */
  @Select("select MENU_ID from SYS_MENU_ROLE where ROLE_ID=#{roleId} order by MENU_ID")
  List<Integer> findMenuRole(@Param("roleId") Integer roleId);
}
