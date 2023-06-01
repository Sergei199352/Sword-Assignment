import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FrequencyAnalyzer {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the path to the text file as a command line argument.");
            return;
        }

        String filePath = args[0];
        boolean caseSensitive = args.length > 1 && args[1].toLowerCase().equals("-i");

        try {
            String text = readTextFile(filePath);
            Map<Character, Integer> charCount = countCharacters(text, caseSensitive);

            System.out.println("Total characters (excluding white space): " + charCount.values().stream().mapToInt(Integer::intValue).sum());
            System.out.println("Top 10 most frequently occurring characters:");
            
            charCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEach(entry -> System.out.println(entry.getKey() + " (" + entry.getValue() + ")"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    private static String readTextFile(String filePath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    private static Map<Character, Integer> countCharacters(String text, boolean caseSensitive) {
        Map<Character, Integer> charCount = new HashMap<>();
        
        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c) || Character.isISOControl(c))
                continue;
            
            if (!caseSensitive)
                c = Character.toLowerCase(c);
            
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        
        return charCount;
    }
}
