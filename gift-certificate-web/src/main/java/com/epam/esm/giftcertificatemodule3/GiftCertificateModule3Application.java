package com.epam.esm.giftcertificatemodule3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude= HibernateJpaAutoConfiguration.class)
public class GiftCertificateModule3Application {

	public static void main(String[] args) {
		SpringApplication.run(GiftCertificateModule3Application.class, args);
	}
}
