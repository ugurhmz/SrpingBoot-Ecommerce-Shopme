package com.ugurhmz.admin.user.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ugurhmz.admin.FileUploadUtil;
import com.ugurhmz.admin.user.services.CategoryNotFoundException;
import com.ugurhmz.admin.user.services.CategoryService;
import com.ugurhmz.common.entity.Category;



@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	
	
	// GET LIST CATEGORIES
	@GetMapping("/categories")
	public String getCategoriesPage(@Param("sortDir") String sortDir, Model model) {
		
		//List<Category> listCategories = categoryService.listAllcategories();	 Before sorting
		
		if(sortDir == null || sortDir.isEmpty()) {
			sortDir = "asc";
		}
		
		List<Category> listCategories = categoryService.listAllcategories(sortDir);													
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("listCategories",listCategories);
		model.addAttribute("reverseSortDir",reverseSortDir);	
		
		return "categories/categories";
	}
	
	
	
	// GET NEW CATEGORIES_FORM
	@GetMapping("/categories/new")
	public String getCategoriesNewForm(Model model) {
			List<Category>	 listCategories = categoryService.listCategoriesUsedInForm();
		
		model.addAttribute("category", new Category());
		model.addAttribute("listCategories",listCategories);
		model.addAttribute("pageTitle","New Category Form");
		
		
		
		return "categories/category_form";
	}
	
	
	/*  BEFORE EDITING
	// POST NEW CATEGORIES
	@PostMapping("/categories/save")
	public String saveCategory(
			Category category, 
			Model model , 
			RedirectAttributes redirectAttribute,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException 
	{
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		category.setImage(fileName);
		
		
		Category savedCategory = categoryService.saveNewCategory(category);
		String uploadDir = "../category-images/" + savedCategory.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
		redirectAttribute.addFlashAttribute("message",category.getName()+" has been saved.");
		return "redirect:/categories";
	}
	*/
	
	
	// AFTER EDITING  -> CATEGORY  POST Operations
	@PostMapping("/categories/save")
	public String saveCategory(
			Category category, 
			Model model , 
			RedirectAttributes redirectAttribute,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException 
	{
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setImage(fileName);
			
			
			Category savedCategory = categoryService.saveNewCategory(category);
			String uploadDir = "../category-images/" + savedCategory.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
			
		} else {
			categoryService.saveNewCategory(category);
		}
		
		redirectAttribute.addFlashAttribute("message",category.getName()+" has been saved.");
		return "redirect:/categories";
	}
	
	
	
	
	
	// GET UPDATE CATEGORIES 
	@GetMapping("/categories/update/{id}")
	public String getCategoryUpdate(
			@PathVariable("id") Integer id ,Model model,
			RedirectAttributes redirectAttribute) 
	{
		try {
			Category category= categoryService.get(id);
			List<Category> listCategories = categoryService.listCategoriesUsedInForm();
			
			
			model.addAttribute("category",category);
			model.addAttribute("listCategories",listCategories);
			model.addAttribute("pageTitle","Update Category ID: "+id);
			
			return "categories/category_form";
			
		} catch (CategoryNotFoundException e) {
			
			redirectAttribute.addFlashAttribute("message", e.getMessage());
			return "redirect:/categories";
		}
		
	}
	
	
}






