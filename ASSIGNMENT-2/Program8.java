// Program8
// GUI-based Investment Calculator using Swing

import javax.swing.*;
import java.awt.event.*;

public class Program8 {

    public static void main(String[] args) {

        // Create frame
        JFrame f = new JFrame("Investment Calculator");

        // Labels
        JLabel l1 = new JLabel("Principal:");
        JLabel l2 = new JLabel("Rate (%):");
        JLabel l3 = new JLabel("Time (years):");
        JLabel l4 = new JLabel("Result:");

        // Text fields
        JTextField t1 = new JTextField();
        JTextField t2 = new JTextField();
        JTextField t3 = new JTextField();

        // Button
        JButton btn = new JButton("Calculate");

        // Result label
        JLabel result = new JLabel("");

        // Set bounds
        l1.setBounds(50, 50, 100, 30);
        t1.setBounds(150, 50, 150, 30);

        l2.setBounds(50, 100, 100, 30);
        t2.setBounds(150, 100, 150, 30);

        l3.setBounds(50, 150, 100, 30);
        t3.setBounds(150, 150, 150, 30);

        btn.setBounds(100, 200, 120, 30);

        l4.setBounds(50, 250, 100, 30);
        result.setBounds(150, 250, 200, 30);

        // Add action
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                double p = Double.parseDouble(t1.getText());
                double r = Double.parseDouble(t2.getText());
                double t = Double.parseDouble(t3.getText());

                double si = (p * r * t) / 100;

                result.setText(String.valueOf(si));
            }
        });

        // Add components
        f.add(l1); f.add(t1);
        f.add(l2); f.add(t2);
        f.add(l3); f.add(t3);
        f.add(btn);
        f.add(l4); f.add(result);

        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}