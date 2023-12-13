package test.task.bookqueryservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class BookQueryConfig {
    @Bean
    public NewTopic bookStreamTopic() {
        return TopicBuilder
                .name("book_event_topic")
                .build();
    }
}
