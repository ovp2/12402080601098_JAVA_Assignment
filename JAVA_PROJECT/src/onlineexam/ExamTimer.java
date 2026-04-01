package onlineexam;

import javax.swing.*;

// This class runs the countdown timer on a SEPARATE THREAD
// it implements Runnable - this is how we do THREADING in Java

public class ExamTimer implements Runnable {
    private int totalSeconds;
    private JLabel timerLabel; // the label on the GUI that shows time
    private boolean running;
    private Runnable onTimeUp; // what to do when time is up

    public ExamTimer(int totalSeconds, JLabel timerLabel, Runnable onTimeUp) {
        this.totalSeconds = totalSeconds;
        this.timerLabel = timerLabel;
        this.running = true;
        this.onTimeUp = onTimeUp;
    }

    @Override
    public void run() {
        // this runs on a separate thread
        while (totalSeconds > 0 && running) {
            // calculate minutes and seconds for display
            int mins = totalSeconds / 60;
            int secs = totalSeconds % 60;
            String timeStr = String.format("  Time Left: %02d:%02d  ", mins, secs);

            // we need to update the GUI from the event thread
            // SwingUtilities.invokeLater makes sure it's thread-safe
            SwingUtilities.invokeLater(() -> {
                timerLabel.setText(timeStr);
                // change color when less than 30 seconds
                if (totalSeconds <= 30) {
                    timerLabel.setForeground(new java.awt.Color(220, 50, 50));
                } else if (totalSeconds <= 60) {
                    timerLabel.setForeground(new java.awt.Color(230, 150, 0));
                }
            });

            try {
                Thread.sleep(1000); // wait 1 second
            } catch (InterruptedException e) {
                // if thread is interrupted, just stop
                break;
            }

            totalSeconds--;
        }

        // if time ran out (not manually stopped)
        if (running && totalSeconds <= 0) {
            SwingUtilities.invokeLater(() -> {
                timerLabel.setText("  TIME'S UP!  ");
                timerLabel.setForeground(new java.awt.Color(220, 50, 50));
                // call the callback function
                if (onTimeUp != null) {
                    onTimeUp.run();
                }
            });
        }
    }

    // method to stop the timer (when student submits early)
    public void stop() {
        running = false;
    }

    public int getRemainingTime() {
        return totalSeconds;
    }
}
