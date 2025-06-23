package Part4;
import java.util.List;
import java.util.ArrayList;


class AuthenticationFailedException extends Exception{
    public AuthenticationFailedException(String message){
        super(message);
    }
}
class DataSourceAccessException extends Exception{
    public DataSourceAccessException(String message, Throwable cause){
        super(message, cause);
    }
}
abstract class TouristDataSource {
    String sourceName;

    public TouristDataSource(String sourceName){
        this.sourceName = sourceName;
    }

    public abstract List<String> fetchData() throws DataSourceAccessException;
}
public class HotelRegistrationsDataSource extends TouristDataSource{

    public HotelRegistrationsDataSource(){
        super("Kathmandu Hotels Registrations");
    }

    @Override
    public List<String> fetchData() throws DataSourceAccessException{
        
        if (sourceName.contains("Hotels") && Math.random() < 0.2){
            try{
                throw new AuthenticationFailedException("Hotel API authentication failed! Did someone forget the password again?");
            }catch (AuthenticationFailedException e) {
                throw new DataSourceAccessException(e.getMessage(), e);
            }
        }

        List<String> data = new ArrayList<>();
        data.add("Hotel: Yak & Yeti, Guest: Ram Thapa, NP");
        data.add("Hotel: Annapurna, Guest: Alice Smith, AU");
        return data;
    }

    public static void main(String[] args){
        HotelRegistrationsDataSource hotelData = new HotelRegistrationsDataSource();

        try {
            List<String> registrations = hotelData.fetchData();
            System.out.println("Fetched hotel registration data:");
            for (String record : registrations) {
                System.out.println(record);
            }
        } catch (DataSourceAccessException e) {
            System.out.println("Failed to fetch hotel data: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println(" Root cause: " + e.getCause().getClass().getSimpleName() + " - " + e.getCause().getMessage());
            }
        }
    }
}
