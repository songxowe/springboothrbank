package com.newer.springboot.controller;

import com.newer.springboot.pojo.Trade;
import com.newer.springboot.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/")
public class TradeController {
  // url            method
  // api/trades   - get    查询所有
  // api/trades/1 - get    根据 id 查询
  // api/trades   - post   新增
  // api/trades   - put    修改
  // api/trades/1 - delete 根据 id 删除

  @Autowired
  private TradeService tradeService;

  // GET: http://127.0.0.1:8086/api/trades
  @RequestMapping(value = "trades", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> list(
      @RequestParam(value = "accountId") Integer accountId,
      @RequestParam(value = "tradeType", required = false) String tradeType,
      @RequestParam(value = "beginDate", required = false) String sbeginDate,
      @RequestParam(value = "endDate", required = false) String sendDate
  ) {

    // beginDate: 2018-08-09 00:00:00
    // endDate:   2018-08-31 23:59:59

    Date beginDate = null;
    Date endDate = null;
    if (sbeginDate != null && sbeginDate.length() > 0 && sendDate != null && sendDate.length() > 0) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try {
        beginDate = sdf.parse(sbeginDate + " 00:00:00");
        endDate = sdf.parse(sendDate + " 23:59:59");
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    List<Trade> trades = tradeService.find(accountId, tradeType, beginDate, endDate);
    return new ResponseEntity<>(trades, HttpStatus.OK);
  }
}
