package com.sharemeal.console;

import com.sharemeal.entity.User;
import com.sharemeal.enums.Role;

/** Strategy interface: one implementation per role. Spring injects all of them as a List. */
public interface RoleMenu {

    Role role();

    void show(User user);
}
