package net.happiness.tests.config;

import net.happiness.tests.repository.DistributedRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "net.happiness.tests.repository", repositoryBaseClass = DistributedRepositoryImpl.class)
public class AppConfiguration {
}
