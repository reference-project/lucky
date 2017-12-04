package com.d6td.lucky;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author xuxinlong
 */
@SpringBootApplication
@MapperScan("com.d6td.lucky.dao")
public class LuckyApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LuckyApplication.class, args);
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LuckyApplication.class);
    }
}
