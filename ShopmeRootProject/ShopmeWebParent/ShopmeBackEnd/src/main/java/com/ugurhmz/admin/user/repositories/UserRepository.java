package com.ugurhmz.admin.user.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ugurhmz.common.entity.User;



@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	public Long countById(Integer id);
}
