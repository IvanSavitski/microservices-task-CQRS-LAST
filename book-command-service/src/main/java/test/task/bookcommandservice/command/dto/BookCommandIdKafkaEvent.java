package test.task.bookcommandservice.command.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookCommandIdKafkaEvent {
    public BookCommandIdKafkaEvent(String type, long id){
        this.type = type;
        this.id = id;
    }
    private String type;
    private long id;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}

