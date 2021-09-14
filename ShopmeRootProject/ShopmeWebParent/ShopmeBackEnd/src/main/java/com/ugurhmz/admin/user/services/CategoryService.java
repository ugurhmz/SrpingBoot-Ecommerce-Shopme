package com.ugurhmz.admin.user.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

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
	
	
	
	/*before sorting
	// LIST ALL ROOT CATEGORIES 
	public List<Category> listAllcategories(){
		List<Category> rootCategories = categoryRepository.findRootCategories();
		
		return listHierarchicalCategories(rootCategories);
	}*/

	
	// LIST ALL ROOT CATEGORIES 
	public List<Category> listAllcategories(){
		List<Category> rootCategories = categoryRepository.findRootCategories(Sort.by("name").ascending());															
		
		return listHierarchicalCategories(rootCategories);
	}
		
		
	
	
	
	
	// HIERARCHICAL CATEGORIES
	public List<Category> listHierarchicalCategories(List<Category> rootCategories) {
		List<Category> hierarchicalCategories = new ArrayList<>();
		
		for(Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyFull(rootCategory)); // Go Category Entity then -> Create copyFull() metod
			
			//Set<Category> children = rootCategory.getChildren(); BEFORE SORTING
			
			Set<Category> children = sortSubCategories(rootCategory.getChildren());
			
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
		//Set<Category> children = parent.getChildren(); 	BEFORE SORTING
		
		Set<Category> children = sortSubCategories(parent.getChildren());
		
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
		
		//Iterable<Category> categoriesInDB = categoryRepository.findAll(); before sorting
		Iterable<Category> categoriesInDB =  categoryRepository.findRootCategories(Sort.by("name").ascending());
		
		for(Category category : categoriesInDB) {
			
			if(category.getParent() == null) {
				
				categoriesUsedInForm.add(Category.copyIdAndName(category));
				
				//Set<Category>  children = category.getChildren(); 		BEFORE SORTING
				Set<Category> children = sortSubCategories(category.getChildren());
				
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
		//Set<Category> children = parent.getChildren(); before sorted
		
		Set<Category> children =sortSubCategories(parent.getChildren());
		
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
	
	
	
	
	
	// UPDATE CATEGORY
	public Category get(Integer id) throws CategoryNotFoundException {
		
		try {
			return categoryRepository.findById(id).get();
		} catch(NoSuchElementException ex) {
			throw new CategoryNotFoundException("Colud not find category, ID : "+id);
		}
	}
	
	
	
	
	
	
	// CATEGORY UNIQUE
	public String checkUniqueCategory(Integer id, String name, String nickName) {
		boolean isCreatingNew = (id == null || id==0);
		
		Category categoryName =  categoryRepository.findByName(name);
		
		if(isCreatingNew) {
			if(categoryName != null) {
				return "DuplicateName";
				
			} else {
				Category categoryByNickName = categoryRepository.findByNickName(nickName);
				if(categoryByNickName != null) {
					return "DuplicateNickName";
				}
			}
			
		} else {
			
			if(categoryName != null && categoryName.getId() != id) {
				return "DuplicateName";
				
			}
			else {
				Category categoryByNickName = categoryRepository.findByNickName(nickName);
				if(categoryByNickName != null && categoryByNickName.getId() != id) {
					return "DuplicateNickName";
				}
				
			}		
		}
		
		
		return "OK";
	}
	
	
	
	// SORTED CATEGORY
	private SortedSet<Category> sortSubCategories(Set<Category> children){
		
		SortedSet<Category>  sortedChildren = new TreeSet<>(new Comparator<Category>() {

			@Override
			public int compare(Category cat1, Category cat2) {
				
				return cat1.getName().compareTo(cat2.getName());
			}
			
			
		});
		
		sortedChildren.addAll(children);
		
		return sortedChildren;
	}
	
	
	
	
}











