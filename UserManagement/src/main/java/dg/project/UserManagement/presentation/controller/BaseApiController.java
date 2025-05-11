package dg.project.UserManagement.presentation.controller;

import dg.project.UserManagement.domain.entities.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@CrossOrigin(origins = "*")
public interface BaseApiController<T extends BaseEntity, ID extends Serializable> {


    @GetMapping
    ResponseEntity<Page<?>> getAll(@RequestParam(required = false) String search,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable ID id);

    @PostMapping
    ResponseEntity<?> save(@RequestBody T entity);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable ID id, @RequestBody T entity);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable ID id);


}
