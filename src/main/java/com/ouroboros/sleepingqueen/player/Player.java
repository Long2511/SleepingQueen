package com.ouroboros.sleepingqueen.player;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

import java.util.ArrayList;
import java.util.List;

public class Player {
    // Each player can have up to 5 normal cards and 4 queen cards at a time.
    private final int MAX_NORMAL_CARDS = 5;
    private final int MAX_QUEEN_CARDS = 4;

    private int position;
    private String name;
    private int score;

    private Card[] normalCards;
    private Card[] queenCards;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.normalCards = new Card[MAX_NORMAL_CARDS];
        this.queenCards = new Card[MAX_QUEEN_CARDS];
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
    }
    public String getName() {
        return name;
    }
    public Card[] getNormalCards() {
        return normalCards;
    }
    public Card[] getQueenCards() {
        return queenCards;
    }

    public int getMAX_NORMAL_CARDS() {
        return MAX_NORMAL_CARDS;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void addCard(int position, Card newCard) {
        if (newCard.getType() == CardType.QUEEN) {
            queenCards[position] = newCard;
        } else {
            normalCards[position] = newCard;
        }
    }
}