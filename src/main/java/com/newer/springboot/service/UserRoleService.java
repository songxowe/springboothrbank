package com.newer.springboot.service;

import java.util.List;

import com.newer.springboot.pojo.Role;
import com.newer.springboot.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userRoleService")
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class UserRoleService {
  @Autowired
  private UserRoleMapper userRoleMapper;

  public List<Role> find(Integer userId) {
    List<Role> userRoles = userRoleMapper.findRole(userId);
    return userRoles;
  }

  /**
   * 用户角色管理操作
   *
   * @param userId
   * @param roleId
   * @param isAuth
   */
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void addUserRoles(Integer userId, Integer roleId, Integer isAuth) {

    if (isAuth == 0) {
      // 取消授权:删除原用户/角色
      userRoleMapper.removeUserRole(userId, roleId);
    } else if (isAuth == 1) {
      // 授权:保存新用户/角色
      userRoleMapper.addUserRole(userId, roleId);
    }
  }
}
