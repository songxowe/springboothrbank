package com.newer.springboot.mapper;

import java.util.List;

import com.newer.springboot.pojo.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 系统用户角色映射器
 *
 * @author SONG
 */
public interface UserRoleMapper {

  @Select("select ID,NAME,CODE,DESCN from SYS_ROLES where ID in (select ROLE_ID from SYS_USER_ROLE where USER_ID=#{userId})")
  List<Role> findRole(@Param("userId") Integer userId);

  @Delete("delete from SYS_USER_ROLE where USER_ID=#{userId} and ROLE_ID=#{roleId}")
  void removeUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

  @Insert("insert into SYS_USER_ROLE(USER_ID,ROLE_ID) values(#{userId},#{roleId})")
  void addUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
