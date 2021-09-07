package com.ugurhmz.admin.user.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ugurhmz.admin.user.repositories.CategoryRepository;
import com.ugurhmz.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

	
	@MockBean
	private CategoryRepository categoryRepo;
	
	@InjectMocks
	private CategoryService categoryService;
	
	
	
	
	// check unique "DuplicateName"
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer id = null;
		String name = "Computers";
		String nickname = "qeqwe";
		
		Category category  = new Category(id,name,nickname);
		
		Mockito.when(categoryRepo.findByName(name)).thenReturn(category);
		Mockito.when(categoryRepo.findByNickName(nickname)).thenReturn(null);
		
		String result = categoryService.checkUniqueCategory(id, name, nickname);
		System.out.println(result);
		
		assertThat(result).isEqualTo("DuplicateName");
	}
	
	
	
	
	
	// check unique "NickName"
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateNickName() {
		Integer id = null;
		String name = "qqeqwe";
		String nickname = "lenses";
		
		Category category  = new Category(id,name,nickname);
		
		Mockito.when(categoryRepo.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepo.findByNickName(nickname)).thenReturn(category);
		
		String result = categoryService.checkUniqueCategory(id, name, nickname);
		
		
		assertThat(result).isEqualTo("DuplicateNickName");
	}
	
	
	
	
	
	// check unique "OK"
	@Test
	public void testCheckUniqueInNewModeReturnOK() {
		Integer id = null;
		String name = "qqeqwe";
		String nickname = "lenses";
		
		Mockito.when(categoryRepo.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepo.findByNickName(nickname)).thenReturn(null);
		
		String result = categoryService.checkUniqueCategory(id, name, nickname);
		
		
		assertThat(result).isEqualTo("OK");
	}
	
	
	
	// check unique editing category 
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName() {
		Integer id = 1;
		String name = "Computers";
		String nickname = "qweqw";
		
		Category category = new Category(2,name,nickname);
		
		Mockito.when(categoryRepo.findByName(name)).thenReturn(category);
		Mockito.when(categoryRepo.findByNickName(nickname)).thenReturn(null);
		
		String result = categoryService.checkUniqueCategory(id, name, nickname);
		
		
		assertThat(result).isEqualTo("DuplicateName");
	}

	
	
	// check unique editing category 
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateNickName() {
		Integer id = 1;
		String name = "qweqwe";
		String nickname = "lenses";
		
		Category category = new Category(2,name,nickname);
		
		Mockito.when(categoryRepo.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepo.findByNickName(nickname)).thenReturn(category);
		
		String result = categoryService.checkUniqueCategory(id, name, nickname);
		
		
		assertThat(result).isEqualTo("DuplicateNickName");
	}

	
	
	
	// check unique Edit "OK"
	@Test
	public void testCheckUniqueInEditModeReturnOK() {
		Integer id = 1;
		String name = "qqeqwe";
		String nickname = "lenses";
		
		
		Category category = new Category(id,name,nickname);
		
		Mockito.when(categoryRepo.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepo.findByNickName(nickname)).thenReturn(category);
		
		String result = categoryService.checkUniqueCategory(id, name, nickname);
		
		
		assertThat(result).isEqualTo("OK");
	}
		
	
	
	
	
}










