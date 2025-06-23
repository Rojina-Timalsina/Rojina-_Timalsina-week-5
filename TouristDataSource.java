package Part4;
import java.util.List;

class DataSourceAccessException extends Exception{
    public DataSourceAccessException(String message){
        super(message);
    }
}
public abstract class TouristDataSource{
    String sourceName;

public TouristDataSource(String sourceName){
    this.sourceName=sourceName;
}
public abstract List<String> FetchData() throws DataSourceAccessException;
}



