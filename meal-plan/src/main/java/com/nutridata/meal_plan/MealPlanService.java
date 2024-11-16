package com.nutridata.meal_plan;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealPlanService {
    private final MealPlanRepository mealPlanRepository;

    public MealPlanService(MealPlanRepository mealPlanRepository) {
        this.mealPlanRepository = mealPlanRepository;
    }

    public List<MealPlan> getMealPlans() {
        return this.mealPlanRepository.findAll();
    }

    public MealPlan getMealPlan(Long id) {
        return this.mealPlanRepository.findById(id)
                .orElseThrow(() -> new MealPlanNotFoundException("Meal plan with id " + id + " not found"));
    }

    public MealPlan addNewMealPlan(MealPlan mealPlan) {
        return this.mealPlanRepository.save(mealPlan);
    }

    public void deleteMealPlan(Long id) {
        this.mealPlanRepository.deleteById(id);
    }

    public MealPlan updateMealPlan(Long id, MealPlan mealPlan) {
        return this.mealPlanRepository.findById(id)
                .map(existingMealPlan -> {
                    existingMealPlan.setName(mealPlan.getName());
                    return this.mealPlanRepository.save(existingMealPlan);
                })
                .orElseThrow(() -> new MealPlanNotFoundException("Meal plan with id " + id + " not found"));
    }
}
