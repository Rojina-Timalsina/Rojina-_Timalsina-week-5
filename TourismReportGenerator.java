package Part4;
import java.util.*;


class DataSourceAccessException extends Exception {
    public DataSourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

class ConnectionLostException extends Exception {
    public ConnectionLostException(String message) {
        super(message);
    }
}

class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}

class DataProcessingException extends Exception {
    public DataProcessingException(String message) {
        super(message);
    }
}

class EmptyDataException extends Exception {
    public EmptyDataException(String message) {
        super(message);
    }
}
abstract class TouristDataSource {
    String sourceName;

    public TouristDataSource(String sourceName) {
        this.sourceName = sourceName;
    }

    public abstract List<String> fetchData() throws DataSourceAccessException;
}
class AirportArrivalsDataSource extends TouristDataSource {
    public AirportArrivalsDataSource() {
        super("Tribhuvan Airport Arrivals");
    }

    public List<String> fetchData() throws DataSourceAccessException {
        if (Math.random() < 0.3) {
            throw new DataSourceAccessException("Airport connection failed!",
                new ConnectionLostException("Maybe a pigeon sat on the antenna?"));
        }
        return Arrays.asList("Visitor: John Doe, USA", "Visitor: Emily White, UK");
    }
}

class HotelRegistrationsDataSource extends TouristDataSource {
    public HotelRegistrationsDataSource() {
        super("Kathmandu Hotels Registrations");
    }

    public List<String> fetchData() throws DataSourceAccessException {
        if (Math.random() < 0.3) {
            throw new DataSourceAccessException("Hotel system locked!",
                new AuthenticationFailedException("Wrong password again?"));
        }
        return Arrays.asList("Guest: Ram Thapa, NP", "Guest: Alice Smith, AU");
    }
}

interface DataProcessor {
    List<String> process(List<String> rawData) throws DataProcessingException;
}

class UniqueVisitorCounter implements DataProcessor {
    public List<String> process(List<String> rawData) throws DataProcessingException {
        if (rawData.isEmpty()) {
            throw new EmptyDataException("No raw data to process!");
        }

        Set<String> uniqueNames = new HashSet<>();
        for (String entry : rawData) {
            String[] parts = entry.split(":")[1].split(",")[0].trim().split(" ");
            uniqueNames.add(parts[0]); // just use first name for simplicity
        }
        return Arrays.asList("Unique Visitors: " + uniqueNames.size());
    }
}
public class TourismReportGenerator {
    public static void generateOverallReport(List<TouristDataSource> sources, DataProcessor processor) {
        System.out.println("Generating overall tourism report...\n");

        for (TouristDataSource source : sources) {
            try {
                List<String> data = source.fetchData();
                List<String> result = processor.process(data);
                for (String line : result) {
                    System.out.println("[" + source.sourceName + "] " + line);
                }
            } catch (DataSourceAccessException e) {
                System.out.println("Could not fetch data from " + source.sourceName + ": " + e.getMessage());
                if (e.getCause() != null) {
                    System.out.println("Reason: " + e.getCause().getMessage());
                }
            } catch (DataProcessingException e) {
                System.out.println("Error processing data from " + source.sourceName + ": " + e.getMessage());
            } finally {
                System.out.println("Data handling from " + source.sourceName + " completed.\n");
            }
        }
    }
    public static void main(String[] args) {
        List<TouristDataSource> sources = new ArrayList<>();
        sources.add(new AirportArrivalsDataSource());
        sources.add(new HotelRegistrationsDataSource());

        DataProcessor processor = new UniqueVisitorCounter();

        generateOverallReport(sources, processor);
    }
}
