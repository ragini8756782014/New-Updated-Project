package com.example.demo.service.category;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetCategoryByIdService {

	@Autowired
	private CategoryRepository categoryRepository;

	/**
     * to get Category by Id  .
     *
     *@param id - Category's Id
     *@return Category
     */
	public Optional<Category> getCategoryById(Long id) {
		validate(id);
		Optional<Category> dbCategory = categoryRepository.findById(id);
		if (dbCategory.isPresent()) {
			log.info("getting all catgory using category id");
			return categoryRepository.findById(id);
		} else {
			log.info("something went wrong");
			throw new ValidationException("FV001", "id do not exist or incorrect");
		}
	}

	private void validate(Long id) {
		if (id <= 0 || id != (Long) id) {
			throw new ValidationException("FV001", "category id cannot be 0 or empty");
		}
	}
}
