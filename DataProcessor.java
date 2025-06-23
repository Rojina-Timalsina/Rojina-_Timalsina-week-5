package Part4;
import java.util.List;

class DataProcessingException extends Exception{
    public DataProcessingException(String message){
        super(message);
    }
}
public interface DataProcessor {
    List<String> process(List<String> rawData) throws DataProcessingException;
}


   