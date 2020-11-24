package com.epam.esm.giftcertificatemodule3.config;

import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Profile("prod")
public class HibernateConfigProd extends HibernateConfig {
    public HibernateConfigProd(Environment env) {
        super(env);
    }
}
