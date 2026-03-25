// Program1
// Find prime numbers using multithreading (Thread, Runnable, Executor Framework)

import java.util.Scanner;
import java.util.concurrent.*;

// Utility class
class PrimeUtil {

    static boolean isPrime(int n) {

        if (n < 2)
            return false;

        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0)
                return false;
        }

        return true;
    }
}

// Using Thread
class PrimeThread extends Thread {

    int start, end;

    PrimeThread(int s, int e) {
        start = s;
        end = e;
    }

    public void run() {
        System.out.println("\nUsing Thread:");

        for (int i = start; i <= end; i++) {
            if (PrimeUtil.isPrime(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}

// Using Runnable
class PrimeRunnable implements Runnable {

    int start, end;

    PrimeRunnable(int s, int e) {
        start = s;
        end = e;
    }

    public void run() {
        System.out.println("\nUsing Runnable:");

        for (int i = start; i <= end; i++) {
            if (PrimeUtil.isPrime(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}

// Main class
public class Program1 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter start: ");
        int start = sc.nextInt();

        System.out.print("Enter end: ");
        int end = sc.nextInt();

        // Thread
        PrimeThread t1 = new PrimeThread(start, end);
        t1.start();

        // Runnable
        Thread t2 = new Thread(new PrimeRunnable(start, end));
        t2.start();

        // Executor Framework
        ExecutorService executor = Executors.newSingleThreadExecutor();

        System.out.println("\nUsing Executor:");
        executor.execute(new PrimeRunnable(start, end));

        executor.shutdown();

        sc.close();
    }
}