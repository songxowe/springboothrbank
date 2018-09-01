package com.newer.springboot.mapper;

import com.newer.springboot.pojo.Trade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 映射器接口
 *
 * @author SONG
 */
public interface TradeMapper {

  @Insert("insert into trade(account_id,trade_type,money,trade_time,remark) values(#{accountId},#{tradeType},#{money},#{tradeTime},#{remark})")
  int add(Trade trade);

  /**
   * 查询交易明细
   *
   * @param accountId
   * @param tradeType
   * @param beginDate
   * @param endDate
   * @return
   */
  List<Trade> find(@Param("accountId") Integer accountId,
                   @Param("tradeType") String tradeType,
                   @Param("beginDate") Date beginDate,
                   @Param("endDate") Date endDate);
}
