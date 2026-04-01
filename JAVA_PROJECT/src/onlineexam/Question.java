package onlineexam;

// This class stores one single question
// it has the question text, 4 options, and which option is correct

public class Question {
    private String questionText;
    private String[] options; // array of 4 options
    private int correctOptionIndex; // 0, 1, 2, or 3

    public Question(String questionText, String[] options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    // getters
    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    // helper method to get a specific option
    public String getOption(int index) {
        if (index >= 0 && index < options.length) {
            return options[index];
        }
        return ""; // return empty if invalid index
    }
}
