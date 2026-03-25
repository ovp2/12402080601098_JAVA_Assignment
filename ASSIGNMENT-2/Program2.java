// Program2
// Producer–Consumer using synchronization and inter-thread communication

// Shared buffer class
class Buffer {

    int data;
    boolean valueSet = false;

    // Producer method
    synchronized void produce(int value) {

        while (valueSet) {
            try {
                wait();
            } catch (Exception e) {
            }
        }

        data = value;
        System.out.println("Produced: " + data);

        valueSet = true;
        notify();
    }

    // Consumer method
    synchronized void consume() {

        while (!valueSet) {
            try {
                wait();
            } catch (Exception e) {
            }
        }

        System.out.println("Consumed: " + data);

        valueSet = false;
        notify();
    }
}

// Producer class
class Producer extends Thread {

    Buffer b;

    Producer(Buffer b) {
        this.b = b;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            b.produce(i);
        }
    }
}

// Consumer class
class Consumer extends Thread {

    Buffer b;

    Consumer(Buffer b) {
        this.b = b;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            b.consume();
        }
    }
}

// Main class
public class Program2 {

    public static void main(String[] args) {

        Buffer b = new Buffer();

        Producer p = new Producer(b);
        Consumer c = new Consumer(b);

        p.start();
        c.start();
    }
}