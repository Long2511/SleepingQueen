package com.ouroboros.sleepingqueen.deck;

public class Card {
    private int ownedPlayer;
    // 1-4 for 4 players, 0: not owned

    private int position;

    private String cardImgPath;

    public Card() {

    }

    public Card(String cardImgPath) {
        this.cardImgPath = cardImgPath;
    }

    public Card(int ownedPlayer, int position) {
        this.ownedPlayer = ownedPlayer;
        this.position = position;
    }

//    void DisplayCard() {
//    Should be in CONTROLLER as DisplayCard(Card, posID)
//    }

    void PlayCard() {

    }

    public String getCardImgPath() {
        return cardImgPath;
    }
}
