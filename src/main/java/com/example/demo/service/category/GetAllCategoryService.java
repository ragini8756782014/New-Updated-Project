package com.example.demo.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetAllCategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	/**
     * to get all Category  .
     *
     *@return List of all Category
     */
	public List<Category> getAllCategory() {
		log.info("getting all categories");
		return categoryRepository.findAll();
	}

}
