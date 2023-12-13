package test.task.bookcommandservice.command.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookcommandservice.command.entity.BookCommand;


@Data
@NoArgsConstructor
public class BookCommandKafkaEvent {
    public BookCommandKafkaEvent(String type, BookCommand book){
        this.type = type;
        this.book = book;
    }
    private String type;
    private BookCommand book;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public BookCommand getBookCommand() {
        return book;
    }
    public void setBookCommand(BookCommand book) {
        this.book = book;
    }
}
