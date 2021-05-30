package com.atguigu.springcloud.lb.impl;

import com.atguigu.springcloud.lb.LoadBalanceer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class MyLB implements LoadBalanceer {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private final int getIndex(){
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= 214743647? 0 : current+1;
        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println(next);
        return next;
    }
    @Override
    public ServiceInstance getServiceInstance(List<ServiceInstance> serviceInstances) {
        int index = this.getIndex() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
