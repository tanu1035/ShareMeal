package com.sharemeal.service;

import com.sharemeal.dto.RegistrationRequest;
import com.sharemeal.entity.User;
import com.sharemeal.enums.Role;
import com.sharemeal.exception.DuplicateEmailException;
import com.sharemeal.exception.InvalidInputException;
import com.sharemeal.exception.InvalidLoginException;
import com.sharemeal.repository.UserRepository;
import jakarta.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    ActivityLogService activityLogService;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final SessionContext session = new SessionContext();
    private AuthService authService;

    @BeforeEach
    void setUp() {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        authService = new AuthService(userRepository, encoder, validator, session, activityLogService);
    }

    private RegistrationRequest validRequest() {
        return new RegistrationRequest("Asha", "A@B.com", "secret1", "9876543210", "Pune", Role.DONOR);
    }

    @Test
    void registerSavesUserWithHashedPasswordAndLowercaseEmail() {
        when(userRepository.existsByEmail("a@b.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = authService.register(validRequest());

        assertEquals("a@b.com", saved.getEmail());
        assertNotEquals("secret1", saved.getPasswordHash());
        assertTrue(encoder.matches("secret1", saved.getPasswordHash()));
    }

    @Test
    void registerRejectsDuplicateEmail() {
        when(userRepository.existsByEmail("a@b.com")).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> authService.register(validRequest()));
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerRejectsInvalidInput() {
        var bad = new RegistrationRequest("", "not-an-email", "123", "abc", "", Role.NGO);
        assertThrows(InvalidInputException.class, () -> authService.register(bad));
    }

    @Test
    void registerRejectsAdminRole() {
        var admin = new RegistrationRequest("Eve", "eve@b.com", "secret1", "9876543210", "Pune", Role.ADMIN);
        assertThrows(InvalidInputException.class, () -> authService.register(admin));
    }

    @Test
    void loginSucceedsWithCorrectPassword() {
        User user = existingUser();
        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.of(user));

        authService.login("A@B.com", "secret1");

        assertTrue(session.isLoggedIn());
        assertEquals("a@b.com", session.getCurrentUser().getEmail());
    }

    @Test
    void loginFailsWithWrongPassword() {
        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.of(existingUser()));

        assertThrows(InvalidLoginException.class, () -> authService.login("a@b.com", "wrong"));
        assertFalse(session.isLoggedIn());
    }

    private User existingUser() {
        User user = new User();
        user.setName("Asha");
        user.setEmail("a@b.com");
        user.setPasswordHash(encoder.encode("secret1"));
        user.setRole(Role.DONOR);
        return user;
    }
}
