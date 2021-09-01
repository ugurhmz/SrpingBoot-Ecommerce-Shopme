package com.ugurhmz.admin.user.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ugurhmz.common.entity.Category;



@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
