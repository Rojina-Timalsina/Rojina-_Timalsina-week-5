package Part4;
import java.util.*;

class EmptyDataException extends Exception{
    public EmptyDataException(String message) {
        super(message);
    }
}
interface DataProcessor {
    List<String> process(List<String> rawData) throws EmptyDataException;
}

public class UniqueVisitorCounter implements DataProcessor {

    @Override
    public List<String> process(List<String> rawData) throws EmptyDataException {
        if (rawData == null || rawData.isEmpty()) {
            throw new EmptyDataException("No raw data to process! Did all tourists go missing?");
        }

        Set<String> uniqueNames = new HashSet<>();

        for (String entry : rawData) {
            String[] parts = entry.split(":");
            if (parts.length > 1) {
                String details = parts[1].trim(); 
                String name = details.split(",")[0].trim();
                uniqueNames.add(name);
            }
        }

        return Collections.singletonList("Unique Visitors: " + uniqueNames.size());
    }

        public static void main(String[] args) {
        UniqueVisitorCounter counter = new UniqueVisitorCounter();

        try {
            List<String> emptyList = new ArrayList<>();
            counter.process(emptyList);
        } catch (EmptyDataException e) {
            System.out.println("Test 1 (Empty): " + e.getMessage());
        }

        try {
            List<String> sampleData = Arrays.asList(
                    "Visitor: John Doe, USA",
                    "Guest: Alice Smith, AU",
                    "Visitor: John Doe, USA"
            );
            List<String> result = counter.process(sampleData);
            System.out.println("Test 2 (Valid): " + result.get(0));
        } catch (EmptyDataException e) {
            System.out.println("Test 2 Error: " + e.getMessage());
        }
    }
}