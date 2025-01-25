package com.ouroboros.sleepingqueen.player;

import com.ouroboros.sleepingqueen.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    // Each player can have up to 5 normal cards and 4 queen cards at a time.
    private final int MAX_NORMAL_CARDS = 5;
    private final int MAX_QUEEN_CARDS = 4;

    private int position;
    private String name;
    private int score;
    private int subPlayerId;

    private Card[] normalCards;
    private Card[] queenCards;

    private boolean[] isChosenCards;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.normalCards = new Card[MAX_NORMAL_CARDS];
        this.queenCards = new Card[MAX_QUEEN_CARDS];
        this.isChosenCards = new boolean[MAX_NORMAL_CARDS];
        resetChosenCards();
    }

    public void resetChosenCards() {
        for (int i = 0; i < MAX_NORMAL_CARDS; i++) {
            isChosenCards[i] = false;
        }
    }

    public List<Card> getChosenCards() {
        List<Card> chosenCards = new ArrayList<>();
        for (int i = 0; i < MAX_NORMAL_CARDS; i++) {
            if (isChosenCards[i]) {
                chosenCards.add(normalCards[i]);
            }
        }
        return chosenCards;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public Card[] getNormalCards() {
        return normalCards;
    }

    public void setNormalCards(Card[] cards) {
        for (int i = 0; i < MAX_NORMAL_CARDS; i++) {
            normalCards[i] = cards[i];
        }
    }

    public Card[] getQueenCards() {
        return queenCards;
    }

    public void setQueenCards(Card[] cards) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            queenCards[i] = cards[i];
        }
    }

    public int getMAX_NORMAL_CARDS() {
        return MAX_NORMAL_CARDS;
    }

    public int getMAX_QUEEN_CARDS() {
        return MAX_QUEEN_CARDS;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void setQueenCard(int position, Card newCard) {
        queenCards[position] = newCard;
    }

    public void setNormalCard(int position, Card newCard) {
        normalCards[position] = newCard;
    }

    public void addQueenCard(Card newCard) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            if (queenCards[i] == null) {
                queenCards[i] = newCard;
                break;
            }
        }
    }

    public void removeNormalCards(List<Card> cards) {
        for (Card card : cards) {
            for (int i = 0; i < MAX_NORMAL_CARDS; i++) {
                if (normalCards[i] == card) {
                    normalCards[i] = null;
                    break;
                }
            }
        }
    }

    public void setChosenCard(int index, boolean isChosen) {
        isChosenCards[index] = isChosen;
    }
}