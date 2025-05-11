package dg.project.UserManagement.application.seeds;

import dg.project.UserManagement.domain.entities.Role;
import dg.project.UserManagement.domain.repository.RoleRepository;
import dg.project.UserManagement.infrastructure.utils.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeed {

    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() > 0) {
                System.out.println("Roles already initialized. Skipping seed.");
                return;
            }

            Role roleAdmin = Role.builder().name(RoleEnum.ADMIN).build();
            Role roleUser = Role.builder().name(RoleEnum.USER).build();


            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);


            System.out.println("Roles initialized successfully.");
        };
    }
}

