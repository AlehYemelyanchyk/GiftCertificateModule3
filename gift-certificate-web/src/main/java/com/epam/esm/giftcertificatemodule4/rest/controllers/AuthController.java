package com.epam.esm.giftcertificatemodule4.rest.controllers;

import com.epam.esm.giftcertificatemodule4.entity.Role;
import com.epam.esm.giftcertificatemodule4.entity.User;
import com.epam.esm.giftcertificatemodule4.payload.request.SignupRequest;
import com.epam.esm.giftcertificatemodule4.services.RoleService;
import com.epam.esm.giftcertificatemodule4.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public AuthController(UserService userService, RoleService roleService, PasswordEncoder encoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @PostMapping("/signin")
    public void signin() {
        // The endpoint is only used to call out the Spring Security FilterChain.
        // At this stage the logic is fully completed with Spring Security
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        Set<String> roles = signUpRequest.getRole();

        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));

        Set<Role> rolesSet = new HashSet<>();

        if (roles == null || roles.size() == 0) {
            Role userRole = roleService.findByName("ROLE_GUEST");
            if (userRole == null) {
                throw new IllegalArgumentException("Role ROLE_GUEST is not found");
            }
            rolesSet.add(userRole);
        } else {
            roles.forEach(role -> {
                if ("user".equals(role)) {
                    Role modRole = this.roleService.findByName("ROLE_USER");
                    if (modRole == null) {
                        throw new IllegalArgumentException("Role ROLE_USER is not found");
                    }
                    rolesSet.add(modRole);
                } else {
                    Role guestRole = this.roleService.findByName("ROLE_GUEST");
                    if (guestRole == null) {
                        throw new IllegalArgumentException("Role ROLE_GUEST is not found");
                    }
                    rolesSet.add(guestRole);
                }
            });
        }
        user.setRoles(rolesSet);
        userService.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
