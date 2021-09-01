package com.ugurhmz.admin.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ugurhmz.admin.user.repositories.CategoryRepository;
import com.ugurhmz.common.entity.Category;



@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	// LIST ALL CATEGORIES
	public List<Category> listAllcategories(){
		return (List<Category>) categoryRepository.findAll();
	}
	
	
	
}
