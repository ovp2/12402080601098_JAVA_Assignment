// Problem statement
// Implement Cipher system using Abstract class and method overriding.

import java.util.Scanner;

// Abstract class
abstract class Cipher {

    String text;
    String encrypted; // store encrypted text

    Cipher(String t) {
        text = t;
    }

    abstract void encrypt();
    abstract void decrypt();
}

// Derived class
class CaesarCipher extends Cipher {

    int key;

    CaesarCipher(String t, int k) {
        super(t);
        key = k;
    }

    // Encrypt method
    void encrypt() {

        encrypted = "";

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                ch = (char) ((ch - base + key) % 26 + base);
            }

            encrypted = encrypted + ch;
        }

        System.out.println("Encrypted Text: " + encrypted);
    }

    // Decrypt method (NOW CORRECT ✔)
    void decrypt() {

        String result = "";

        for (int i = 0; i < encrypted.length(); i++) {
            char ch = encrypted.charAt(i);

            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                ch = (char) ((ch - base - key + 26) % 26 + base);
            }

            result = result + ch;
        }

        System.out.println("Decrypted Text: " + result);
    }
}

// Main class
public class Program6 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        System.out.print("Enter key (shift value): ");
        int key = sc.nextInt();

        CaesarCipher obj = new CaesarCipher(text, key);

        obj.encrypt();
        obj.decrypt();

        sc.close();
    }
}