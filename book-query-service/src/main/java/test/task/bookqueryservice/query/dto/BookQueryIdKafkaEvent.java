package test.task.bookqueryservice.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BookQueryIdKafkaEvent {
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
    @Override
    public String toString() {
        return "BookQueryIdKafkaEvent [type=" + type + ", bookQueryId=" + id + "]";
    }
}
