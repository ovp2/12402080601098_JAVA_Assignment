package onlineexam;

// MultipleChoiceExam extends Exam - INHERITANCE again
// this is specifically for MCQ type exams

public class MultipleChoiceExam extends Exam {
    private int marksPerQuestion;

    public MultipleChoiceExam(String examName, int duration, int marksPerQuestion) {
        super(examName, duration); // calling parent constructor
        this.marksPerQuestion = marksPerQuestion;
    }

    public int getMarksPerQuestion() {
        return marksPerQuestion;
    }

    // calculate total possible marks
    public int getTotalMarks() {
        return getTotalQuestions() * marksPerQuestion;
    }

    @Override
    public String toString() {
        return getExamName() + " [MCQ] - " + getTotalQuestions() + " Qs, " 
               + marksPerQuestion + " marks each";
    }
}
