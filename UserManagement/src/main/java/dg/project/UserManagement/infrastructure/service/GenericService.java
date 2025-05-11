package dg.project.UserManagement.infrastructure.service;

import dg.project.UserManagement.domain.entities.BaseEntity;
import dg.project.UserManagement.domain.repository.BaseRepository;
import dg.project.UserManagement.infrastructure.helpers.PaginationParams;
import dg.project.UserManagement.infrastructure.interfaces.IGenericService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public abstract class GenericService<T extends BaseEntity, ID extends Serializable> implements IGenericService<T, ID> {

    protected final BaseRepository<T, ID> repository;


    public GenericService(BaseRepository<T, ID> repository) {
        this.repository = repository;

    }

    @Override
    public Page<T> getAll(PaginationParams params) {
        Pageable pageable = PageRequest.of(params.getPageIndex() - 1, params.getPageSize());

        if (params.getSearch() != null && !params.getSearch().isEmpty()) {
            return repository.findByNameContainingIgnoreCase(params.getSearch(), pageable);
        } else {
            return repository.findAll(pageable);
        }
    }



    @Override
    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    @Override
    public void create(T entity) {
        repository.save(entity);
    }

    @Override
    public void update(T entity) {
        repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}
