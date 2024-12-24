package com.ouroboros.sleepingqueen.dao;

import com.ouroboros.sleepingqueen.deck.Card;

import java.util.List;
import java.util.Optional;



public interface CardDAO {
    List<Card> getAll();
    List<Card> getAllQueenCard();
}
