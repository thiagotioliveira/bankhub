package dev.thiagooliveira.bankhub;

import org.springframework.boot.SpringApplication;

public class TestBankhubApplication {

	public static void main(String[] args) {
		SpringApplication.from(BankhubApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
