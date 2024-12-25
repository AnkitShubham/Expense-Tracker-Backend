package dev.project.expensetracker.services;

import dev.project.expensetracker.dao.CardDao;
import dev.project.expensetracker.entities.Card;
import dev.project.expensetracker.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServices implements CardRepository {

    @Autowired
    private CardDao cardDao;

    @Override
    public Card addCard(Card card) {
        return cardDao.save(card);
    }

    @Override
    public List<Card> getUserCards(String userId) {
        return cardDao.findCardsByUserId(userId);
    }

    @Override
    public Optional<Card> getCardById(Long cardId) {
        return cardDao.findById(cardId);
    }

    @Override
    public void deleteCardById(Long cardId) {
        cardDao.deleteById(cardId);
    }
}
