// Program4
// Sort Book objects using Comparable and Comparator

import java.util.*;

// Book class implementing Comparable
class Book implements Comparable<Book> {

    int id;
    String name;
    double price;

    // Constructor
    Book(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Comparable (sort by price)
    public int compareTo(Book b) {
        return Double.compare(this.price, b.price);
    }

    // Display method
    void display() {
        System.out.println(id + " " + name + " " + price);
    }
}

// Comparator (sort by name)
class SortByName implements Comparator<Book> {

    public int compare(Book b1, Book b2) {
        return b1.name.compareTo(b2.name);
    }
}

// Main class
public class Program4 {

    public static void main(String[] args) {

        ArrayList<Book> list = new ArrayList<>();

        // Add books
        list.add(new Book(1, "Java", 500));
        list.add(new Book(2, "Python", 300));
        list.add(new Book(3, "C++", 400));

        // Sort using Comparable (price)
        Collections.sort(list);

        System.out.println("Sorted by Price:");
        for (Book b : list) {
            b.display();
        }

        // Sort using Comparator (name)
        Collections.sort(list, new SortByName());

        System.out.println("\nSorted by Name:");
        for (Book b : list) {
            b.display();
        }
    }
}