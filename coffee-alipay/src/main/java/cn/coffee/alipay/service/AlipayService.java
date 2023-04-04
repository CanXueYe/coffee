package cn.coffee.alipay.service;

import com.alipay.api.AlipayApiException;

public interface AlipayService {

    /**
     * App支付2.0
     * @return
     */
    Object appPay() throws AlipayApiException;

    /**
     * 统一收单交易退款接口
     * @return
     */
    Object unifyRefund();
}
