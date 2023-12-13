package test.task.librarycommandservice.command.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookcommandservice.command.entity.BookCommand;
import test.task.librarycommandservice.command.entity.LibraryCommand;

import java.util.List;

@Data
@NoArgsConstructor
public class LibraryCommandsListKafkaEvent {
    public LibraryCommandsListKafkaEvent(String type, List<LibraryCommand> libraries){
        this.type = type;
        this.libraries = libraries;
    }
    private String type;
    private List<LibraryCommand> libraries;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<LibraryCommand> getBookCommandList() {
        return libraries;
    }
    public void setBookCommandList(List<LibraryCommand> libraries) {
        this.libraries = libraries;
    }
}
