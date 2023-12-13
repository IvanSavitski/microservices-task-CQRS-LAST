package test.task.librarycommandservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("test.task.bookcommandservice.command.entity")
public class LibraryCommandServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryCommandServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelLibraryCommandMapper() {
        return new ModelMapper();
    }
}


