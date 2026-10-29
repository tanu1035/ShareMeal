package com.sharemeal.console;

import com.sharemeal.enums.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NgoMenu extends BaseMenu {

    public NgoMenu(InputUtil input) {
        super(input);
    }

    @Override
    public Role role() {
        return Role.NGO;
    }

    @Override
    protected String title() {
        return "NGO MENU";
    }

    @Override
    protected List<String> options() {
        return List.of(
                "Browse available food",
                "Request food",
                "Track my requests",
                "Confirm pickup received");
    }
}
