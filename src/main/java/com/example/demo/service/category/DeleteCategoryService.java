package com.example.demo.service.category;

import java.util.Optional;

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
public class DeleteCategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	/**
     * to Delete a Category  .
     *
     *@param id - Category's Id
     *@return response
     */
	public BaseResponse deleteCategory(Long id) {
		validate(id);
		BaseResponse baseResponse = new BaseResponse();
		Optional<Category> dbCategory = categoryRepository.findById(id);
		if (dbCategory.isPresent()) {
			log.info("category is deleted");
			categoryRepository.deleteById(id);
			baseResponse.setStatus(HttpStatus.ACCEPTED.value());
			baseResponse.setMessage("your category has been successfully deleted");
			return baseResponse;
		}
		log.info("spmething went wrong");
		baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		baseResponse.setMessage("your id is incorrect");
		return baseResponse;
	}

	private void validate(Long id) {
		if (id <= 0 || id != (Long) id) {
			throw new ValidationException("FV001", "category id cannot be 0 or empty");
		}
	}
}
