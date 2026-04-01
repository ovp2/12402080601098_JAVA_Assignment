package onlineexam;

// Custom exception class for when the exam timer runs out
// this shows EXCEPTION HANDLING concept

public class ExamTimeoutException extends Exception {
    public ExamTimeoutException(String message) {
        super(message);
    }
}
