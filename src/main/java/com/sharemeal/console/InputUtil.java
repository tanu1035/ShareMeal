package com.sharemeal.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/** Small helper around Scanner so every menu reads input the same way. */
@Component
public class InputUtil {

    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    /** Keeps asking until the user enters a whole number between min and max (inclusive). */
    public int readChoice(String prompt, int min, int max) {
        while (true) {
            String line = readLine(prompt);
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the message below
            }
            System.out.printf("Please enter a number between %d and %d.%n", min, max);
        }
    }
}
