package com.newer.springboot.service;

import com.newer.springboot.mapper.TradeMapper;
import com.newer.springboot.pojo.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 交易业务逻辑
 *
 * @author SONG
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class TradeService {
  @Autowired
  private TradeMapper tradeMapper;

  /**
   * 查询交易明细
   *
   * @param accountId
   * @param tradeType
   * @param beginDate
   * @param endDate
   * @return
   */
  @Cacheable(value = "trade.find")
  public List<Trade> find(Integer accountId,
                          String tradeType,
                          Date beginDate,
                          Date endDate) {
    return tradeMapper.find(accountId, tradeType, beginDate, endDate);
  }
}
