package com.example.VideoStreamdemo;

import org.springframework.boot.SpringApplication;

public class TestVideoStreamdemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(VideoStreamdemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
