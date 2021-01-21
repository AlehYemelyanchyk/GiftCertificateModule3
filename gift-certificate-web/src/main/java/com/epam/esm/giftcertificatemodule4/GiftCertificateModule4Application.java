package com.epam.esm.giftcertificatemodule4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity(debug = true)
@SpringBootApplication()
public class GiftCertificateModule4Application extends SpringBootServletInitializer {

    private static final Class<GiftCertificateModule4Application> APPLICATION_CLASS =
            GiftCertificateModule4Application.class;

    public static void main(String[] args) {
        SpringApplication.run(APPLICATION_CLASS, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(APPLICATION_CLASS);
    }
}