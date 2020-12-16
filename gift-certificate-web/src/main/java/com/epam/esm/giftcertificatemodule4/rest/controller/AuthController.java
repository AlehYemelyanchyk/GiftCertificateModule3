package com.epam.esm.giftcertificatemodule4.rest.controller;

import com.epam.esm.giftcertificatemodule4.services.RoleService;
import com.epam.esm.giftcertificatemodule4.entity.Role;
import com.epam.esm.giftcertificatemodule4.entity.User;
import com.epam.esm.giftcertificatemodule4.payload.request.LoginRequest;
import com.epam.esm.giftcertificatemodule4.payload.request.SignupRequest;
import com.epam.esm.giftcertificatemodule4.payload.response.JwtResponse;
import com.epam.esm.giftcertificatemodule4.payload.response.MessageResponse;
import com.epam.esm.giftcertificatemodule4.security.jwt.JwtUtils;
import com.epam.esm.giftcertificatemodule4.security.services.UserDetailsImpl;
import com.epam.esm.giftcertificatemodule4.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger();

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          RoleService roleService,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                castAuthoritiesToRoles(userDetails.getAuthorities())));
    }

    private List<String> castAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        try {
            if (userService.existsByName(signUpRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }
        } catch (Exception e) {
            LOGGER.info("The name doesn't exist. Can be registered.");
        }

        try {
            if (userService.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }
        } catch (Exception e) {
            LOGGER.info("The email doesn't exist. Can be registered.");
        }

        User user = new User();
        user.setName(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.size() == 0) {
            Role userRole = roleService.findByName("ROLE_GUEST");
            if (userRole == null) {
                throw new RuntimeException("Error: Role is not found.");
            }
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("user".equals(role)) {
                    Role modRole = this.roleService.findByName("ROLE_USER");
                    if (modRole == null) {
                        throw new RuntimeException("Error: Role is not found.");
                    }
                    roles.add(modRole);
                } else {
                    Role guestRole = this.roleService.findByName("ROLE_GUEST");
                    if (guestRole == null) {
                        throw new RuntimeException("Error: Role is not found.");
                    }
                    roles.add(guestRole);
                }
            });
        }
        user.setRoles(roles);
        userService.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
