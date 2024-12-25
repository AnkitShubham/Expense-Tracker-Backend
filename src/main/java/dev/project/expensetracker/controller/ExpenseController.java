package dev.project.expensetracker.controller;

import dev.project.expensetracker.dao.UserDao;
import dev.project.expensetracker.entities.Expense;
import dev.project.expensetracker.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepo;
    @Autowired
    private UserDao userDao;

    @PostMapping("/addExpense")
    public ResponseEntity<?> addExpense(@RequestBody Expense expense) {
        try {
            Expense savedExpense = expenseRepo.addExpense(expense);
            return ResponseEntity.status(201).body(savedExpense); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding the expense.");
        }
    }

    @GetMapping("/getExpenses/{userId}")
    public ResponseEntity<?> getExpenses(@PathVariable String userId) {
        try {
            List<Expense> expenses = expenseRepo.getUserExpenses(userId);
            if (expenses.isEmpty()) {
                return ResponseEntity.status(404).body("No expenses found for the given user.");
            }
            return ResponseEntity.ok(expenses); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching expenses.");
        }
    }

    @PutMapping("/updateExpense/{expenseId}")
    public ResponseEntity<?> updateExpense(@PathVariable Long expenseId, @RequestBody Expense updatedExpense) {
        Optional<Expense> existingExpense = expenseRepo.getExpenseById(expenseId);

        if (existingExpense.isPresent()) {
            Expense expense = existingExpense.get();
            expense.setCategory(updatedExpense.getCategory());
            expense.setPaymentMethod(updatedExpense.getPaymentMethod());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDescription(updatedExpense.getDescription());
            expense.setExpenseDate(updatedExpense.getExpenseDate());

            expenseRepo.addExpense(expense); // Save the updated expense
            return ResponseEntity.ok(expense); // 200 OK
        } else {
            return ResponseEntity.status(404).body("Expense not found.");
        }
    }

    @DeleteMapping("/deleteExpense/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long expenseId) {
        Optional<Expense> existingExpense = expenseRepo.getExpenseById(expenseId);

        if (existingExpense.isPresent()) {
            expenseRepo.deleteExpenseById(expenseId); // Delete the expense
            return ResponseEntity.ok("Expense deleted successfully."); // 200 OK
        } else {
            return ResponseEntity.status(404).body("Expense not found.");
        }
    }

    @GetMapping("/getMonthlyExpenses/{userId}")
    public ResponseEntity<?> getMonthlyExpense(@PathVariable String userId){
        try{
            List<Map<String, Object>> expenseSummary = expenseRepo.getUserMonthlyExpense(userId);
            if (expenseSummary.isEmpty()) {
                return ResponseEntity.status(404).body("No expense data found for the given user.");
            }
            return ResponseEntity.ok(expenseSummary);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching expenses.");
        }
    }

    @GetMapping("/getYearlyExpenses/{userId}")
    public ResponseEntity<?> getYearlyExpenses(@PathVariable String userId) {
        try {
            List<Map<String, Object>> yearlyExpenses = expenseRepo.getYearlyExpenses(userId);

            if (yearlyExpenses.isEmpty()) {
                return ResponseEntity.status(404).body("No expense data found for the given user.");
            }

            return ResponseEntity.ok(yearlyExpenses);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching yearly expenses.");
        }
    }

}
