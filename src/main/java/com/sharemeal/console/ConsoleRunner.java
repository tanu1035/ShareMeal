package com.sharemeal.console;

import com.sharemeal.dto.RegistrationRequest;
import com.sharemeal.entity.User;
import com.sharemeal.enums.Role;
import com.sharemeal.exception.ShareMealException;
import com.sharemeal.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Entry point of the console UI: welcome menu (register / login), then the role specific menu. */
@Component
@Order(2)
public class ConsoleRunner implements CommandLineRunner {

    private final AuthService authService;
    private final InputUtil input;
    private final Map<Role, RoleMenu> menus;

    public ConsoleRunner(AuthService authService, InputUtil input, List<RoleMenu> roleMenus) {
        this.authService = authService;
        this.input = input;
        this.menus = roleMenus.stream()
                .collect(Collectors.toMap(RoleMenu::role, Function.identity()));
    }

    @Override
    public void run(String... args) {
        System.out.println("=======================================");
        System.out.println("  ShareMeal - Smart Food Donation System");
        System.out.println("=======================================");

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");

            switch (input.readChoice("Choose: ", 0, 2)) {
                case 1 -> handleRegister();
                case 2 -> handleLogin();
                default -> running = false;
            }
        }
        System.out.println("Thank you for helping reduce food waste. Goodbye!");
    }

    private void handleRegister() {
        try {
            System.out.println("\n--- Register ---");
            String name = input.readLine("Name: ");
            String email = input.readLine("Email: ");
            String password = input.readLine("Password (min 6 chars): ");
            String phone = input.readLine("Phone (10 digits): ");
            String address = input.readLine("Address: ");
            int roleChoice = input.readChoice("Register as 1) Donor  2) NGO : ", 1, 2);
            Role role = roleChoice == 1 ? Role.DONOR : Role.NGO;

            User user = authService.register(new RegistrationRequest(name, email, password, phone, address, role));
            System.out.println("Registration successful. Welcome, " + user.getName() + "! You can log in now.");
        } catch (ShareMealException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void handleLogin() {
        try {
            System.out.println("\n--- Login ---");
            String email = input.readLine("Email: ");
            String password = input.readLine("Password: ");

            User user = authService.login(email, password);
            menus.get(user.getRole()).show(user);
            authService.logout();
            System.out.println("Logged out.");
        } catch (ShareMealException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }
}
