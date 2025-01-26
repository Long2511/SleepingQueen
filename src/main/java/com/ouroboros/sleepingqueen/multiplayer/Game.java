/**
 * Game.java
 * Purpose: This class is used to store the game information.
 *
 * @Author Hai Long Mac
 */

package com.ouroboros.sleepingqueen.multiplayer;

import com.ouroboros.sleepingqueen.deck.Card;

import java.io.Serializable;
import java.util.List;

public class Game implements Serializable {
    private String gameId;
    private List<Player> players;
    private boolean gameStatus;
    private String gameWinner;
    private List<Card> queensCardsDeck;
    private List<Card> gameDeck;

    // Getters and setters
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean isGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getGameWinner() {
        return gameWinner;
    }

    public void setGameWinner(String gameWinner) {
        this.gameWinner = gameWinner;
    }

    public List<Card> getQueensCardsDeck() {
        return queensCardsDeck;
    }

    public void setQueensCardsDeck(List<Card> queensCardsDeck) {
        this.queensCardsDeck = queensCardsDeck;
    }

    public List<Card> getGameDeck() {
        return gameDeck;
    }

    public void setGameDeck(List<Card> gameDeck) {
        this.gameDeck = gameDeck;
    }
}