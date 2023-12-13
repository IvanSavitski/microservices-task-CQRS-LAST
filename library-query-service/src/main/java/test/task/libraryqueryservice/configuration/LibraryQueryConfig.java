package test.task.libraryqueryservice.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan("test.task")
@EntityScan("test.task.libraryqueryservice.query.entity")
@EnableJpaRepositories(basePackages = {"test.task.bookqueryservice.query.repository",
        "test.task.libraryqueryservice.query.repository"})
public class LibraryQueryConfig {

    /*@Bean
    //@LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }*/

}