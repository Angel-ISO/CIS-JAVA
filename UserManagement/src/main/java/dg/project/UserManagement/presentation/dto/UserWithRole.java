package dg.project.UserManagement.presentation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.Set;

@JsonPropertyOrder({"username", "name", "email", "createdAt", "isEnabled", "accountNoExpired", "accountNoLocked", "credentialNoExpired", "roles"})
public record UserWithRole(
        String username,
        String name,
        String email,
        Date createdAt,
        boolean isEnabled,
        boolean accountNoExpired,
        boolean accountNoLocked,
        boolean credentialNoExpired,
        Set<String> roles
) {
}
