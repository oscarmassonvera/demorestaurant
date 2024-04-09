package com.rabbit.demorest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rabbit.demorest.entities.Rol;
import com.rabbit.demorest.entities.Users;

@Repository
public interface IUsersRepo extends CrudRepository<Users,Long> {
    Users findByUsername(String username);
    List<Users> findByRol(Rol rol);
}
