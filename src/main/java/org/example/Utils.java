package org.example;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {

    private static final Scanner in = new Scanner(System.in);

    public static void createPoints(String... points) {
        int i = 1;
        for (String point : points) {
            System.out.println(i + ") " + point);
            i++;
        }
        System.out.print("\n> ");
    }

    public static String askLine(String message) {
        while (true) {
            System.out.println(message);
            System.out.print("> ");
            String line = in.nextLine().trim();
            if (line.isBlank()) {
                System.out.println("Це поле не може бути пустим");
            } else {
                return line;
            }
        }
    }


    public static int askInt(int min, int max) {
        while (true) {
            String s = in.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < min || v > max) {
                    System.out.println("Діапазон: " + min + "..." + max);
                } else return v;
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть число:");
                System.out.print("> ");
            }
        }
    }

    public static int askIntWithoutLimit(String message){
        System.out.println(message);
        System.out.print("> ");
        while (true) {
            String s = in.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть число:");
                System.out.print("> ");
            }
        }
    }
}
