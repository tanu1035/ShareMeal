package com.sharemeal.service;

import com.sharemeal.entity.User;
import org.springframework.stereotype.Component;

/** Holds the currently logged-in user (a console app has exactly one session). */
@Component
public class SessionContext {

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void clear() {
        this.currentUser = null;
    }
}
