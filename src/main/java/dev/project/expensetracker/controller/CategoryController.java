package dev.project.expensetracker.controller;

import dev.project.expensetracker.entities.Category;
import dev.project.expensetracker.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryRepo.addCategory(category);
            return ResponseEntity.status(201).body(savedCategory); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding the category.");
        }
    }

    @GetMapping("/getCategories/{userId}")
    public ResponseEntity<?> getCategories(@PathVariable String userId) {
        try {
            List<Category> categories = categoryRepo.getUserCategories(userId);
            if (categories.isEmpty()) {
                return ResponseEntity.status(404).body("No categories found for the given user.");
            }
            return ResponseEntity.ok(categories); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching categories.");
        }
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody Category updatedCategory) {
        Optional<Category> existingCategory = categoryRepo.getCategoryById(categoryId);

        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setName(updatedCategory.getName());
            category.setDescription(updatedCategory.getDescription());

            categoryRepo.addCategory(category); // Save the updated category
            return ResponseEntity.ok(category); // 200 OK
        } else {
            return ResponseEntity.status(404).body("Category not found.");
        }
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        Optional<Category> existingCategory = categoryRepo.getCategoryById(categoryId);

        if (existingCategory.isPresent()) {
            categoryRepo.deleteCategoryById(categoryId); // Delete the category
            return ResponseEntity.ok("Category deleted successfully."); // 200 OK
        } else {
            return ResponseEntity.status(404).body("Category not found.");
        }
    }
}
