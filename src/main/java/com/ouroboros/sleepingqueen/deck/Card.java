package com.ouroboros.sleepingqueen.deck;

public class Card {
    private String cardName;
    private int ownedPlayer;    // 1-5 for 5 players, 0: not owned

    private int position;

    private String cardImgPath;
    private String backImgPath;
    protected CardType type;

    public Card() {}

    public Card(String cardName, String cardImgPath, String backImgPath) {
        this.cardName = cardName;
        this.cardImgPath = cardImgPath;
        this.backImgPath = backImgPath;
        this.ownedPlayer = 0;
        this.position = -1;
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

//    void PlayCard() {
//    Should be in CONTROLLER
//    }

    public String getCardImgPath() {
        return cardImgPath;
    }
    public String getBackImgPath() {
        return backImgPath;
    }
    public CardType getType() {
        return this.type;
    }
}
