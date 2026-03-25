// Problem statement
// Demonstrate Inner Classes (member, local, anonymous)

// Outer class
class Outer {

    int x = 10;

    // 1. Member Inner Class
    class MemberInner {
        void display() {
            System.out.println("Member Inner Class: x = " + x);
        }
    }

    // Method to demonstrate local and anonymous class
    void show() {

        // 2. Local Inner Class
        class LocalInner {
            void display() {
                System.out.println("Local Inner Class: x = " + x);
            }
        }

        LocalInner obj1 = new LocalInner();
        obj1.display();

        // 3. Anonymous Inner Class
        Runnable obj2 = new Runnable() {
            public void run() {
                System.out.println("Anonymous Inner Class: x = " + x);
            }
        };

        obj2.run();
    }
}

// Main class
public class Program7 {

    public static void main(String[] args) {

        Outer outer = new Outer();

        // Member Inner Class
        Outer.MemberInner obj = outer.new MemberInner();
        obj.display();

        // Local + Anonymous
        outer.show();
    }
}