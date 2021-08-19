package com.ugurhmz.admin.user.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ugurhmz.common.entity.Role;



@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
