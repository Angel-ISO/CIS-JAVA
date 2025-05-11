package dg.project.UserManagement.infrastructure.service;

import dg.project.UserManagement.domain.entities.Role;
import dg.project.UserManagement.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleService extends GenericService<Role, UUID> {

    @Autowired
    public RoleService(RoleRepository repository ) {
        super(repository);
    }

}