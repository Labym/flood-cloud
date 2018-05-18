package com.labym.flood.config;

import com.labym.flood.common.config.FloodProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Jing
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, FloodProperties.class})
@ComponentScan("com.labym.flood.common")
public class FloodConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(FloodConfigApplication.class, args);
    }
}