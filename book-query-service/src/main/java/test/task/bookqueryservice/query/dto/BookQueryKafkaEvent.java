package test.task.bookqueryservice.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookqueryservice.query.entity.BookQuery;

@Data
@NoArgsConstructor
public class BookQueryKafkaEvent {
    private String type;
    private BookQuery book;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public BookQuery getBookQuery() {
        return book;
    }
    public void setBookQuery(BookQuery book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookQueryKafkaEvent [type=" + type + ", bookQuery=" + book + "]";
    }
}
