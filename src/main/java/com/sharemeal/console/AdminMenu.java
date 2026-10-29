package com.sharemeal.console;

import com.sharemeal.enums.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminMenu extends BaseMenu {

    public AdminMenu(InputUtil input) {
        super(input);
    }

    @Override
    public Role role() {
        return Role.ADMIN;
    }

    @Override
    protected String title() {
        return "ADMIN MENU";
    }

    @Override
    protected List<String> options() {
        return List.of(
                "View all users",
                "Activate / deactivate a user",
                "View all donations",
                "Generate reports",
                "View activity log");
    }
}
