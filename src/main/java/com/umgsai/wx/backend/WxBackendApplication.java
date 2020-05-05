package com.umgsai.wx.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.umgsai.wx.backend.dao")
@SpringBootApplication
public class WxBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxBackendApplication.class, args);
	}

}
