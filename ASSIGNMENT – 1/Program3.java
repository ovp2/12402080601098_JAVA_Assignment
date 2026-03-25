// Problem statement
// Write a Java program demonstrating Wrapper classes and String vs StringBuffer.

import java.util.Scanner;

// Class for Wrapper Demo
class WrapperDemo {

    void showWrapper() {

        System.out.println("=== Wrapper Class Demo ===");

        // Primitive to Wrapper (Autoboxing)
        int a = 10;
        Integer obj = a;

        System.out.println("Primitive int: " + a);
        System.out.println("Wrapper Integer: " + obj);

        // Wrapper to Primitive (Unboxing)
        int b = obj;
        System.out.println("Unboxed value: " + b);

        // String to int
        String s = "123";
        int num = Integer.parseInt(s);
        System.out.println("String to int: " + num);

        // valueOf()
        Integer val = Integer.valueOf(50);
        System.out.println("Using valueOf(): " + val);
    }
}

// Class for String Operations
class StringDemo {

    void compare(Scanner sc) {

        sc.nextLine(); // clear buffer

        System.out.print("Enter a string: ");
        String str = sc.nextLine();

        System.out.println("\n=== String vs StringBuffer ===");

        // String (Immutable)
        String s1 = str;
        s1.concat(" World");

        System.out.println("String after concat: " + s1);

        // StringBuffer (Mutable)
        StringBuffer sb = new StringBuffer(str);
        sb.append(" World");

        System.out.println("StringBuffer after append: " + sb);

        sb.reverse();
        System.out.println("Reversed StringBuffer: " + sb);
    }
}

// Main class
public class Program3 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        WrapperDemo obj1 = new WrapperDemo();
        obj1.showWrapper();

        StringDemo obj2 = new StringDemo();
        obj2.compare(sc);

        sc.close(); // close scanner
    }
}