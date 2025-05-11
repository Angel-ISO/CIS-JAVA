package dg.project.UserManagement.presentation.dto.Role;

import jakarta.validation.constraints.NotBlank;

public record SingleRole(
        @NotBlank String name
) {}
