package dev.project.expensetracker.repositories;

import dev.project.expensetracker.entities.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository {
    Card addCard(Card card);

    List<Card> getUserCards(String userId);

    Optional<Card> getCardById(Long cardId);

    void deleteCardById(Long cardId);
}
