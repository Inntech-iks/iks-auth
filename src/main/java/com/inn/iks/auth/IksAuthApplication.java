package com.inn.iks.auth;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value = "com.inn")
@EnableAutoConfiguration
@EnableJpaRepositories(value = "com.inn")
@EntityScan(basePackages = "com.inn")

public class IksAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(IksAuthApplication.class, args);
	}
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
