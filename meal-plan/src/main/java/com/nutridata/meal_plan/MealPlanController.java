package com.nutridata.meal_plan;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal-plan")
public class MealPlanController {
    private final MealPlanService mealPlanService;

    public MealPlanController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
    }

    @GetMapping
    public List<MealPlan> getMealPlans() {
        return this.mealPlanService.getMealPlans();
    }

    @GetMapping("/{id}")
    public MealPlan getMealPlan(@PathVariable Long id) {
        return this.mealPlanService.getMealPlan(id);
    }

    @ExceptionHandler(MealPlanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMealPlanNotFound(MealPlanNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addNewMealPlan(@Valid @RequestBody MealPlan mealPlan) {
        this.mealPlanService.addNewMealPlan(mealPlan);
    }

    @PutMapping("/{id}")
    public MealPlan updateMealPlan(@PathVariable Long id, @Valid @RequestBody MealPlan mealPlan) {
        return this.mealPlanService.updateMealPlan(id, mealPlan);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteMealPlan(@PathVariable Long id) {
        this.mealPlanService.deleteMealPlan(id);
    }
}
