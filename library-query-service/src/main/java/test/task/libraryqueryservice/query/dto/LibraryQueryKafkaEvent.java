package test.task.libraryqueryservice.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.libraryqueryservice.query.entity.LibraryQuery;

@Data
@NoArgsConstructor
public class LibraryQueryKafkaEvent {
    private String type;
    private LibraryQuery library;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public LibraryQuery getLibraryQuery() {
        return library;
    }
    public void setLibraryQuery(LibraryQuery book) {
        this.library = library;
    }

    @Override
    public String toString() {
        return "LibraryQueryKafkaEvent [type=" + type + ", LibraryQuery=" + library + "]";
    }
}
