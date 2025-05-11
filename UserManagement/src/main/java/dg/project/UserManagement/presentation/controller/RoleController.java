package dg.project.UserManagement.presentation.controller;

import dg.project.UserManagement.domain.entities.Role;
import dg.project.UserManagement.infrastructure.helpers.PaginationParams;
import dg.project.UserManagement.infrastructure.service.RoleService;
import dg.project.UserManagement.presentation.dto.Role.SingleRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController implements BaseApiController<Role, UUID> {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
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

        Page<Role> rolesPage = roleService.getAll(params);

        Page<SingleRole> dtosPage = rolesPage.map(role -> new SingleRole(role.getName().toString()));

        return ResponseEntity.ok(dtosPage);
    }


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id) {
        Optional<Role> roleOptional = roleService.getById(id);

        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            SingleRole dto = new SingleRole(role.getName().toString());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    @Override
    public ResponseEntity<?> save(Role entity) {
        roleService.update(entity);
        return ResponseEntity.ok("Role Created Sucessfully.");
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> update(@PathVariable UUID id, Role entity) {
        return ResponseEntity.status(405).body("This method not be created yet, roles are not editable.");
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try{
            roleService.delete(id);
            return ResponseEntity.ok("Role deleted successfully.");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting role: " + e.getMessage());
        }
    }

}
