package dg.project.UserManagement.infrastructure.interfaces;

import dg.project.UserManagement.domain.entities.BaseEntity;
import dg.project.UserManagement.infrastructure.helpers.PaginationParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IGenericService<T extends BaseEntity, ID extends Serializable> {


    Page<T> getAll(PaginationParams params);

    public Optional<T> getById(ID id);

    public void create(T entity);

    public void update(T entity);

    public void delete(ID id);

}
