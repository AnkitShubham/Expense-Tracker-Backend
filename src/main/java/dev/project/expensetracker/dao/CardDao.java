package dev.project.expensetracker.dao;

import dev.project.expensetracker.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardDao extends JpaRepository<Card, Long> {
    List<Card> findCardsByUserId(String userId);
    Optional<Card> findById(Long id);
    void deleteById(Long id);
}
