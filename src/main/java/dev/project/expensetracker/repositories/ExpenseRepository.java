package dev.project.expensetracker.repositories;

import dev.project.expensetracker.entities.Expense;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpenseRepository {
    Expense addExpense(Expense expense);

    List<Expense> getUserExpenses(String userId);

    Optional<Expense> getExpenseById(Long expenseId);

    void deleteExpenseById(Long expenseId);

    List<Map<String, Object>> getUserMonthlyExpense(String userId);

    List<Map<String, Object>> getYearlyExpenses(String userId);
}
