package com.newer.springboot.mapper;

import com.newer.springboot.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 系统用户映射器
 *
 * @author SONG
 */
public interface UserMapper {

  User findByUsername(@Param("username") String username);

  @Update("update sys_users set password=#{password},last_password_reset_date=now() where id=#{id}")
  int changePassword(@Param("id") Integer id,
                     @Param("password") String password);

  // ** CRUD *********************************************************
  @Select("select id,username,email,status,last_password_reset_date from sys_users")
  @Results({
      @Result(property = "lastPasswordResetDate", column = "last_password_reset_date")
  })
  List<User> find();

  @Select("select id,username,email,status,last_password_reset_date lastPasswordResetDate from sys_users where id=#{id}")
  User findById(Integer id);

  @Insert("insert into sys_users(username,password,email,status,last_password_reset_date) values(#{username},#{password},#{email},#{status},#{lastPasswordResetDate})")
  int add(User user);

  @Update("update sys_users set username=#{username},password=#{password}, email=#{email},status=#{status} where id=#{id}")
  int modify(User user);

  @Delete("delete from SYS_USERS where ID=#{id}")
  int remove(Integer id);

  @Delete("delete from SYS_USER_ROLE where USER_ID=#{userId} and ROLE_ID=#{roleId}")
  void removeUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

  @Insert("insert into SYS_USER_ROLE(USER_ID,ROLE_ID) values(#{userId},#{roleId})")
  void addUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
