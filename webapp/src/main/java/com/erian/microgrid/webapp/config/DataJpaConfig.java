package com.erian.microgrid.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.erian.microgrid.webapp"})
public class DataJpaConfig {


}
