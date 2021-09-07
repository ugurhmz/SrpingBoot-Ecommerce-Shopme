package com.ugurhmz.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ugurhmz.admin.user.services.CategoryService;




@RestController
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;
	
	
	
	@PostMapping("/categories/check_unique")
	public String postCheckUnique(@Param("id") Integer id, @Param("name") String name, @Param("nickName") String nickName) {
		
		return categoryService.checkUniqueCategory(id, name, nickName);
	}
	
}
