package com.ugurhmz.admin.user.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ugurhmz.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	
	public Long countById(Integer id);

	
	// FOR isEmailUnique
	@Query("SELECT u FROM  User u WHERE u.email = :email")
	public User getByUserEmail(@Param("email") String email);


	@Query("UPDATE User u SET  u.enabled = ?2 WHERE u.id=?1")
	@Modifying
	public void updateEnableStatus(Integer id, boolean enabled);
}
