package test.task.bookqueryservice.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookqueryservice.query.entity.BookQuery;

import java.util.List;

@Data
@NoArgsConstructor
public class BookQueriesListKafkaEvent {
    private String type;
    private List<BookQuery> books;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public  List<BookQuery>  getBookQueryList() {
        return books;
    }
    public void setBookQueryList(List<BookQuery>  books) {
        this.books = books;
    }
    @Override
    public String toString() {
        return "BookQueriesListKafkaEvent [type=" + type + ", bookQueryList=" + books + "]";
    }
}