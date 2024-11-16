package com.nutridata.meal_plan;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MealPlanController.class)
class MealPlanControllerTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private MealPlanService mealPlanService;
	private static final String rootUri = "/api/meal-plan";

	@Test
	void getRootUri_ShouldReturnOkStatus() throws Exception {
		when(this.mealPlanService.getMealPlans()).thenReturn(List.of(new MealPlan()));
		this.mockMvc.perform(get(rootUri)).andExpect(status().isOk());
	}

	@Test
	void getRootUri_WithValidIdShould_ReturnOkStatus() throws Exception {
		when(this.mealPlanService.getMealPlan(1L)).thenReturn(new MealPlan());
		this.mockMvc.perform(get(rootUri + "/1")).andExpect(status().isOk());
	}

	@Test
	void getRootUri_WithInvalidId_ShouldReturnNotFoundStatus() throws Exception {
		when(this.mealPlanService.getMealPlan(1L))
				.thenThrow(new MealPlanNotFoundException("Meal plan with id 1 not found"));
		this.mockMvc.perform(get(rootUri + "/1")).andExpect(status().isNotFound());
	}

	@Test
	void postRootUri_WithValidMealPlan_ShouldReturnCreatedStatus() throws Exception {
		String mealPlanJson = "{\"name\":\"Meal Plan 1\"}";
		when(this.mealPlanService.addNewMealPlan(new MealPlan())).thenReturn(new MealPlan());

		this.mockMvc.perform(post(rootUri).contentType(MediaType.APPLICATION_JSON)
				.content(mealPlanJson)).andExpect(status().isCreated());
	}

	@Test
	void postRootUri_WithInvalidMealPlan_ShouldReturnBadRequestStatus() throws Exception {
		String mealPlanJson = "{\"name\":\"\"}";

		this.mockMvc.perform(post(rootUri).contentType(MediaType.APPLICATION_JSON)
				.content(mealPlanJson)).andExpect(status().isBadRequest());
	}

	@Test
	void putRootUri_WithValidIdAndMealPlan_ShouldReturnOkStatus() throws Exception {
		String mealPlanJson = "{\"name\":\"Meal Plan 1\"}";

		this.mockMvc.perform(put(rootUri + "/1").contentType(MediaType.APPLICATION_JSON)
				.content(mealPlanJson)).andExpect(status().isOk());
	}

}
