package org.example.adapter.in;

import java.util.Scanner;

public class PrintingService {
    private static final Scanner scanner = new Scanner(System.in);

    public static void printStartingMessage(String menu) {
        System.out.println(menu);
    }

    public static int askForANumber(String message) {
        System.out.println(message);
        return scanner.nextInt();
    }
}
