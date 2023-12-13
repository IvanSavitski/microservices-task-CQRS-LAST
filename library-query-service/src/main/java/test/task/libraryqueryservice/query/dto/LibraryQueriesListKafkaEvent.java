package test.task.libraryqueryservice.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.task.libraryqueryservice.query.entity.LibraryQuery;

import java.util.List;

@Data
@NoArgsConstructor
public class LibraryQueriesListKafkaEvent {
    private String type;
    private List<LibraryQuery> library;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public  List<LibraryQuery>  getBookQueryList() {
        return library;
    }
    public void setBookQueryList(List<LibraryQuery>  books) {
        this.library = library;
    }
    @Override
    public String toString() {
        return "LibraryQueriesListKafkaEvent [type=" + type + ", libraryQueryList=" + library + "]";
    }
}
