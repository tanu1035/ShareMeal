package com.sharemeal.console;

import com.sharemeal.enums.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DonorMenu extends BaseMenu {

    public DonorMenu(InputUtil input) {
        super(input);
    }

    @Override
    public Role role() {
        return Role.DONOR;
    }

    @Override
    protected String title() {
        return "DONOR MENU";
    }

    @Override
    protected List<String> options() {
        return List.of(
                "Add food donation",
                "View my donations",
                "Cancel a donation",
                "View requests on my donations",
                "Approve / reject a request",
                "Confirm pickup");
    }
}
