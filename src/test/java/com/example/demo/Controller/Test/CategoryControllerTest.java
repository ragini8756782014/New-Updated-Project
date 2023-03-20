package com.example.demo.Controller.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.CategoryController;
import com.example.demo.entity.Category;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.category.CreateCategoryService;
import com.example.demo.service.category.DeleteCategoryService;
import com.example.demo.service.category.GetAllCategoryService;
import com.example.demo.service.category.GetCategoryByIdService;
import com.example.demo.service.category.UpdateCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Mock
	private CreateCategoryService createCategoryService;
	@Mock
	private DeleteCategoryService deleteCategoryService;
	@Mock
	private GetAllCategoryService getAllCategoryService;
	@Mock
	private GetCategoryByIdService getCategoryByIdService;
	@Mock
	private UpdateCategoryService updateCategoryService;
	@InjectMocks
	private CategoryController categoryController;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	public void getAllCategoryTest() throws Exception {
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(new Category(1L, "Single Bed", 1));
		categoryList.add(new Category(2L, "Single Bed", 1));

		when(getAllCategoryService.getAllCategory()).thenReturn(categoryList);
		this.mockMvc.perform(get("/category")).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void getCategoryByIdTest() throws Exception {
		Category category = new Category(1L, "Single Bed", 1);
		when(getCategoryByIdService.getCategoryById(1L)).thenReturn(Optional.of(category));

		this.mockMvc.perform(get("/category/{id}", 1L)).andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.jsonPath(".categoryType").value("Single Bed"))
				.andExpect(MockMvcResultMatchers.jsonPath(".noOfBed").value(1)).andDo(print());
	}

	@Test
	public void createCategoryTest() throws Exception {
		Category category = new Category(1L, "Single Bed", 1);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage("your category has been successfully registered");
		baseResponse.setStatus(202);
		when(createCategoryService.addCategory(Mockito.any())).thenReturn(baseResponse);
		// Converting our Java object to JSON format because MockMvc works only with
		// JSON format
		ObjectMapper mapper = new ObjectMapper();
		String jsonbody = mapper.writeValueAsString(category);
		System.out.println(jsonbody);
		this.mockMvc.perform(post("/category").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath(".status").value(202))
				.andExpect(MockMvcResultMatchers.jsonPath(".message")
						.value("your category has been successfully registered"))
				.andDo(print());
	}

	@Test
	public void deleteRoomTest() throws Exception {
		Category category = new Category(1L, "Single Bed", 1);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage("your category has been successfully deleted");
		baseResponse.setStatus(202);
		when(deleteCategoryService.deleteCategory(Mockito.anyLong())).thenReturn(baseResponse);
		mockMvc.perform(delete("/category/{id}", category.getCategory_id())).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath(".status").value(202))
				.andExpect(
						MockMvcResultMatchers.jsonPath(".message").value("your category has been successfully deleted"))
				.andDo(print());
	}

}
