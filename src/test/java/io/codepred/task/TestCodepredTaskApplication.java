package io.codepred.task;

import org.springframework.boot.SpringApplication;

public class TestCodepredTaskApplication {

	public static void main(String[] args) {
		SpringApplication.from(CodepredTaskApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
