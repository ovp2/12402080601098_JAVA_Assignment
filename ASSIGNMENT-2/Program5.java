// Program5
// Count word occurrences from a file using File Handling APIs

import java.io.*;
import java.util.*;

public class Program5 {

    public static void main(String[] args) {

        HashMap<String, Integer> map = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));

            String line;

            while ((line = br.readLine()) != null) {

                // Convert to lowercase & split words
                String words[] = line.toLowerCase().split("\\W+");

                for (String word : words) {

                    if (word.isEmpty()) continue;

                    if (map.containsKey(word)) {
                        map.put(word, map.get(word) + 1);
                    } else {
                        map.put(word, 1);
                    }
                }
            }

            br.close();

            // Display result
            System.out.println("Word Count:");
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}