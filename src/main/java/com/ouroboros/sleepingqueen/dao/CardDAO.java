/**
 * Interface for CardDAO
 * This interface is used to get all the normal and queen cards
 * from the database
 * Using DAO pattern to isolate database access code from the rest of the application
 *
 * @author: Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.dao;

import com.ouroboros.sleepingqueen.deck.Card;

import java.util.List;


public interface CardDAO {
    List<Card> getAllNormalCard();

    List<Card> getAllQueenCard();

}
