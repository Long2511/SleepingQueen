package com.ouroboros.sleepingqueen.dao;

import com.ouroboros.sleepingqueen.deck.Card;

import java.util.List;


public interface CardDAO {
    List<Card> getAllNormalCard();
    List<Card> getAllQueenCard();

}
