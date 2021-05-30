package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_Ok(Integer id){
        return "线程池："+ Thread.currentThread().getName() +" paymentInfo_Ok,id "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id){
        int timeNumber = 5;
//        int timeNumber = 10/0;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+ Thread.currentThread().getName() +" paymentInfo_TimeOut,id "+id+"\t"+"O(∩_∩)O哈哈~ ：耗时 "+timeNumber;
    }

    public String paymentInfo_TimeOutHandler(Integer id){

        return "线程池："+ Thread.currentThread().getName() +" paymentInfo_TimeOut,id "+id+"\t"+"o(*￣︶￣*)o";
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreakert_forBacke",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "1000"),//时间窗口
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败效率达到多少后跳闸
    })
    public String paymentCircuitBreakert(Integer id){
       if (id<0){
           throw new RuntimeException("id 不能为负数");
       }
       String serNumber = IdUtil.simpleUUID();
        return "线程池："+ Thread.currentThread().getName() +"调用成功流水号 "+serNumber;
    }

    public String paymentCircuitBreakert_forBacke(Integer id){

        return "id不能为负数，请稍后再试";
    }
}
