package com.atguigu.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderConsulControler {

    public static final String PAYMENT_URL = "http://consul-provider-payment";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/consul")
    public String paymetInfo() {
        String result = restTemplate.getForObject(PAYMENT_URL + "/payment/consul", String.class);
        return result;
    }
}
