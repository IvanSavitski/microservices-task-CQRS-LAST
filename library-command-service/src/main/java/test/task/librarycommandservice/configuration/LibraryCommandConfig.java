package test.task.librarycommandservice.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan("test.task")
@EntityScan("test.task.librarycommandservice.command.entity")
@EnableJpaRepositories(basePackages = {"test.task.bookcommandservice.command.repository",
        "test.task.librarycommandservice.command.repository"})
public class LibraryConfig {

    @Bean
    //@LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}