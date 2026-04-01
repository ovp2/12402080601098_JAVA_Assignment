package onlineexam;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// This is the MAIN class - it has all the GUI screens
// We're using Java Swing for the graphical user interface
// basically this is where everything comes together

public class OnlineExamSystem extends JFrame {

    // colors we'll use throughout the app (keeping it simple and nice)
    private static final Color BG_COLOR = new Color(245, 248, 250);
    private static final Color CARD_COLOR = new Color(255, 255, 255);
    private static final Color ACCENT_COLOR = new Color(65, 105, 225);

    private static final Color TEXT_COLOR = new Color(30, 30, 40);
    private static final Color TEXT_SECONDARY = new Color(100, 100, 110);
    private static final Color SUCCESS_COLOR = new Color(40, 160, 80);
    private static final Color ERROR_COLOR = new Color(200, 50, 50);
    private static final Color WARNING_COLOR = new Color(220, 140, 0);

    // main objects we need
    private Student currentStudent;
    private ExamManager examManager;
    private ExamTimer examTimer;
    private Thread timerThread;

    // GUI components that we need to access from different methods
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // exam screen components
    private int currentQuestionIndex = 0;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;
    private JLabel questionLabel;
    private JLabel questionNumberLabel;
    private JLabel timerLabel;
    private JPanel navGridPanel; // the question navigation grid

    // constructor - sets up the window
    public OnlineExamSystem() {
        examManager = new ExamManager();

        // basic window setup
        setTitle("Online Examination System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setResizable(false);

        // using CardLayout so we can switch between screens easily
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BG_COLOR);

        // create all the screens
        mainPanel.add(createLoginScreen(), "login");
        mainPanel.add(createRegisterScreen(), "register");
        mainPanel.add(createExamSelectionScreen(), "selection");
        // exam screen and result screen will be created dynamically

        add(mainPanel);
        cardLayout.show(mainPanel, "login"); // start with login screen
    }

    // ==========================================
    // LOGIN SCREEN
    // ==========================================
    private JPanel createLoginScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);

        // create a card-like container for the login form
        JPanel loginCard = new JPanel();
        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
        loginCard.setBackground(CARD_COLOR);
        loginCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210), 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        loginCard.setPreferredSize(new Dimension(420, 480));

        // title
        JLabel title = new JLabel("Online Exam System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setMaximumSize(new Dimension(300, 40));

        JLabel subtitle = new JLabel("Login to your account", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setMaximumSize(new Dimension(300, 25));

        // enrollment field
        JLabel enrollLabel = new JLabel("Enrollment Number");
        enrollLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        enrollLabel.setForeground(TEXT_SECONDARY);
        enrollLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField enrollField = new JTextField(20);
        styleTextField(enrollField);
        enrollField.setMaximumSize(new Dimension(300, 40));

        // password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passLabel.setForeground(TEXT_SECONDARY);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passField = new JPasswordField(20);
        stylePasswordField(passField);
        passField.setMaximumSize(new Dimension(300, 40));

        // login button
        JButton loginBtn = createStyledButton("Login", ACCENT_COLOR, Color.WHITE);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(300, 42));

        // what happens when login is clicked
        loginBtn.addActionListener(e -> {
            String enrollment = enrollField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            // EXCEPTION HANDLING - check for empty fields
            try {
                if (enrollment.isEmpty() || password.isEmpty()) {
                    throw new IllegalArgumentException("Please fill in both fields!");
                }

                // validate credentials against saved users file
                String name = UserFileHandler.validateLogin(enrollment, password);
                if (name == null) {
                    throw new IllegalArgumentException(
                        "Invalid enrollment number or password!\nPlease register first if you don't have an account.");
                }

                // create the student object with the name from the file
                currentStudent = new Student(name, enrollment);
                // clear fields for next time
                enrollField.setText("");
                passField.setText("");
                cardLayout.show(mainPanel, "selection");

            } catch (IllegalArgumentException ex) {
                // show error message
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Login Failed", JOptionPane.WARNING_MESSAGE);
            }
        });

        // separator line
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(300, 1));
        separator.setForeground(new Color(200, 200, 210));

        // "New Student? Register Here" - clearly visible button
        JLabel noAccountLabel = new JLabel("New Student? Create an account");
        noAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        noAccountLabel.setForeground(TEXT_SECONDARY);
        noAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton registerBtn = createStyledButton("Register Here", SUCCESS_COLOR, Color.WHITE);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(300, 42));
        registerBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        // add everything to the card with spacing
        loginCard.add(title);
        loginCard.add(Box.createVerticalStrut(5));
        loginCard.add(subtitle);
        loginCard.add(Box.createVerticalStrut(25));
        loginCard.add(enrollLabel);
        loginCard.add(Box.createVerticalStrut(5));
        loginCard.add(enrollField);
        loginCard.add(Box.createVerticalStrut(12));
        loginCard.add(passLabel);
        loginCard.add(Box.createVerticalStrut(5));
        loginCard.add(passField);
        loginCard.add(Box.createVerticalStrut(20));
        loginCard.add(loginBtn);
        loginCard.add(Box.createVerticalStrut(15));
        loginCard.add(separator);
        loginCard.add(Box.createVerticalStrut(12));
        loginCard.add(noAccountLabel);
        loginCard.add(Box.createVerticalStrut(8));
        loginCard.add(registerBtn);

        panel.add(loginCard);
        return panel;
    }

    // ==========================================
    // REGISTER / SIGN UP SCREEN
    // ==========================================
    private JPanel createRegisterScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);

        JPanel registerCard = new JPanel();
        registerCard.setLayout(new BoxLayout(registerCard, BoxLayout.Y_AXIS));
        registerCard.setBackground(CARD_COLOR);
        registerCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210), 1),
            BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
        registerCard.setPreferredSize(new Dimension(420, 500));

        // title
        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setMaximumSize(new Dimension(300, 40));

        JLabel subtitle = new JLabel("Register to get started", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setMaximumSize(new Dimension(300, 25));

        // name field
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameLabel.setForeground(TEXT_SECONDARY);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField(20);
        styleTextField(nameField);
        nameField.setMaximumSize(new Dimension(300, 40));

        // enrollment field
        JLabel enrollLabel = new JLabel("Enrollment Number");
        enrollLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        enrollLabel.setForeground(TEXT_SECONDARY);
        enrollLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField enrollField = new JTextField(20);
        styleTextField(enrollField);
        enrollField.setMaximumSize(new Dimension(300, 40));

        // password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passLabel.setForeground(TEXT_SECONDARY);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passField = new JPasswordField(20);
        stylePasswordField(passField);
        passField.setMaximumSize(new Dimension(300, 40));

        // confirm password field
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        confirmLabel.setForeground(TEXT_SECONDARY);
        confirmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField confirmField = new JPasswordField(20);
        stylePasswordField(confirmField);
        confirmField.setMaximumSize(new Dimension(300, 40));

        // register button
        JButton registerBtn = createStyledButton("Register", SUCCESS_COLOR, Color.WHITE);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(300, 42));

        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String enrollment = enrollField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            String confirm = new String(confirmField.getPassword()).trim();

            // validation with exception handling
            try {
                if (name.isEmpty() || enrollment.isEmpty() || 
                    password.isEmpty() || confirm.isEmpty()) {
                    throw new IllegalArgumentException("Please fill in all fields!");
                }
                if (password.length() < 4) {
                    throw new IllegalArgumentException("Password must be at least 4 characters!");
                }
                if (!password.equals(confirm)) {
                    throw new IllegalArgumentException("Passwords do not match!");
                }

                // try to register the user
                boolean success = UserFileHandler.registerUser(name, enrollment, password);
                if (!success) {
                    throw new IllegalArgumentException(
                        "This enrollment number is already registered!\nPlease login instead.");
                }

                // registration successful - show message and go to login
                JOptionPane.showMessageDialog(this,
                    "Registration successful!\nYou can now login with your credentials.",
                    "Welcome!", JOptionPane.INFORMATION_MESSAGE);

                // clear fields
                nameField.setText("");
                enrollField.setText("");
                passField.setText("");
                confirmField.setText("");

                cardLayout.show(mainPanel, "login");

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Registration Failed", JOptionPane.WARNING_MESSAGE);
            }
        });

        // "Already have an account? Login" link
        JPanel loginLinkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        loginLinkPanel.setBackground(CARD_COLOR);
        loginLinkPanel.setMaximumSize(new Dimension(300, 30));

        JLabel haveAccountLabel = new JLabel("Already have an account?");
        haveAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        haveAccountLabel.setForeground(TEXT_SECONDARY);

        JButton loginLink = new JButton("Login");
        loginLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loginLink.setForeground(ACCENT_COLOR);
        loginLink.setBackground(CARD_COLOR);
        loginLink.setBorderPainted(false);
        loginLink.setFocusPainted(false);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        loginLinkPanel.add(haveAccountLabel);
        loginLinkPanel.add(loginLink);

        // add everything to card
        registerCard.add(title);
        registerCard.add(Box.createVerticalStrut(5));
        registerCard.add(subtitle);
        registerCard.add(Box.createVerticalStrut(20));
        registerCard.add(nameLabel);
        registerCard.add(Box.createVerticalStrut(5));
        registerCard.add(nameField);
        registerCard.add(Box.createVerticalStrut(12));
        registerCard.add(enrollLabel);
        registerCard.add(Box.createVerticalStrut(5));
        registerCard.add(enrollField);
        registerCard.add(Box.createVerticalStrut(12));
        registerCard.add(passLabel);
        registerCard.add(Box.createVerticalStrut(5));
        registerCard.add(passField);
        registerCard.add(Box.createVerticalStrut(12));
        registerCard.add(confirmLabel);
        registerCard.add(Box.createVerticalStrut(5));
        registerCard.add(confirmField);
        registerCard.add(Box.createVerticalStrut(20));
        registerCard.add(registerBtn);
        registerCard.add(Box.createVerticalStrut(12));
        registerCard.add(loginLinkPanel);

        panel.add(registerCard);
        return panel;
    }

    // ==========================================
    // EXAM SELECTION SCREEN
    // ==========================================
    private JPanel createExamSelectionScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // header
        JLabel header = new JLabel("Select an Exam", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(header, BorderLayout.NORTH);

        // exam cards grid
        JPanel examGrid = new JPanel(new GridLayout(2, 2, 15, 15));
        examGrid.setBackground(BG_COLOR);

        ArrayList<Exam> exams = examManager.getAvailableExams();
        // icons for each exam (using unicode emojis basically)
        String[] icons = {"☕", "🔷", "🗄", "🐍"};
        Color[] cardColors = {
            new Color(230, 240, 255), new Color(255, 235, 235),
            new Color(245, 235, 255), new Color(235, 255, 235)
        };

        for (int i = 0; i < exams.size(); i++) {
            Exam exam = exams.get(i);
            JPanel card = createExamCard(exam, icons[i], cardColors[i]);
            examGrid.add(card);
        }

        panel.add(examGrid, BorderLayout.CENTER);

        // bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(BG_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton viewResultsBtn = createStyledButton("View Past Results", 
            new Color(200, 205, 220), TEXT_COLOR);
        viewResultsBtn.addActionListener(e -> showPastResults());

        JButton logoutBtn = createStyledButton("Logout", 
            new Color(255, 210, 210), TEXT_COLOR);
        logoutBtn.addActionListener(e -> {
            currentStudent = null;
            cardLayout.show(mainPanel, "login");
        });

        bottomPanel.add(viewResultsBtn);
        bottomPanel.add(logoutBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    // helper to create a clickable exam card
    private JPanel createExamCard(Exam exam, String icon, Color bgColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 205, 220), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(exam.getExamName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel(exam.getTotalQuestions() + " Questions | " 
            + exam.getDuration() + "s", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLabel.setForeground(TEXT_SECONDARY);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // if it's an MCQ exam, show marks info
        if (exam instanceof MultipleChoiceExam) {
            MultipleChoiceExam mcq = (MultipleChoiceExam) exam;
            JLabel marksLabel = new JLabel(mcq.getMarksPerQuestion() + " marks/question", 
                SwingConstants.CENTER);
            marksLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            marksLabel.setForeground(ACCENT_COLOR);
            marksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            card.add(iconLabel);
            card.add(Box.createVerticalStrut(10));
            card.add(nameLabel);
            card.add(Box.createVerticalStrut(5));
            card.add(infoLabel);
            card.add(Box.createVerticalStrut(3));
            card.add(marksLabel);
        } else {
            card.add(iconLabel);
            card.add(Box.createVerticalStrut(10));
            card.add(nameLabel);
            card.add(Box.createVerticalStrut(5));
            card.add(infoLabel);
        }

        // click to start exam
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    OnlineExamSystem.this,
                    "Start \"" + exam.getExamName() + "\" exam?\n" +
                    "Time: " + exam.getDuration() + " seconds\n" +
                    "Questions: " + exam.getTotalQuestions(),
                    "Confirm", JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    startExam(exam);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(bgColor.brighter());
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_COLOR, 2),
                    BorderFactory.createEmptyBorder(19, 19, 19, 19)
                ));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(bgColor);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 205, 220), 1),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });

        return card;
    }

    // ==========================================
    // EXAM SCREEN
    // ==========================================
    private void startExam(Exam exam) {
        examManager.setCurrentExam(exam);
        currentQuestionIndex = 0;

        // create exam screen panel
        JPanel examPanel = new JPanel(new BorderLayout());
        examPanel.setBackground(BG_COLOR);

        // --- TOP BAR ---
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 245, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel examTitle = new JLabel(exam.getExamName());
        examTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        examTitle.setForeground(TEXT_COLOR);

        timerLabel = new JLabel("  Time Left: --:--  ");
        timerLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        timerLabel.setForeground(SUCCESS_COLOR);
        timerLabel.setOpaque(true);
        timerLabel.setBackground(new Color(230, 235, 245));
        timerLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        topBar.add(examTitle, BorderLayout.WEST);
        topBar.add(timerLabel, BorderLayout.EAST);
        examPanel.add(topBar, BorderLayout.NORTH);

        // --- CENTER: Question area ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        // question card
        JPanel questionCard = new JPanel();
        questionCard.setLayout(new BoxLayout(questionCard, BoxLayout.Y_AXIS));
        questionCard.setBackground(CARD_COLOR);
        questionCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        questionNumberLabel = new JLabel("Question 1 of " + exam.getTotalQuestions());
        questionNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        questionNumberLabel.setForeground(ACCENT_COLOR);
        questionNumberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        questionLabel.setForeground(TEXT_COLOR);
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        questionCard.add(questionNumberLabel);
        questionCard.add(Box.createVerticalStrut(12));
        questionCard.add(questionLabel);
        questionCard.add(Box.createVerticalStrut(20));

        // radio buttons for options
        optionGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            optionButtons[i].setForeground(TEXT_COLOR);
            optionButtons[i].setBackground(CARD_COLOR);
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            optionButtons[i].setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            optionGroup.add(optionButtons[i]);
            questionCard.add(optionButtons[i]);

            // save answer whenever an option is clicked
            final int optIndex = i;
            optionButtons[i].addActionListener(e -> {
                examManager.setAnswer(currentQuestionIndex, optIndex);
                updateNavGrid(); // update the navigation grid colors
            });
        }

        centerPanel.add(questionCard, BorderLayout.CENTER);

        // --- RIGHT SIDE: Navigation grid ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BG_COLOR);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        rightPanel.setPreferredSize(new Dimension(180, 0));

        JLabel navTitle = new JLabel("Questions", SwingConstants.CENTER);
        navTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        navTitle.setForeground(TEXT_SECONDARY);
        navTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        rightPanel.add(navTitle, BorderLayout.NORTH);

        // create grid of question numbers
        int totalQ = exam.getTotalQuestions();
        int cols = 5;
        int rows = (int) Math.ceil((double) totalQ / cols);
        navGridPanel = new JPanel(new GridLayout(rows, cols, 4, 4));
        navGridPanel.setBackground(BG_COLOR);

        for (int i = 0; i < totalQ; i++) {
            JButton numBtn = new JButton(String.valueOf(i + 1));
            numBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            numBtn.setForeground(TEXT_COLOR);
            numBtn.setBackground(new Color(220, 220, 230));
            numBtn.setFocusPainted(false);
            numBtn.setBorderPainted(false);
            numBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            numBtn.setMargin(new Insets(4, 4, 4, 4));

            final int qIndex = i;
            numBtn.addActionListener(e -> {
                saveCurrentAnswer();
                currentQuestionIndex = qIndex;
                loadQuestion(currentQuestionIndex);
            });
            navGridPanel.add(numBtn);
        }

        rightPanel.add(navGridPanel, BorderLayout.CENTER);

        // legend for nav grid
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 3));
        legendPanel.setBackground(BG_COLOR);
        legendPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        legendPanel.add(createLegendItem(ACCENT_COLOR, "Current"));
        legendPanel.add(createLegendItem(SUCCESS_COLOR, "Answered"));
        legendPanel.add(createLegendItem(new Color(220, 220, 230), "Not done"));

        rightPanel.add(legendPanel, BorderLayout.SOUTH);
        centerPanel.add(rightPanel, BorderLayout.EAST);

        examPanel.add(centerPanel, BorderLayout.CENTER);

        // --- BOTTOM: Navigation buttons ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        bottomPanel.setBackground(new Color(240, 245, 250));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JButton prevBtn = createStyledButton("← Previous", new Color(200, 205, 220), TEXT_COLOR);
        JButton nextBtn = createStyledButton("Next →", new Color(200, 205, 220), TEXT_COLOR);
        JButton submitBtn = createStyledButton("Submit Exam", ERROR_COLOR, Color.WHITE);

        prevBtn.addActionListener(e -> {
            saveCurrentAnswer();
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion(currentQuestionIndex);
            }
        });

        nextBtn.addActionListener(e -> {
            saveCurrentAnswer();
            if (currentQuestionIndex < exam.getTotalQuestions() - 1) {
                currentQuestionIndex++;
                loadQuestion(currentQuestionIndex);
            }
        });

        submitBtn.addActionListener(e -> {
            saveCurrentAnswer();
            int unanswered = 0;
            for (int i = 0; i < exam.getTotalQuestions(); i++) {
                if (examManager.getAnswer(i) == -1) unanswered++;
            }
            String msg = "Are you sure you want to submit?";
            if (unanswered > 0) {
                msg += "\n\nYou have " + unanswered + " unanswered question(s)!";
            }
            int confirm = JOptionPane.showConfirmDialog(this, msg, 
                "Submit Exam", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                submitExam();
            }
        });

        bottomPanel.add(prevBtn);
        bottomPanel.add(nextBtn);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(submitBtn);
        examPanel.add(bottomPanel, BorderLayout.SOUTH);

        // add exam panel and switch to it
        mainPanel.add(examPanel, "exam");
        cardLayout.show(mainPanel, "exam");

        // load first question
        loadQuestion(0);

        // start the timer on a separate thread
        examTimer = new ExamTimer(exam.getDuration(), timerLabel, () -> {
            // this runs when time is up (callback)
            try {
                throw new ExamTimeoutException("Time's up! Auto-submitting your exam.");
            } catch (ExamTimeoutException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Time Up!", JOptionPane.INFORMATION_MESSAGE);
                submitExam();
            }
        });
        timerThread = new Thread(examTimer);
        timerThread.start(); // start the countdown on a new thread
    }

    // load a specific question's data onto the screen
    private void loadQuestion(int index) {
        Exam exam = examManager.getCurrentExam();
        Question q = exam.getQuestions().get(index);

        questionNumberLabel.setText("Question " + (index + 1) + " of " + exam.getTotalQuestions());
        questionLabel.setText("<html><body style='width: 350px'>" + q.getQuestionText() + "</body></html>");

        // set option texts
        optionGroup.clearSelection();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q.getOption(i));
        }

        // if student already answered this question, select that option
        int savedAnswer = examManager.getAnswer(index);
        if (savedAnswer >= 0 && savedAnswer < 4) {
            optionButtons[savedAnswer].setSelected(true);
        }

        updateNavGrid();
    }

    // save whatever option is currently selected
    private void saveCurrentAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                examManager.setAnswer(currentQuestionIndex, i);
                return;
            }
        }
    }

    // update the question navigation grid colors
    private void updateNavGrid() {
        Component[] buttons = navGridPanel.getComponents();
        for (int i = 0; i < buttons.length; i++) {
            JButton btn = (JButton) buttons[i];
            if (i == currentQuestionIndex) {
                btn.setBackground(ACCENT_COLOR); // current question
                btn.setForeground(Color.WHITE);
            } else if (examManager.getAnswer(i) != -1) {
                btn.setBackground(SUCCESS_COLOR); // answered
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(new Color(220, 220, 230)); // not answered
                btn.setForeground(TEXT_COLOR);
            }
        }
    }

    // create a small colored square with label for the legend
    private JPanel createLegendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        item.setBackground(BG_COLOR);
        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(10, 10));
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        label.setForeground(TEXT_SECONDARY);
        item.add(colorBox);
        item.add(label);
        return item;
    }

    // ==========================================
    // SUBMIT AND SHOW RESULTS
    // ==========================================
    private void submitExam() {
        // stop the timer
        if (examTimer != null) {
            examTimer.stop();
        }

        // get results using ExamManager (which uses the inner Grader class)
        int[] results = examManager.getResults();
        int correct = results[0];
        int wrong = results[1];
        int unanswered = results[2];
        int total = examManager.getCurrentExam().getTotalQuestions();
        double percentage = (correct * 100.0) / total;

        // figure out the grade
        String grade;
        Color gradeColor;
        if (percentage >= 90) { grade = "A+"; gradeColor = SUCCESS_COLOR; }
        else if (percentage >= 80) { grade = "A"; gradeColor = SUCCESS_COLOR; }
        else if (percentage >= 70) { grade = "B"; gradeColor = ACCENT_COLOR; }
        else if (percentage >= 60) { grade = "C"; gradeColor = WARNING_COLOR; }
        else if (percentage >= 50) { grade = "D"; gradeColor = WARNING_COLOR; }
        else { grade = "F"; gradeColor = ERROR_COLOR; }

        // save result to file (FILE HANDLING)
        ResultFileHandler.saveResult(
            currentStudent.getName(),
            currentStudent.getEnrollmentNo(),
            examManager.getCurrentExam().getExamName(),
            correct, total, percentage, grade
        );

        // create result screen
        showResultScreen(correct, wrong, unanswered, total, percentage, grade, gradeColor);
    }

    // ==========================================
    // RESULT SCREEN
    // ==========================================
    private void showResultScreen(int correct, int wrong, int unanswered,
                                   int total, double percentage, String grade, Color gradeColor) {
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(BG_COLOR);
        resultPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        // header
        JLabel header = new JLabel("Exam Results", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        resultPanel.add(header, BorderLayout.NORTH);

        // center content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BG_COLOR);

        // score card
        JPanel scoreCard = new JPanel();
        scoreCard.setLayout(new BoxLayout(scoreCard, BoxLayout.Y_AXIS));
        scoreCard.setBackground(CARD_COLOR);
        scoreCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        scoreCard.setMaximumSize(new Dimension(600, 250));
        scoreCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        // student info
        JLabel studentInfo = new JLabel("Student: " + currentStudent.getName() + 
            " | " + currentStudent.getEnrollmentNo());
        studentInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentInfo.setForeground(TEXT_SECONDARY);
        studentInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel examInfo = new JLabel("Exam: " + examManager.getCurrentExam().getExamName());
        examInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        examInfo.setForeground(TEXT_SECONDARY);
        examInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // big grade display
        JLabel gradeLabel = new JLabel(grade);
        gradeLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));
        gradeLabel.setForeground(gradeColor);
        gradeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel percentLabel = new JLabel(String.format("%.1f%%", percentage));
        percentLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        percentLabel.setForeground(TEXT_COLOR);
        percentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // stats row
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 5));
        statsPanel.setBackground(CARD_COLOR);

        statsPanel.add(createStatLabel("✓ Correct", String.valueOf(correct), SUCCESS_COLOR));
        statsPanel.add(createStatLabel("✗ Wrong", String.valueOf(wrong), ERROR_COLOR));
        statsPanel.add(createStatLabel("— Skipped", String.valueOf(unanswered), WARNING_COLOR));
        statsPanel.add(createStatLabel("Total", String.valueOf(total), ACCENT_COLOR));

        scoreCard.add(studentInfo);
        scoreCard.add(Box.createVerticalStrut(3));
        scoreCard.add(examInfo);
        scoreCard.add(Box.createVerticalStrut(10));
        scoreCard.add(gradeLabel);
        scoreCard.add(percentLabel);
        scoreCard.add(Box.createVerticalStrut(12));
        scoreCard.add(statsPanel);

        centerPanel.add(scoreCard);
        centerPanel.add(Box.createVerticalStrut(12));

        // answer breakdown - scrollable list
        JPanel breakdownPanel = new JPanel();
        breakdownPanel.setLayout(new BoxLayout(breakdownPanel, BoxLayout.Y_AXIS));
        breakdownPanel.setBackground(BG_COLOR);

        JLabel bTitle = new JLabel("Answer Breakdown");
        bTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        bTitle.setForeground(TEXT_COLOR);
        bTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        breakdownPanel.add(bTitle);
        breakdownPanel.add(Box.createVerticalStrut(8));

        ArrayList<Question> questions = examManager.getCurrentExam().getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int studentAns = examManager.getAnswer(i);
            int correctAns = q.getCorrectOptionIndex();
            boolean isRight = (studentAns == correctAns);

            JPanel qRow = new JPanel(new BorderLayout());
            qRow.setBackground(isRight ? new Color(235, 255, 235) : new Color(255, 235, 235));
            qRow.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    isRight ? new Color(150, 220, 150) : new Color(250, 150, 150), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            qRow.setMaximumSize(new Dimension(600, 100));
            qRow.setAlignmentX(Component.LEFT_ALIGNMENT);

            String qText = "Q" + (i+1) + ": " + q.getQuestionText();
            String ansText;
            if (studentAns == -1) {
                ansText = "Skipped | Correct: " + q.getOption(correctAns);
            } else {
                ansText = "Your answer: " + q.getOption(studentAns) + 
                          " | Correct: " + q.getOption(correctAns);
            }

            JLabel qLabel = new JLabel("<html><b>" + qText + "</b></html>");
            qLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            qLabel.setForeground(TEXT_COLOR);

            JLabel aLabel = new JLabel(ansText);
            aLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            aLabel.setForeground(isRight ? SUCCESS_COLOR : 
                                 (studentAns == -1 ? WARNING_COLOR : ERROR_COLOR));

            qRow.add(qLabel, BorderLayout.NORTH);
            qRow.add(aLabel, BorderLayout.SOUTH);
            breakdownPanel.add(qRow);
            breakdownPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scroll = new JScrollPane(breakdownPanel);
        scroll.setBackground(BG_COLOR);
        scroll.getViewport().setBackground(BG_COLOR);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setPreferredSize(new Dimension(600, 200));

        centerPanel.add(scroll);
        resultPanel.add(centerPanel, BorderLayout.CENTER);

        // bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        bottomPanel.setBackground(BG_COLOR);

        JButton backBtn = createStyledButton("Back to Exams", ACCENT_COLOR, Color.WHITE);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "selection"));

        JButton exitBtn = createStyledButton("Exit", new Color(255, 210, 210), TEXT_COLOR);
        exitBtn.addActionListener(e -> System.exit(0));

        bottomPanel.add(backBtn);
        bottomPanel.add(exitBtn);
        resultPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(resultPanel, "result");
        cardLayout.show(mainPanel, "result");
    }

    // helper to create a stat label (like "Correct: 3")
    private JPanel createStatLabel(String title, String value, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);

        JLabel valLabel = new JLabel(value, SwingConstants.CENTER);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valLabel.setForeground(color);
        valLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(valLabel);
        panel.add(titleLabel);
        return panel;
    }

    // show past results dialog
    private void showPastResults() {
        // only show results for the currently logged in student
        String results = ResultFileHandler.readResultsByEnrollment(
            currentStudent.getEnrollmentNo()
        );
        JTextArea textArea = new JTextArea(results);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(CARD_COLOR);
        textArea.setForeground(TEXT_COLOR);
        textArea.setCaretColor(TEXT_COLOR);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 350));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210)));

        JOptionPane.showMessageDialog(this, scrollPane, 
            "My Past Results", JOptionPane.PLAIN_MESSAGE);
    }

    // ==========================================
    // HELPER METHODS for styling
    // ==========================================
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(TEXT_COLOR);
        field.setBackground(new Color(240, 245, 250));
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    // same styling but for password fields (JPasswordField extends JTextField)
    private void stylePasswordField(JPasswordField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(TEXT_COLOR);
        field.setBackground(new Color(240, 245, 250));
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 38));

        // hover effect
        Color hoverColor = bgColor.brighter();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // ==========================================
    // MAIN METHOD - this is where the app starts
    // ==========================================
    public static void main(String[] args) {
        // set look and feel to make it look nicer
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // if it fails, no big deal, default look is fine too
            System.out.println("Could not set look and feel: " + e.getMessage());
        }

        // run the GUI on the event dispatch thread (Swing best practice)
        SwingUtilities.invokeLater(() -> {
            OnlineExamSystem app = new OnlineExamSystem();
            app.setVisible(true);
        });
    }
}
