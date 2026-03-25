// Problem statement
// Develop Matrix class with constructors, transpose and multiplication.

import java.util.Scanner;

// Matrix class
class Matrix {

    int rows, cols;
    int[][] mat;

    // Default constructor
    Matrix() {
        rows = 0;
        cols = 0;
    }

    // Parameterized constructor
    Matrix(int r, int c) {
        rows = r;
        cols = c;
        mat = new int[rows][cols];
    }

    // Input matrix
    void input(Scanner sc) {
        System.out.println("Enter elements:");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = sc.nextInt();
            }
        }
    }

    // Display matrix
    void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Transpose
    Matrix transpose() {
        Matrix t = new Matrix(cols, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                t.mat[j][i] = mat[i][j];
            }
        }

        return t;
    }

    // Multiplication
    Matrix multiply(Matrix m2) {

        if (this.cols != m2.rows) {
            System.out.println("Multiplication not possible!");
            return null;
        }

        Matrix result = new Matrix(this.rows, m2.cols);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < m2.cols; j++) {

                result.mat[i][j] = 0;

                for (int k = 0; k < this.cols; k++) {
                    result.mat[i][j] += this.mat[i][k] * m2.mat[k][j];
                }
            }
        }

        return result;
    }
}

// Main class
public class Program2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Matrix 1
        System.out.print("Enter rows and columns of Matrix 1: ");
        int r1 = sc.nextInt();
        int c1 = sc.nextInt();

        Matrix m1 = new Matrix(r1, c1);
        m1.input(sc);

        // Matrix 2
        System.out.print("Enter rows and columns of Matrix 2: ");
        int r2 = sc.nextInt();
        int c2 = sc.nextInt();

        Matrix m2 = new Matrix(r2, c2);
        m2.input(sc);

        // Display matrices
        System.out.println("\nMatrix 1:");
        m1.display();

        System.out.println("\nMatrix 2:");
        m2.display();

        // Transpose of Matrix 1
        Matrix t = m1.transpose();
        System.out.println("\nTranspose of Matrix 1:");
        t.display();

        // Multiplication
        Matrix result = m1.multiply(m2);

        if (result != null) {
            System.out.println("\nMultiplication Result:");
            result.display();
        }

        sc.close(); // close scanner
    }
}