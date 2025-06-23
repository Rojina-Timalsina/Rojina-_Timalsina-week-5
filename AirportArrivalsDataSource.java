package Part4;
import java.util.List;
import java.util.ArrayList;

class ConnectionLostException extends Exception {
    public ConnectionLostException(String message) {
        super(message);
    }
} 
   class DataSourceAccessException extends Exception{
    public DataSourceAccessException(String message, Throwable cause){
        super(message, cause);
    }
}
abstract class TouristDataSource{
    String sourceName;

    public TouristDataSource(String sourceName){
        this.sourceName = sourceName;
    }

    public abstract List<String> fetchData() throws DataSourceAccessException;
}

public class AirportArrivalsDataSource extends TouristDataSource{
  public AirportArrivalsDataSource() {
        super("Tribhuvan Airport Arrivals");
    }
    @Override
    public List<String> fetchData() throws DataSourceAccessException{
        if(sourceName.contains("Tribhuvan") && Math.random() < 0.3) {
            try{
                throw new ConnectionLostException("Airport data connection lost! Maybe a pigeon sat on the antenna?");
            }catch (ConnectionLostException e) {
                
                throw new DataSourceAccessException(e.getMessage(), e);
            }
        }
        List<String> data = new ArrayList<>();
        data.add("Visitor: John Doe, USA");
        data.add("Visitor: Emily White, UK");
        return data;
    }

    public static void main(String[] args){
        AirportArrivalsDataSource dataSource = new AirportArrivalsDataSource();

        try{
            List<String> arrivals = dataSource.fetchData();
            System.out.println("Fetched arrivals data:");
            for (String visitor : arrivals) {
                System.out.println(visitor);
            }
        }catch (DataSourceAccessException e){
            System.out.println("Failed to fetch data: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println(" Root cause: " + e.getCause().getClass().getSimpleName() +
                                   " - " + e.getCause().getMessage());
            }
        }
    }
}