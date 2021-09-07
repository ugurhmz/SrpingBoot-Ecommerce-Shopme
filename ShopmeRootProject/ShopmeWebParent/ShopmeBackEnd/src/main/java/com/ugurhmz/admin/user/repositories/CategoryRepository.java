package com.ugurhmz.admin.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ugurhmz.common.entity.Category;



@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

	
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL ")
	public List<Category> findRootCategories();
	
	
	//Category name , unique
	public Category findByName(String name);
	
	
	// Category nickName, unique
	public Category findByNickName(String nickName);
}
