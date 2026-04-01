package onlineexam;

import java.util.ArrayList;
import java.util.HashMap;

// ExamManager handles all the exam logic
// It has an INNER CLASS called Grader for calculating scores
// It also uses HashMap and ArrayList (Collection API)

public class ExamManager {
    private ArrayList<Exam> availableExams; // list of all exams
    private HashMap<Integer, Integer> studentAnswers; // question index -> selected option
    private Exam currentExam;

    public ExamManager() {
        availableExams = new ArrayList<>();
        studentAnswers = new HashMap<>();
        loadExams(); // load some sample exams when manager is created
    }

    // this method creates sample exams with questions
    // in a real project you'd load these from a database or file
    private void loadExams() {

        // --- Java Basics Exam ---
        MultipleChoiceExam javaExam = new MultipleChoiceExam("Java Basics", 120, 2);
        javaExam.addQuestion(new Question(
            "Which keyword is used to create a class in Java?",
            new String[]{"struct", "class", "object", "define"}, 1
        ));
        javaExam.addQuestion(new Question(
            "What is the default value of an int variable in Java?",
            new String[]{"1", "null", "0", "undefined"}, 2
        ));
        javaExam.addQuestion(new Question(
            "Which method is the entry point of a Java program?",
            new String[]{"start()", "init()", "main()", "run()"}, 2
        ));
        javaExam.addQuestion(new Question(
            "What does JVM stand for?",
            new String[]{"Java Very Much", "Java Virtual Machine", 
                         "Java Variable Method", "Just Virtual Memory"}, 1
        ));
        javaExam.addQuestion(new Question(
            "Which of these is NOT a primitive data type in Java?",
            new String[]{"int", "boolean", "String", "char"}, 2
        ));
        availableExams.add(javaExam);

        // --- OOP Concepts Exam ---
        MultipleChoiceExam oopExam = new MultipleChoiceExam("OOP Concepts", 90, 2);
        oopExam.addQuestion(new Question(
            "Which OOP concept allows a class to inherit from another class?",
            new String[]{"Polymorphism", "Encapsulation", "Inheritance", "Abstraction"}, 2
        ));
        oopExam.addQuestion(new Question(
            "What is encapsulation?",
            new String[]{"Hiding data using access modifiers", "Creating multiple objects",
                         "Using loops in classes", "Deleting unused objects"}, 0
        ));
        oopExam.addQuestion(new Question(
            "Which keyword is used to inherit a class in Java?",
            new String[]{"implements", "inherits", "extends", "super"}, 2
        ));
        oopExam.addQuestion(new Question(
            "What is polymorphism?",
            new String[]{"Having many variables", "One interface, many forms",
                         "Creating many classes", "Using only one method"}, 1
        ));
        oopExam.addQuestion(new Question(
            "An abstract class can have:",
            new String[]{"Only abstract methods", "Only concrete methods",
                         "Both abstract and concrete methods", "No methods at all"}, 2
        ));
        availableExams.add(oopExam);

        // --- DBMS Exam ---
        MultipleChoiceExam dbmsExam = new MultipleChoiceExam("DBMS Fundamentals", 90, 2);
        dbmsExam.addQuestion(new Question(
            "What does SQL stand for?",
            new String[]{"Strong Query Language", "Structured Query Language",
                         "Simple Question Language", "Standard Query Logic"}, 1
        ));
        dbmsExam.addQuestion(new Question(
            "Which command is used to retrieve data from a database?",
            new String[]{"GET", "FETCH", "SELECT", "RETRIEVE"}, 2
        ));
        dbmsExam.addQuestion(new Question(
            "A primary key must be:",
            new String[]{"Null", "Duplicate", "Unique and Not Null", "Only numeric"}, 2
        ));
        dbmsExam.addQuestion(new Question(
            "Which normal form removes partial dependency?",
            new String[]{"1NF", "2NF", "3NF", "BCNF"}, 1
        ));
        dbmsExam.addQuestion(new Question(
            "What is a foreign key?",
            new String[]{"A key from another country", 
                         "A primary key of another table referenced in current table",
                         "A key that is always null", "A key used for encryption"}, 1
        ));
        availableExams.add(dbmsExam);

        // --- Python Basics Exam ---
        MultipleChoiceExam pythonExam = new MultipleChoiceExam("Python Basics", 90, 2);
        pythonExam.addQuestion(new Question(
            "Which symbol is used for comments in Python?",
            new String[]{"//", "/*", "#", "--"}, 2
        ));
        pythonExam.addQuestion(new Question(
            "What is the output of print(type(5))?",
            new String[]{"<class 'float'>", "<class 'int'>", 
                         "<class 'str'>", "<class 'number'>"}, 1
        ));
        pythonExam.addQuestion(new Question(
            "Which keyword is used to define a function in Python?",
            new String[]{"function", "func", "def", "define"}, 2
        ));
        pythonExam.addQuestion(new Question(
            "Python is a:",
            new String[]{"Compiled language", "Interpreted language",
                         "Machine language", "Assembly language"}, 1
        ));
        pythonExam.addQuestion(new Question(
            "What does len() function do?",
            new String[]{"Returns length of an object", "Creates a new list",
                         "Deletes an element", "Sorts a list"}, 0
        ));
        availableExams.add(pythonExam);
    }

    // get list of all available exams
    public ArrayList<Exam> getAvailableExams() {
        return availableExams;
    }

    // set which exam the student is currently taking
    public void setCurrentExam(Exam exam) {
        this.currentExam = exam;
        this.studentAnswers.clear(); // clear any previous answers
    }

    public Exam getCurrentExam() {
        return currentExam;
    }

    // record a student's answer for a question
    public void setAnswer(int questionIndex, int selectedOption) {
        studentAnswers.put(questionIndex, selectedOption); // HashMap usage
    }

    // get what the student answered for a specific question
    public int getAnswer(int questionIndex) {
        // HashMap.getOrDefault - returns -1 if no answer was selected
        return studentAnswers.getOrDefault(questionIndex, -1);
    }

    public HashMap<Integer, Integer> getAllAnswers() {
        return studentAnswers;
    }

    // ========================================
    // INNER CLASS - Grader
    // this class is inside ExamManager because it's only used here
    // no other class needs to access it directly
    // ========================================
    private class Grader {
        private int correctCount;
        private int wrongCount;
        private int unanswered;

        public Grader() {
            correctCount = 0;
            wrongCount = 0;
            unanswered = 0;
        }

        // go through all questions and check answers
        public void grade() {
            ArrayList<Question> questions = currentExam.getQuestions();
            for (int i = 0; i < questions.size(); i++) {
                if (studentAnswers.containsKey(i)) {
                    int selected = studentAnswers.get(i);
                    int correct = questions.get(i).getCorrectOptionIndex();
                    if (selected == correct) {
                        correctCount++;
                    } else {
                        wrongCount++;
                    }
                } else {
                    unanswered++;
                }
            }
        }

        public int getCorrectCount() { return correctCount; }
        public int getWrongCount() { return wrongCount; }
        public int getUnanswered() { return unanswered; }
    }

    // this method uses the inner Grader class to calculate results
    public int[] getResults() {
        Grader grader = new Grader(); // creating inner class object
        grader.grade();
        // return array: [correct, wrong, unanswered]
        return new int[]{grader.getCorrectCount(), grader.getWrongCount(), grader.getUnanswered()};
    }

    // check if a specific answer is correct (for showing in result screen)
    public boolean isCorrect(int questionIndex) {
        if (studentAnswers.containsKey(questionIndex)) {
            return studentAnswers.get(questionIndex) == 
                   currentExam.getQuestions().get(questionIndex).getCorrectOptionIndex();
        }
        return false;
    }
}
