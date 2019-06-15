package com.jll.pay.hezPay;

public interface HezPayDao
{
  /**
   * query the white list of tong lue cloud
 * @param ip
 * @return
 */
public long queryWhiteListCount(String ip, String roleName);
  
  /**
   * query the count of order
 * @param orderId
 * @return
 */
public long queryDepositOrderCount(int orderId);
}
