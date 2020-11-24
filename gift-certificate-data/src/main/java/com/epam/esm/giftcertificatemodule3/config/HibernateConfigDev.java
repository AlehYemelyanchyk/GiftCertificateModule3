package com.epam.esm.giftcertificatemodule3.config;

import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Profile("dev")
public class HibernateConfigDev extends HibernateConfig {
    public HibernateConfigDev(Environment env) {
        super(env);
    }
}
