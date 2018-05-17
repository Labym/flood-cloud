package com.labym.flood.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableZuulProxy
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringCloudApplication
public class FloodGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FloodGatewayApplication.class, args);
    }

    @Bean
    LoadBalancerInterceptor loadBalancerInterceptor(LoadBalancerClient loadBalance){
        return new LoadBalancerInterceptor(loadBalance);
    }

}
