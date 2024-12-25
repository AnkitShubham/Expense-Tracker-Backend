package dev.project.expensetracker.services;

import dev.project.expensetracker.dao.ExpenseDao;
import dev.project.expensetracker.entities.Expense;
import dev.project.expensetracker.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class ExpenseServices implements ExpenseRepository {

    @Autowired
    private ExpenseDao expenseDao;

    @Override
    public Expense addExpense(Expense expense) {
        return expenseDao.save(expense);
    }

    @Override
    public List<Expense> getUserExpenses(String userId) {
        return expenseDao.findExpensesByUserId(userId);
    }

    @Override
    public Optional<Expense> getExpenseById(Long expenseId) {
        return expenseDao.findById(expenseId);
    }

    @Override
    public void deleteExpenseById(Long expenseId) {
        expenseDao.deleteById(expenseId);
    }

    @Override
    public List<Map<String, Object>> getUserMonthlyExpense(String userId) {
        // Calculate start and end dates for the current month
        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

        String startDate = firstDay.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String endDate = lastDay.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Fetch expenses for the current month
        List<Expense> expenses = expenseDao.findExpensesByUserIdAndExpenseDateBetween(userId, startDate, endDate);

        // Group expenses by category and calculate the total
        Map<String, Double> expenseSummary = new HashMap<>();
        for (Expense expense : expenses) {
            expenseSummary.put(
                    expense.getCategory(),
                    expenseSummary.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount()
            );
        }

        // Convert the grouped data to the desired format
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Double> entry : expenseSummary.entrySet()) {
            Map<String, Object> categorySummary = new HashMap<>();
            categorySummary.put("category", entry.getKey());
            categorySummary.put("value", entry.getValue());
            result.add(categorySummary);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> getYearlyExpenses(String userId) {
        // Get the current year
        int currentYear = LocalDate.now().getYear();

        // Calculate the start and end dates of the year
        String startDate = "01/01/" + currentYear;
        String endDate = "31/12/" + currentYear;

        // Fetch all expenses for the user within the year
        List<Expense> expenses = expenseDao.findExpensesByUserIdAndExpenseDateBetween(userId, startDate, endDate);

        // Group expenses by month and calculate the total
        Map<Month, Double> monthlyExpenseMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Expense expense : expenses) {
            LocalDate expenseDate = LocalDate.parse(expense.getExpenseDate(), formatter);
            Month month = expenseDate.getMonth();
            monthlyExpenseMap.put(
                    month,
                    monthlyExpenseMap.getOrDefault(month, 0.0) + expense.getAmount()
            );
        }

        // Convert the map to the desired format
        List<Map<String, Object>> result = new ArrayList<>();
        for (Month month : Month.values()) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            monthData.put("expense", monthlyExpenseMap.getOrDefault(month, 0.0));
            result.add(monthData);
        }

        return result;
    }
}
