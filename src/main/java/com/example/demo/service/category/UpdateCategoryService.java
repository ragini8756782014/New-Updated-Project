package com.example.demo.service.category;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UpdateCategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	/**
     * to Update Category  .
     *
     *@param category - Category
     *@return response
     */
	public BaseResponse updateCategory(Category category) {
		BaseResponse baseResponse = new BaseResponse();
		Optional<Category> dbcategory = categoryRepository.findById(category.getCategory_id());
		if (dbcategory.isPresent()) {
			log.info("updated category details");
			categoryRepository.save(category);
		} else {
			log.info("something went wrong");
			baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			baseResponse.setMessage("your category id do not exist in database");
			return baseResponse;
		}
		baseResponse.setStatus(HttpStatus.ACCEPTED.value());
		baseResponse.setMessage("your room has been successfully registered");
		return baseResponse;
	}



}
