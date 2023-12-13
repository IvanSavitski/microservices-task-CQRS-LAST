package test.task.bookcommandservice;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@EnableDiscoveryClient
public class BookCommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookCommandServiceApplication.class, args);
    }

    @Bean
    @Primary
    public ModelMapper modelBookCommandMapper() {
        return new ModelMapper();
    }
}