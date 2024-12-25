package dev.project.expensetracker.controller;

import dev.project.expensetracker.entities.Card;
import dev.project.expensetracker.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CardController {

    @Autowired
    private CardRepository cardRepo;

    @PostMapping("/addCard")
    public ResponseEntity<?> addCard(@RequestBody Card card) {
        try {
            Card savedCard = cardRepo.addCard(card);
            return ResponseEntity.status(201).body(savedCard); // 201 Created with saved card
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding the card.");
        }
    }

    @GetMapping("/getCards/{userId}")
    public ResponseEntity<?> getCards(@PathVariable String userId) {
        try {
            List<Card> userCards = cardRepo.getUserCards(userId);
            if (userCards.isEmpty()) {
                return ResponseEntity.status(404).body("No cards found for the given user.");
            }
            return ResponseEntity.ok(userCards); // 200 OK with the list of cards
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching the cards.");
        }
    }

    @PutMapping("/updateCard/{cardId}")
    public ResponseEntity<?> updateCard(@PathVariable Long cardId, @RequestBody Card updatedCard) {
        Optional<Card> existingCard = cardRepo.getCardById(cardId);

        if (existingCard.isPresent()) {
            Card card = existingCard.get();
            card.setCardName(updatedCard.getCardName());
            card.setCardNumber(updatedCard.getCardNumber());
            card.setValidUpto(updatedCard.getValidUpto());
            card.setCardType(updatedCard.getCardType());

            cardRepo.addCard(card); // Save updated card
            return ResponseEntity.ok(card);
        } else {
            return ResponseEntity.status(404).body("Card not found.");
        }
    }

    @DeleteMapping("/deleteCard/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId) {
        Optional<Card> existingCard = cardRepo.getCardById(cardId);

        if (existingCard.isPresent()) {
            cardRepo.deleteCardById(cardId); // Delete the card
            return ResponseEntity.ok("Card deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Card not found.");
        }
    }
}
