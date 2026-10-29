package com.sharemeal.service;

import com.sharemeal.dto.RegistrationRequest;
import com.sharemeal.entity.User;
import com.sharemeal.enums.Role;
import com.sharemeal.exception.DuplicateEmailException;
import com.sharemeal.exception.InvalidInputException;
import com.sharemeal.exception.InvalidLoginException;
import com.sharemeal.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;
    private final SessionContext session;
    private final ActivityLogService activityLog;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       Validator validator,
                       SessionContext session,
                       ActivityLogService activityLog) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.session = session;
        this.activityLog = activityLog;
    }

    @Transactional
    public User register(RegistrationRequest request) {
        Set<ConstraintViolation<RegistrationRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .sorted()
                    .collect(Collectors.joining("; "));
            throw new InvalidInputException(message);
        }
        if (request.role() == null || request.role() == Role.ADMIN) {
            throw new InvalidInputException("Role must be DONOR or NGO.");
        }

        String email = request.email().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }

        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setPhone(request.phone());
        user.setAddress(request.address().trim());

        User saved = userRepository.save(user);
        activityLog.log(saved, "REGISTERED as " + saved.getRole());
        return saved;
    }

    @Transactional
    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(InvalidLoginException::new);

        if (!user.isActive()) {
            throw new InvalidLoginException("This account has been deactivated.");
        }
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new InvalidLoginException();
        }

        session.setCurrentUser(user);
        activityLog.log(user, "LOGGED IN");
        return user;
    }

    public void logout() {
        if (session.isLoggedIn()) {
            activityLog.log(session.getCurrentUser(), "LOGGED OUT");
        }
        session.clear();
    }
}
