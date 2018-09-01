package com.newer.springboot.mapper;

import com.newer.springboot.pojo.Account;
import org.apache.ibatis.annotations.*;

/**
 * 映射器接口
 *
 * @author SONG
 */
public interface AccountMapper {

  /**
   * 开户
   *
   * @param account
   * @return
   */
  @Insert("insert into account(account_id,password,remaining) values(#{accountId},#{password},#{remaining})")
  @Options(useGeneratedKeys = true, keyColumn = "id")
  int add(Account account);

  @Select("select id,account_id accountId,password,remaining from account where id=#{id}")
  Account findById(Integer id);

  @Select("select id,account_id accountId,password,remaining from account where account_id=#{accountId}")
  Account findByAccountId(@Param("accountId") String accountId);

  /**
   * 登录
   *
   * @param accountId
   * @param password
   * @return
   */
  @Select("select id,account_id accountId,password,remaining from account where account_id=#{accountId} and password=#{password}")
  Account login(@Param("accountId") String accountId,
                @Param("password") String password);

  /**
   * 更新余额
   *
   * @param account
   * @return
   */
  @Update("update account set remaining=#{remaining} where id=#{id}")
  int modify(Account account);
}
