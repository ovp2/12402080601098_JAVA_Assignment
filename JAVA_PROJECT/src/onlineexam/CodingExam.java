package onlineexam;

// CodingExam also extends Exam - another INHERITANCE example
// we kept this simple, it just adds a language field
// in a real app this would have code submission and stuff

public class CodingExam extends Exam {
    private String programmingLanguage;

    public CodingExam(String examName, int duration, String programmingLanguage) {
        super(examName, duration);
        this.programmingLanguage = programmingLanguage;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    @Override
    public String toString() {
        return getExamName() + " [Coding - " + programmingLanguage + "] - " 
               + getTotalQuestions() + " Qs";
    }
}
