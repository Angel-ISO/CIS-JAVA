package dg.project.UserManagement.application.seeds;

import dg.project.UserManagement.domain.repository.RoleRepository;
import dg.project.UserManagement.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedOrchestrator implements CommandLineRunner {

    private final UserSeed userSeed;
    private final RoleSeed roleSeed;


    private UserRepository userRepository;
    private RoleRepository roleRepository;


    @Autowired
    public SeedOrchestrator(UserSeed userSeed, RoleSeed roleSeed,
                            UserRepository userRepository,
                            RoleRepository roleRepository
                            ) {
        this.userSeed = userSeed;
        this.roleSeed = roleSeed;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    @Override
    public void run(String... args) throws Exception {

        roleSeed.initRoles(roleRepository).run(args);

        userSeed.initUsers(userRepository, roleRepository).run(args);

        System.out.println("All seeds initialized successfully.");
    }
}
