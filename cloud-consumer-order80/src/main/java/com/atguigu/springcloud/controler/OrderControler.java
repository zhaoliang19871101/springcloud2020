package com.atguigu.springcloud.controler;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalanceer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class OrderControler {

    public static final String PAYMENT_URL = "http://cloud-provider-service";
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalanceer loadBalanceer;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") long id){
        CommonResult<Payment> result = restTemplate.getForObject(PAYMENT_URL + "/payment/get/"+id,CommonResult.class);
        return result;
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getForEntity(@PathVariable("id") long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/"+id,CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else {
            return new CommonResult<Payment>(444,"查询出错",null);
        }

    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        ServiceInstance serviceInstance = loadBalanceer.getServiceInstance(instances);
        return restTemplate.getForObject(serviceInstance.getUri()+"/payment/lb",String.class);
    }
}
