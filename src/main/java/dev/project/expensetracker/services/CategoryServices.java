package dev.project.expensetracker.services;

import dev.project.expensetracker.dao.CategoryDao;
import dev.project.expensetracker.entities.Category;
import dev.project.expensetracker.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServices implements CategoryRepository {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category addCategory(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public List<Category> getUserCategories(String userId) {
        return categoryDao.findCategoriesByUserId(userId);
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryDao.findById(categoryId);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        categoryDao.deleteById(categoryId);
    }
}
