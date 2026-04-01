package onlineexam;

import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// This class handles FILE I/O operations
// saving results to a text file and reading them back

public class ResultFileHandler {
    private static final String FILE_NAME = "exam_results.txt";

    // save result to file using BufferedWriter
    public static void saveResult(String studentName, String enrollmentNo, 
                                   String examName, int correct, int total,
                                   double percentage, String grade) {
        try {
            // true = append mode, so we don't overwrite old results
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            
            // get current date and time
            String dateTime = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            );

            writer.write("========================================");
            writer.newLine();
            writer.write("Date: " + dateTime);
            writer.newLine();
            writer.write("Student: " + studentName);
            writer.newLine();
            writer.write("Enrollment: " + enrollmentNo);
            writer.newLine();
            writer.write("Exam: " + examName);
            writer.newLine();
            writer.write("Score: " + correct + " / " + total);
            writer.newLine();
            writer.write("Percentage: " + String.format("%.1f", percentage) + "%");
            writer.newLine();
            writer.write("Grade: " + grade);
            writer.newLine();
            writer.write("========================================");
            writer.newLine();
            writer.newLine();

            writer.close();
            System.out.println("Result saved to " + FILE_NAME + " successfully!");

        } catch (IOException e) {
            // exception handling for file errors
            System.out.println("Error saving result: " + e.getMessage());
        }
    }

    // read only the logged-in student's results from file
    // we filter by enrollment number so each student sees only their own results
    public static String readResultsByEnrollment(String enrollmentNo) {
        StringBuilder sb = new StringBuilder();
        File file = new File(FILE_NAME);
        
        if (!file.exists()) {
            return "No results found yet. Take an exam first!";
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            // we'll read the file block by block
            // each result block starts and ends with "========"
            ArrayList<String> currentBlock = new ArrayList<>();
            boolean insideBlock = false;
            boolean blockBelongsToStudent = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("========")) {
                    if (insideBlock) {
                        // we reached the end of a block
                        currentBlock.add(line);
                        // if this block belongs to our student, add it to the output
                        if (blockBelongsToStudent) {
                            for (String blockLine : currentBlock) {
                                sb.append(blockLine).append("\n");
                            }
                            sb.append("\n");
                        }
                        // reset for next block
                        currentBlock.clear();
                        insideBlock = false;
                        blockBelongsToStudent = false;
                    } else {
                        // starting a new block
                        insideBlock = true;
                        currentBlock.add(line);
                    }
                } else if (insideBlock) {
                    currentBlock.add(line);
                    // check if this line has the enrollment we're looking for
                    if (line.startsWith("Enrollment: ") && 
                        line.substring("Enrollment: ".length()).trim().equals(enrollmentNo)) {
                        blockBelongsToStudent = true;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            return "Error reading results: " + e.getMessage();
        }

        if (sb.length() == 0) {
            return "You haven't taken any exams yet!";
        }

        return sb.toString();
    }
}
