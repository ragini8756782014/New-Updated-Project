package com.example.demo.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreateCategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	/**
     * to add new Category  .
     *
     *@param category - Category
     *@return response
     */
	public BaseResponse addCategory(Category category) {
		validate(category);
		log.info("category is saved");
		categoryRepository.save(category);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatus(HttpStatus.ACCEPTED.value());
		baseResponse.setMessage("your category has been successfully created");
		return baseResponse;
	}

	private void validate(Category category) {
		if (category.getCategory_type() == null || category.getCategory_type().isBlank()
				|| category.getCategory_type().isEmpty()) {
			throw new ValidationException("FV001", " category type cannot be null or empty");
		} else if (category.getNo_of_bed() <= 0) {
			throw new ValidationException("FV002", " no of brds cannot be 0 or empty");
		} else {

		}
	}

}
