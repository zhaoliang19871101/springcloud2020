package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixForBackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_Ok(Integer id) {
        return "PaymentHystrixForBackService  paymentInfo_Ok   ";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentHystrixForBackService   paymentInfo_TimeOut  ";
    }
}
