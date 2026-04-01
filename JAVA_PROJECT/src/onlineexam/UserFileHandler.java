package onlineexam;

import java.io.*;

// This class handles saving/loading user credentials to a file
// so students can register and login with a password
// uses FILE HANDLING (BufferedReader/BufferedWriter)

public class UserFileHandler {
    private static final String FILE_NAME = "users.txt";

    // save a new user to the file
    // format: name|enrollmentNo|password (pipe-separated)
    public static boolean registerUser(String name, String enrollmentNo, String password) {
        // first check if enrollment number already exists
        if (isEnrollmentTaken(enrollmentNo)) {
            return false; // enrollment already registered
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(name + "|" + enrollmentNo + "|" + password);
            writer.newLine();
            writer.close();
            System.out.println("User registered successfully: " + enrollmentNo);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
            return false;
        }
    }

    // check if an enrollment number is already registered
    public static boolean isEnrollmentTaken(String enrollmentNo) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2 && parts[1].equals(enrollmentNo)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error checking user: " + e.getMessage());
        }
        return false;
    }

    // validate login credentials - returns the user's name if valid, null if not
    public static String validateLogin(String enrollmentNo, String password) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                // check if enrollment and password match
                if (parts.length >= 3 && 
                    parts[1].equals(enrollmentNo) && 
                    parts[2].equals(password)) {
                    reader.close();
                    return parts[0]; // return the name
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error validating login: " + e.getMessage());
        }
        return null; // login failed
    }
}
