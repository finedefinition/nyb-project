package com.norwayyachtbrockers.config;

import com.norwayyachtbrockers.repository.FuelRepository;
import com.norwayyachtbrockers.repository.KeelRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {KeelRepository.class, FuelRepository.class})
@EntityScan("com.norwayyachtbrockers.model")
public class RepositoryConfig {
}