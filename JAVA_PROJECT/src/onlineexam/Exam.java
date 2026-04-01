package onlineexam;

import java.util.ArrayList;

// Base class for all exam types
// we use ArrayList here (Collection API) to store questions

public class Exam {
    private String examName;
    private int duration; // in seconds
    private ArrayList<Question> questions; // Collection API - ArrayList

    public Exam(String examName, int duration) {
        this.examName = examName;
        this.duration = duration;
        this.questions = new ArrayList<>(); // initialize empty list
    }

    // add a question to the exam
    public void addQuestion(Question q) {
        questions.add(q);
    }

    // getters
    public String getExamName() {
        return examName;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    @Override
    public String toString() {
        return examName + " (" + questions.size() + " questions, " + duration + " sec)";
    }
}
