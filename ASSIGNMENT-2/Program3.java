// Program3
// CRUD operations using ArrayList, HashMap, TreeMap

import java.util.*;

public class Program3 {

    public static void main(String[] args) {

        // 1. ArrayList
        ArrayList<String> list = new ArrayList<>();

        // CREATE
        list.add("Apple");
        list.add("Banana");
        list.add("Mango");

        // READ
        System.out.println("ArrayList: " + list);

        // UPDATE
        list.set(1, "Orange");

        // DELETE
        list.remove("Mango");

        System.out.println("Updated ArrayList: " + list);


        // 2. HashMap
        HashMap<Integer, String> hm = new HashMap<>();

        // CREATE
        hm.put(1, "Amit");
        hm.put(2, "Ravi");
        hm.put(3, "Neha");

        // READ
        System.out.println("\nHashMap: " + hm);

        // UPDATE
        hm.put(2, "Rahul");

        // DELETE
        hm.remove(3);

        System.out.println("Updated HashMap: " + hm);


        // 3. TreeMap
        TreeMap<Integer, String> tm = new TreeMap<>();

        // CREATE
        tm.put(101, "Math");
        tm.put(102, "Science");
        tm.put(103, "English");

        // READ
        System.out.println("\nTreeMap: " + tm);

        // UPDATE
        tm.put(102, "Physics");

        // DELETE
        tm.remove(103);

        System.out.println("Updated TreeMap: " + tm);
    }
}