package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.category.CreateCategoryService;
import com.example.demo.service.category.DeleteCategoryService;
import com.example.demo.service.category.GetAllCategoryService;
import com.example.demo.service.category.GetCategoryByIdService;
import com.example.demo.service.category.UpdateCategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CategoryController {

	@Autowired
	private CreateCategoryService createCategoryService;
	@Autowired
	private DeleteCategoryService deleteCategoryService;
	@Autowired
	private GetAllCategoryService getAllCategoryService;
	@Autowired
	private GetCategoryByIdService getCategoryByIdService;
	@Autowired
	private UpdateCategoryService updateCategoryService;	
	
	/**
     * To Add a new Category.
     *
     * @param category - Category
     * @return ResponseEntity
     */
	@PostMapping("/category")
	public ResponseEntity<BaseResponse> createCategory(@RequestBody Category category) {
		log.info("saving category in database ");
		return new ResponseEntity<>(createCategoryService.addCategory(category), HttpStatus.CREATED);
	}
    
	/**
     * To get List of all Category .
     *
     * @return ResponseEntity
     */
	@GetMapping("/category")
	public ResponseEntity<List<Category>> getAllCategory() {
		log.info("getting all category type data  ");
		return new ResponseEntity<>(getAllCategoryService.getAllCategory(), HttpStatus.OK);
	}
    
	/**
     * To get Details of Category with category ID.
     *
     * @param id - Category ID
     * @return ResponseEntity
     */
	@GetMapping("/category/{id}")
	public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable("id") Long id) {
		log.info("getting category using category id ");
		return new ResponseEntity<>(getCategoryByIdService.getCategoryById(id), HttpStatus.FOUND);
	}

	/**
     * to Delete a Category.
     *
     * @param id -  Category Id
     * @return ResponseEntity
     */
	@DeleteMapping("/category/{id}")
	public ResponseEntity<BaseResponse> deleteCategory(@PathVariable("id") Long id) {
		log.info("deleting category using id");
		return new ResponseEntity<>(deleteCategoryService.deleteCategory(id), HttpStatus.OK);
	}
    
	
	/**
     * to Update Category.
     *
     * @param category   - Category
     * @return ResponseEntity
     */
	@PutMapping("/category")
	public ResponseEntity<BaseResponse> updateCategory(@RequestBody Category category) {
		log.info("updating all data of category ");
		return new ResponseEntity<>(updateCategoryService.updateCategory(category), HttpStatus.OK);
	}

}
