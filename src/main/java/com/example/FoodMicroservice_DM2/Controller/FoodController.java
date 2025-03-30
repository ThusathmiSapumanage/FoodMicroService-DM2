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
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("price") Double price,
            @RequestParam("category") String category) throws IOException {
        try
        {
            foodService.addFood(name, description, file, price, category);
            return "Food added successfully";
        }
        catch (Exception e)
        {
            return "Error adding food: " + e.getMessage();
        }
    }

    @PutMapping("/foods/{id}")
    public String updateFood(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("price") Double price,
            @RequestParam("category") String category) throws IOException {
        try
        {
            foodService.updateFood(id, name, description, file, price, category);
            return "Food updated successfully";
        }
        catch (Exception e)
        {
            return "Error updating food: " + e.getMessage();
        }
    }

    @DeleteMapping("/foods/{id}")
    public String deleteFood(@PathVariable("id") int id) {
        try
        {
            foodService.deleteFood(id);
            return "Food deleted successfully";
        }
        catch (Exception e)
        {
            return "Error deleting food: " + e.getMessage();
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

    @GetMapping("foods/search")
    public List<Food> searchByName(@RequestParam String name) {
        return foodService.searchFoodByName(name);
    }

    @GetMapping("foods/filter")
    public List<Food> filterByPrice(@RequestParam Double min,
                                    @RequestParam Double max) {
        return foodService.filterByPrice(min, max);
    }

}
