package dev.project.expensetracker.dao;

import dev.project.expensetracker.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseDao extends JpaRepository<Expense, Long> {
    List<Expense> findExpensesByUserId(String userId);

    Optional<Expense> findById(Long id);

    List<Expense> findExpensesByUserIdAndExpenseDateBetween(String userId, String startDate, String endDate);

//    List<Expense> findExpensesByUserIdAndExpenseDateBetween(String userId, String startDate, String endDate);
}
