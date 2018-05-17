package com.labym.flood.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Jing
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class FloodConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(FloodConfigApplication.class, args);
    }
}