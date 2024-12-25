package dev.project.expensetracker.dao;

import dev.project.expensetracker.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByUserId(String userId);

    Optional<Category> findById(Long id);
}
