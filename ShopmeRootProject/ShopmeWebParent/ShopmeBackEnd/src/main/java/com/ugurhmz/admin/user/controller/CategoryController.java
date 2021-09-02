package com.ugurhmz.admin.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ugurhmz.admin.user.services.CategoryService;
import com.ugurhmz.common.entity.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	
	
	@GetMapping("/categories")
	public String getCategoriesPage(Model model) {
		
		List<Category> listCategories = categoryService.listAllcategories();
		
		model.addAttribute("listCategories",listCategories);
		
		
		return "categories/categories";
	}
	
	@GetMapping("/categories/new")
	public String getCategoriesNewForm(Model model) {
			List<Category>	 listCategories = categoryService.listCategoriesUsedInForm();
		
		model.addAttribute("category", new Category());
		model.addAttribute("listCategories",listCategories);
		model.addAttribute("pageTitle","New Category Form");
		
		
		
		return "categories/category_form";
	}
	
	
	
	
	
	
	
	
	
}






