// Program6
// Display all files of a given directory using File class

import java.io.File;

public class Program6 {

    public static void main(String[] args) {

        // Change path as per your system
        String path = "C:\\Users\\HP\\OneDrive\\Desktop\\12402080601098javaAssigment\\ASSIGNMENT-2";

        File dir = new File(path);

        // Check if directory exists
        if (dir.exists() && dir.isDirectory()) {

            File[] files = dir.listFiles();

            System.out.println("Files in directory:");

            for (File f : files) {

                if (f.isFile()) {
                    System.out.println(f.getName());
                }
            }

        } else {
            System.out.println("Invalid directory!");
        }
    }
}