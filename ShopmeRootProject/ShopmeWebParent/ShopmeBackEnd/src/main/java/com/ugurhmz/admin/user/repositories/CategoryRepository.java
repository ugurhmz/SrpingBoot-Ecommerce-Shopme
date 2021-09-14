package com.ugurhmz.admin.user.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ugurhmz.common.entity.Category;



@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

	
	/*before sort
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL ")
	public List<Category> findRootCategories();
	*/
	
	// Sorting Category
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories(Sort sort);
	
	
	
	// Category status enable
	@Query("UPDATE Category c SET  c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateEnableStatus(Integer id, boolean enabled);
	
	
	
	
	//Category name , unique
	public Category findByName(String name);
	
	
	// Category nickName, unique
	public Category findByNickName(String nickName);
}
