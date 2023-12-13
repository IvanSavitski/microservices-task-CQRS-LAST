package test.task.libraryqueryservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("test.task.bookqueryservice.query.entity")
public class LibraryQueryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryQueryServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelLibraryQueryMapper() {
        return new ModelMapper();
    }
}