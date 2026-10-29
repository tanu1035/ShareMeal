package com.sharemeal.console;

import com.sharemeal.entity.User;

import java.util.List;

/** Template Method: common menu loop; subclasses supply the title, options and handlers. */
public abstract class BaseMenu implements RoleMenu {

    protected final InputUtil input;

    protected BaseMenu(InputUtil input) {
        this.input = input;
    }

    protected abstract String title();

    protected abstract List<String> options();

    /** Override per option as modules are built. Default: not implemented yet. */
    protected void handle(int choice, User user) {
        System.out.println("'" + options().get(choice - 1) + "' will be added in an upcoming module.");
    }

    @Override
    public void show(User user) {
        while (true) {
            System.out.println();
            System.out.println("===== " + title() + " | " + user.getName() + " =====");
            List<String> options = options();
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, options.get(i));
            }
            System.out.println("0. Logout");

            int choice = input.readChoice("Choose: ", 0, options.size());
            if (choice == 0) {
                return;
            }
            handle(choice, user);
        }
    }
}
