package dg.project.UserManagement.domain.repository;

import dg.project.UserManagement.domain.entities.Role;
import dg.project.UserManagement.infrastructure.utils.RoleEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends BaseRepository<Role, UUID> {

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Optional<Role> findByName(RoleEnum name);

}
