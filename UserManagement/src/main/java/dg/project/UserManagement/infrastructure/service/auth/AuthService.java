package dg.project.UserManagement.infrastructure.service.auth;

import dg.project.UserManagement.infrastructure.utils.JwtUtils;
import dg.project.UserManagement.presentation.dto.AuthLoginRequest;
import dg.project.UserManagement.presentation.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }


    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        try {
            Authentication authentication = authenticate(authLoginRequest.username(), authLoginRequest.password());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtils.createToken(authentication);
            return new AuthResponse(authLoginRequest.username(), "User logged successfully", accessToken, true);
        } catch (BadCredentialsException e) {
            return new AuthResponse(null, "Invalid credentials", null, false);
        } catch (Exception e) {
            return new AuthResponse(null, "An error occurred during login", null, false);
        }
    }

}
