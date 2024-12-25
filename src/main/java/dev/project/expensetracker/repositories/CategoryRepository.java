package dev.project.expensetracker.repositories;

import dev.project.expensetracker.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category addCategory(Category category);

    List<Category> getUserCategories(String userId);

    Optional<Category> getCategoryById(Long categoryId);

    void deleteCategoryById(Long categoryId);
}
