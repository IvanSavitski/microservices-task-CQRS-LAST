package test.task.bookcommandservice.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookcommandservice.command.entity.BookCommand;

import java.util.List;

@Data
@NoArgsConstructor
public class BookCommandsListKafkaEvent {
    public BookCommandsListKafkaEvent(String type, List<BookCommand> books){
        this.type = type;
        this.books = books;
    }
    private String type;
    private List<BookCommand> books;
    public String getType() {
        return type;
    }
   public void setType(String type) {
        this.type = type;
    }
    public List<BookCommand> getBookCommandList() {
        return books;
    }
    public void setBookCommandList(List<BookCommand> books) {
        this.books = books;
    }
}
