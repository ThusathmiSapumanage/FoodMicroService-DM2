package com.example.FoodMicroservice_DM2.Controller;

import com.example.FoodMicroservice_DM2.Entity.Food;
import com.example.FoodMicroservice_DM2.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;

@RestController
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/foods")
    public String addFood(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam MultipartFile file,
            @RequestParam Double price
    ) {
        try {
            foodService.addFood(name, description, file, price);
            return "Food item added successfully!";
        } catch (IOException e) {
            return "Error adding food item: " + e.getMessage();
        }
    }

    @GetMapping("/foods")
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("foods/{id}")
    public Food getFoodById(@PathVariable int id) {
        return foodService.getFoodById(id);
    }

    @PutMapping("foods/{id}")
    public String updateFood(
            @PathVariable int id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam Double price
    ) {
        try {
            foodService.updateFood(id, name, description, file, price);
            return "Food item updated successfully!";
        } catch (IOException e) {
            return "Error updating food item: " + e.getMessage();
        }
    }

    @DeleteMapping("foods/{id}")
    public String deleteFood(@PathVariable int id) {
        foodService.deleteFood(id);
        return "Food item deleted successfully!";
    }

    @GetMapping("foods/search") //to test
    public List<Food> searchFoodByName(@RequestParam String name) {
        return foodService.searchFoodByName(name);
    }

    @GetMapping("foods/filter") //to test
    public List<Food> filterByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice
    ) {
        return foodService.filterByPriceRange(minPrice, maxPrice);
    }
}
