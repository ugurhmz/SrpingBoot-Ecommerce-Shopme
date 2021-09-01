package com.ugurhmz.admin.user.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ugurhmz.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	// Root category test (1)
	@Test
	public void testCreateROOTcategory() {
		Category category = new Category("Electronics");
		Category savedCategory = categoryRepository.save(category);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	
	}	
	
	
	
	// Sub category test (2)
	@Test
	public void  testCreateSubCategory() {
		Category parent = new Category(1);	// id : 1 -> Computers
		Category subCategory = new Category("Laptops",parent);
		
		Category savedCategory = categoryRepository.save(subCategory);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	
	// Multiple Sub category add test (3)
	@Test
	public void testCreateMultipleSubCategory() {
		Category parent = new Category(1);
		
		
		//Toplu kategory ekleme 
		Category pc = new Category("LCD",parent);
		Category ram = new Category("Mouse",parent);
		
		// JAVA 9 same operations -> categoryRepository.saveAll(List.of(pc,ram));
		List<Category> pcLists = Arrays.asList(pc,ram);
		pcLists = Collections.unmodifiableList(pcLists);
		
		for(Category el : pcLists) {
			categoryRepository.save(el);
		}
		
		//categoryRepository.save(pc);
		//categoryRepository.save(ram);
	}
	
	
	// Root category Electorics ->    add subcategory to parent. (4)
	@Test
	public void testCreateMultipleOtherSubCategory() {	
		Category parent = new Category(2);
		
		
		Category cameras = new Category("Cameras",parent);
		Category smartPhones = new Category("Smart Phones",parent);
		Category printer = new Category("Printer",parent);
		
		
		List<Category> electronicLists = Arrays.asList(cameras,smartPhones,printer);
		electronicLists = Collections.unmodifiableList(electronicLists);
		
		
		for(Category el : electronicLists) {
			categoryRepository.save(el);
		}
		
	}
		
	
	
	//adding multiple sub category with  [JAVA 9] (5)
	@Test
	public void testCreateMultipleByJava9SubCategory() {
		Category parent = new Category(2);
		
		Category tvs = new Category("TVS",parent);
		Category radios= new Category("Radios",parent);
	
		
		categoryRepository.saveAll(List.of(tvs,radios));
	
		
	}	
	
	// Children category added			(6)
	@Test
	public void testSubCategory() {
		Category parent = new Category(12);
		
		Category canon = new Category("CANON EOS M50 ",parent);
		
		Category savedCategory = categoryRepository.save(canon);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	
	
	
	// Get  Root Category -> children( Bunu yaparken, default constructor olması gerektiğini unutma!!)  (7)
	@Test
	public void testGetCategory() {
		Category category = categoryRepository.findById(1).get();
		System.out.println("category name : "+category.getName());
		
		Set<Category>  children = category.getChildren();
		
		for(Category subCategory : children) {
			System.out.println(subCategory.getName()+" ");
		}
		
		assertThat(children.size()).isGreaterThan(0);
		
	}
	
	// Print HierarchicalCategories		(8)
	@Test
	public void testPrintHierarchicalCategories() {
		Iterable<Category> categories = categoryRepository.findAll();
		
		for(Category category : categories) {
			if(category.getParent() == null) {
				
				System.out.println(category.getName());
				Set<Category> children = category.getChildren();
				
				for(Category subCategory : children) {
					System.out.println("--" + subCategory.getName());
				}
				
				
			}
		}
	}
	
	
	
}














