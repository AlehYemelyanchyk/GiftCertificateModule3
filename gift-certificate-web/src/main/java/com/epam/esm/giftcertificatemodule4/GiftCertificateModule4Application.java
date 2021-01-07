package com.epam.esm.giftcertificatemodule4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity(debug = true)
@SpringBootApplication()
public class GiftCertificateModule4Application {

	public static void main(String[] args) {
		SpringApplication.run(GiftCertificateModule4Application.class, args);
	}
}
