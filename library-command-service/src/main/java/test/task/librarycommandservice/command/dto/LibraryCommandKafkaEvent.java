package test.task.librarycommandservice.command.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.bookcommandservice.command.entity.BookCommand;
import test.task.librarycommandservice.command.entity.LibraryCommand;

@Data
@NoArgsConstructor
public class LibraryCommandKafkaEvent {
    public LibraryCommandKafkaEvent(String type, LibraryCommand library) {
        this.type = type;
        this.library = library;
    }

    private String type;
    private LibraryCommand library;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public LibraryCommand getLibraryCommand() {
        return library;
    }
    public void setLibraryCommand(LibraryCommand library) {
        this.library = library;
    }
}