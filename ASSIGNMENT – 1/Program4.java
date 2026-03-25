// Problem statement
// Implement BankAccount class with deposit, withdraw and balance inquiry.

import java.util.Scanner;

// BankAccount class
class BankAccount {

    int accNo;
    String name;
    double balance;

    // Constructor
    BankAccount(int accNo, String name, double balance) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
    }

    // Deposit method
    void deposit(double amount) {
        balance = balance + amount;
        System.out.println("Amount deposited: " + amount);
    }

    // Withdraw method
    void withdraw(double amount) {

        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance = balance - amount;
            System.out.println("Amount withdrawn: " + amount);
        }
    }

    // Balance inquiry
    void displayBalance() {
        System.out.println("Current Balance: " + balance);
    }

    // Display account details
    void displayDetails() {
        System.out.println("\nAccount No: " + accNo);
        System.out.println("Name: " + name);
        System.out.println("Balance: " + balance);
    }
}

// Main class
public class Program4 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Input account details
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        sc.nextLine(); // clear buffer

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        // Create object
        BankAccount obj = new BankAccount(accNo, name, balance);

        int choice;

        do {
            System.out.println("\n--- Bank Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Balance Inquiry");
            System.out.println("4. Display Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double dep = sc.nextDouble();
                    obj.deposit(dep);
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double wd = sc.nextDouble();
                    obj.withdraw(wd);
                    break;

                case 3:
                    obj.displayBalance();
                    break;

                case 4:
                    obj.displayDetails();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close(); // close scanner
    }
}