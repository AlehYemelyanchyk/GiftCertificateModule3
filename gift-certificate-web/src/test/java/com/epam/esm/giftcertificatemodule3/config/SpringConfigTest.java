package com.epam.esm.giftcertificatemodule3.config;

import com.epam.esm.giftcertificatemodule3.impl.AbstractIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Profile("test")
public class SpringConfigTest extends HibernateConfig {
    public SpringConfigTest(Environment env) {
        super(env);
    }

    @Bean
    public AbstractIntegrationTest getAbstractTestClass() {
        return new AbstractIntegrationTest();
    }
}
