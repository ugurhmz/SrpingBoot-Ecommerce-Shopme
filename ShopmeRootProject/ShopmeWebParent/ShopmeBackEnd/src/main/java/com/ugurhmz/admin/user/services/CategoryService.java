package com.ugurhmz.admin.user.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ugurhmz.admin.user.repositories.CategoryRepository;
import com.ugurhmz.common.entity.Category;



@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	/*
	// LIST ALL CATEGORIES / BEFORE hiararchical
	public List<Category> listAllcategories(){
		return (List<Category>) categoryRepository.findAll();
	}*/
	
	
	
	
	// LIST ALL ROOT CATEGORIES 
	public List<Category> listAllcategories(){
		List<Category> rootCategories = categoryRepository.findRootCategories();
		
		return listHierarchicalCategories(rootCategories);
	}
	
	
	
	
	// HIERARCHICAL CATEGORIES
	public List<Category> listHierarchicalCategories(List<Category> rootCategories) {
		List<Category> hierarchicalCategories = new ArrayList<>();
		
		for(Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyFull(rootCategory)); // Go Category Entity then -> Create copyFull() metod
			
			Set<Category> children = rootCategory.getChildren();
			
			for(Category subCategory : children) {
				String name = "--" + subCategory.getName();
				hierarchicalCategories.add(Category.copyFull(subCategory, name));
				
				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
			}
		
		
		}
		
		return hierarchicalCategories;
	}
	
	
	
	// RECURSIVE list sub hieararchical categories
	private void listSubHierarchicalCategories(
			List<Category> hierarchicalCategories,Category parent, int subLevel) 
	{
		Set<Category> children = parent.getChildren();
		int newSubLevel = subLevel + 1;
		
		
		for(Category subCategory : children) {
			String name = "";
			
			for(int i=0 ; i < newSubLevel ; i++) {
				name += "--";
			}
			
			name += " "+subCategory.getName();
			hierarchicalCategories.add(Category.copyFull(subCategory, name));
			
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
		}
	}
	
	
	
	
	// LIST ALL CATEGORIES IN category_form.html
	public List<Category> listCategoriesUsedInForm() {
		
		List<Category> categoriesUsedInForm  = new ArrayList<>();
		Iterable<Category> categoriesInDB = categoryRepository.findAll();
		
		for(Category category : categoriesInDB) {
			
			if(category.getParent() == null) {
				
				categoriesUsedInForm.add(Category.copyIdAndName(category));
				Set<Category>  children = category.getChildren();
				
				for(Category subCategory : children) {
					
					String name = "--" + subCategory.getName();
					categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(),name));
					
					listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
				}
			}
		}
		
		return  categoriesUsedInForm;
	}
	
	
	// listSubCategoriesUsedInForm - (Recursive for Category name)
	private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
		
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for(Category subCategory : children) {
			String name="";
			
			for(int i=0 ; i < newSubLevel ; i++) {
				name += "--";
			}
			
			name += "--"+subCategory.getName();
			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(),name));
			
			listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
		}	
	}
	
	
	
	// SAVE CATEGORY
	public Category saveNewCategory(Category category) {
		return categoryRepository.save(category);
	}
	
	
	
	
	
}











