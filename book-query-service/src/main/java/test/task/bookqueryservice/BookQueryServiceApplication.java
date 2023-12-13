package test.task.bookqueryservice;



import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class BookQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookQueryServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelBookQueryMapper() {
        return new ModelMapper();
    }
}

