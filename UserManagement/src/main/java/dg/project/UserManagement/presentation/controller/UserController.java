package dg.project.UserManagement.presentation.controller;

import dg.project.UserManagement.domain.entities.User;
import dg.project.UserManagement.infrastructure.helpers.PaginationParams;
import dg.project.UserManagement.infrastructure.service.UserService;
import dg.project.UserManagement.presentation.dto.UserWithRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController implements BaseApiController<User, UUID> {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<?>> getAll(@RequestParam(required = false) String search,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int size) {

        PaginationParams params = new PaginationParams();
        params.setSearch(search);
        params.setPageIndex(page);
        params.setPageSize(size);

        Page<User> usersPage = userService.getAll(params);

        Page<UserWithRole> dtosPage = usersPage.map(user -> new UserWithRole(
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.isEnabled(),
                user.isAccountNoExpired(),
                user.isAccountNoLocked(),
                user.isCredentialNoExpired(),
                user.getRoles().stream()
                        .map(role -> role.getName().toString())
                        .collect(Collectors.toSet())
        ));

        return ResponseEntity.ok(dtosPage);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id) {
        Optional<User> userOptional = userService.getById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserWithRole dto = new UserWithRole(
                    user.getUsername(),
                    user.getName(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.isEnabled(),
                    user.isAccountNoExpired(),
                    user.isAccountNoLocked(),
                    user.isCredentialNoExpired(),
                    user.getRoles().stream()
                            .map(role -> role.getName().toString())
                            .collect(Collectors.toSet())
            );
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok("User Created Sucessfully.");
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody User user) {
        return ResponseEntity.status(405).body("This method is not found.");
    }


    @Override
    @DeleteMapping("/{id}")
    @Deprecated
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return ResponseEntity.status(405).body("This method is not found.");
    }

    @DeleteMapping("delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id, Authentication authentication) {
        try {
            userService.deleteUser(id, authentication);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body("You don't have permission to delete this user: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid user id: " + id);
        }
    }


    @PostMapping("/add-role")
    public ResponseEntity<?> addRoleToUser(@RequestParam UUID id, @RequestParam String roleName) {
        try {
            userService.addRoleToUser(id, roleName);
            return ResponseEntity.ok("Role " + roleName + " added successfully to the user.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role id: " + id);
        }
    }

    @DeleteMapping("/remove-role")
    public ResponseEntity<?> removeRoleFromUser(@RequestParam UUID id, @RequestParam String roleName) {
        try {
            userService.removeRoleFromUser(id, roleName);
            return ResponseEntity.ok("Role " + roleName + " removed successfully from the user.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role id: " + id);
        }
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<?> updateUserDetails(@PathVariable UUID id, @RequestBody User updatedUser, Authentication authentication) {
        try {
            User updated = userService.updateUserDetails(id, updatedUser, authentication);
            return ResponseEntity.ok("User details updated successfully.");
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body("You don't have permission to update this user: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("User not found or invalid data: " + e.getMessage());
        }
    }
}
