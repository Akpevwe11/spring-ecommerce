package com.codewithmosh.springecommerce.service;

import com.codewithmosh.springecommerce.dto.AuthenticationResponse;
import com.codewithmosh.springecommerce.dto.RegisterRequest;
import com.codewithmosh.springecommerce.dto.UserResponse;
import com.codewithmosh.springecommerce.entity.Role;
import com.codewithmosh.springecommerce.entity.User;
import com.codewithmosh.springecommerce.exception.EmailAlreadyExistsException;
import com.codewithmosh.springecommerce.repository.UserRepository;
import com.codewithmosh.springecommerce.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("An account with this email already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return AuthenticationResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .user(toUserResponse(savedUser))
                .build();
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
