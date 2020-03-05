package com.example.demo;

import com.example.demo.common.service.impl.CommonServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;


@SpringBootApplication()
@MapperScan(value = "com.example.demo.mapper")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @EventListener({ApplicationReadyEvent.class})
	void applicationReadyEvent() {
		String url = "http://localhost:8080";
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
