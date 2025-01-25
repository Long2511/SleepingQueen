package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.card.DeckController;
import com.ouroboros.sleepingqueen.card.QueenFieldController;
import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;
import com.ouroboros.sleepingqueen.deck.cardcollection.NumberCard;
import com.ouroboros.sleepingqueen.mainPlayer.MainPlayerCardField;
import com.ouroboros.sleepingqueen.mainPlayer.MainPlayerQueenField;
import com.ouroboros.sleepingqueen.player.Player;
import com.ouroboros.sleepingqueen.subPlayer.SubPlayerFieldController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BoardViewController {

    private static String playerCount;
    private final String[] SPECIAL_QUEENS = {"Rose Queen", "Dog Queen", "Cat Queen"};
    @FXML
    public HBox mainPlayerQueenFieldBox;
    @FXML
    private StackPane rootPane;
    @FXML
    private HBox deckField;
    @FXML
    private AnchorPane overlayPane;
    @FXML
    private GridPane Menu;
    @FXML
    private QueenFieldController queenFieldController;
    @FXML
    private HBox subPlayerField;
    @FXML
    private HBox mainPlayerCardFieldBox;
    @FXML

    private MainPlayerQueenField mainPlayerQueenFieldController;
    private List<Player> playerList;
    private int currentTurnPlayerIndex;
    private SubPlayerFieldController subPlayerFieldController;
    private DeckController deckController;
    private MainPlayerCardField mainPlayerCardFieldController;

    private Card selectedQueenCard;

    private boolean isQueenCardSelected;

    public static int getPlayerCount() {
        return Integer.parseInt(playerCount);
    }

    public static void setPlayerCount(String count) {
        playerCount = count;
    }

    @FXML
    private void handleMenuButtonClick() {
        overlayPane.setVisible(!overlayPane.isVisible());
    }

    @FXML
    public void initialize() {
        isQueenCardSelected = false;
        try {
            // Try  to load Deck to the Board
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/boardView/deck-on-board.fxml"));
            VBox deckPane = loader.load();
            deckField.getChildren().add(deckPane);
            deckController = loader.getController();
            deckController.setPlayNowButtonClick(this::handlePlayNowButtonClick);

            // Load overlay
            FXMLLoader overlayLoader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/menu.fxml"));
            overlayPane = overlayLoader.load();
            overlayPane.setVisible(false);
            rootPane.getChildren().add(overlayPane);
            overlayPane.styleProperty().set("-fx-background-color: rgba(40, 32, 46  , 0.75);");
            StackPane.setAlignment(overlayPane, Pos.CENTER);

            // Set up menu button in the overlay
            MenuController menuOverlayController = overlayLoader.getController();
            menuOverlayController.setOverlayPane(overlayPane);
            AnchorPane.setTopAnchor(Menu, 564.0);

            //Add key event listener for Esc key
            rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    handleMenuButtonClick();
                }
            });

            // Try to load SubPlayerField to the Board
            FXMLLoader subPlayerFieldLoader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-field.fxml"));
            GridPane subPlayerPane = subPlayerFieldLoader.load();
            subPlayerField.getChildren().add(subPlayerPane);
            subPlayerFieldController = subPlayerFieldLoader.getController();
            subPlayerFieldController.initializeBoard(getPlayerCount());


            // Try to load MainPlayerCardField to the Board
            FXMLLoader MainPlayerCardFieldloader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/mainPlayerView/main-player-card-field.fxml"));
            mainPlayerCardFieldBox.getChildren().add((AnchorPane) MainPlayerCardFieldloader.load());
            mainPlayerCardFieldController = MainPlayerCardFieldloader.getController();

            // Try to load MainPlayerQueenField to the Board
            FXMLLoader MainPlayerQueenFieldloader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/mainPlayerView/main-player-queen-field.fxml"));
            mainPlayerQueenFieldBox.getChildren().add((AnchorPane) MainPlayerQueenFieldloader.load());
            mainPlayerQueenFieldController = MainPlayerQueenFieldloader.getController();


        } catch (Exception e) {
            e.printStackTrace();
        }

        setUpPlayer();
        queenFieldController.setOnQueenCardSelected(this::handleQueenCardSelection);
    }

    private void setUpPlayer() {
        // Set up players
        playerList = new ArrayList<>();
        for (int i = 0; i < getPlayerCount(); i++) {
            Player player = new Player("Player " + (i + 1));
            // Draw 5 cards for each player
            for (int j = 0; j < player.getMAX_NORMAL_CARDS(); j++) {
                player.setNormalCard(j, deckController.drawCard());
            }
            // Player has no queen card at the beginning
            for (int j = 0; j < player.getMAX_QUEEN_CARDS(); j++) {
                player.setQueenCard(j, null);
            }
            playerList.add(player);
        }
        // Player 1 makes a move first
        currentTurnPlayerIndex = 0;
        setUpPlayerTurn();
    }

    private void renderMainPlayerNormalCard(int playerIndex) {
        mainPlayerCardFieldController.setCard(playerList.get(playerIndex).getNormalCards());
    }

    private void renderMainPlayerQueenCard(int playerIndex) {
        mainPlayerQueenFieldController.setCard(playerList.get(playerIndex).getQueenCards());
    }

    private void renderSubPlayer(int playerIndex, int subPlayerIndex) {
        subPlayerFieldController.setPlayer(subPlayerIndex, playerList.get(playerIndex));
    }

    private void setUpPlayerTurn() {
        // load cards of the CurrentTurnPlayer to the main player card field
        renderMainPlayerNormalCard(currentTurnPlayerIndex);
        renderMainPlayerQueenCard(currentTurnPlayerIndex);
    }

    private void endPlayerTurn() {
        int nextTurnPlayerIndex = (currentTurnPlayerIndex + 1) % getPlayerCount();
        List<Card> currentPlayerQueenCards = null;

        // Retrieve the current player's cards
        List<Card> currentPlayerNormalCards = List.of(playerList.get(currentTurnPlayerIndex).getNormalCards());
        if (playerList.get(currentTurnPlayerIndex).getQueenCards() != null) {
            currentPlayerQueenCards = List.of(playerList.get(currentTurnPlayerIndex).getQueenCards());
        }


        // Retrieve the next player's cards
        List<Card> nextPlayerNormalCards = List.of(playerList.get(nextTurnPlayerIndex).getNormalCards());
        List<Card> nextPlayerQueenCards = List.of(playerList.get(nextTurnPlayerIndex).getQueenCards());

        // Swap the normal cards between the current player and the next player

        playerList.get(currentTurnPlayerIndex).setNormalCards(nextPlayerNormalCards);
        playerList.get(nextTurnPlayerIndex).setNormalCards(currentPlayerNormalCards);

        // Swap the queen cards between the current player and the next player
        playerList.get(currentTurnPlayerIndex).setQueenCards(nextPlayerQueenCards);
        assert currentPlayerQueenCards != null;
        playerList.get(nextTurnPlayerIndex).setQueenCards(currentPlayerQueenCards);

        // Update the main player card field with the next player's cards
        mainPlayerCardFieldController.setCard(playerList.get(nextTurnPlayerIndex).getNormalCards());

        // Update the sub-player field with the current player's cards
        subPlayerFieldController.setPlayer(currentTurnPlayerIndex, playerList.get(currentTurnPlayerIndex));

        currentTurnPlayerIndex = nextTurnPlayerIndex;
    }

    private void pickQueenCardFromField() {
        if (selectedQueenCard == null) {
            // TODO: prompt user to select a queen card
            System.out.println("No queen card selected.");
            return;
        }
        boolean addQueen = true;
        boolean isRoseQueen = selectedQueenCard.getCardName().equals(SPECIAL_QUEENS[0]);
        System.out.println("Selected Queen: " + selectedQueenCard.getCardName());
        if (selectedQueenCard.getCardName().equals(SPECIAL_QUEENS[1])) {
            // Dog Queen - check if player has Cat Queen
            Card[] playerQueenCards = playerList.get(currentTurnPlayerIndex).getQueenCards();
            for (Card card : playerQueenCards) {
                if (card != null && card.getCardName().equals(SPECIAL_QUEENS[2])) {
                    // Player has Cat Queen - cannot have the same Dog & Cat at the same time => Put back Dog Queen to the field
                    // TODO: prompt DOG & CAT cannot be together
                    System.out.println("Dog Queen processing");
                    addQueen = false;
                    break;
                }
            }
        } else if (selectedQueenCard.getCardName().equals(SPECIAL_QUEENS[2])) {
            // Cat Queen - check if player has Dog Queen
            Card[] playerQueenCards = playerList.get(currentTurnPlayerIndex).getQueenCards();
            for (Card card : playerQueenCards) {
                if (card != null && card.getCardName().equals(SPECIAL_QUEENS[1])) {
                    // Player has Dog Queen - cannot have the same Dog & Cat at the same time => Put back Cat Queen to the field
                    // TODO: prompt DOG & CAT cannot be together
                    System.out.println("Cat Queen processing");
                    addQueen = false;
                    break;
                }
            }
        }
        // Add queen: add to player's queen card and remove from the field
        if (addQueen) {
            playerList.get(currentTurnPlayerIndex).addQueenCard(selectedQueenCard);
            queenFieldController.removeQueenFromField(selectedQueenCard);
            renderMainPlayerQueenCard(currentTurnPlayerIndex);
        }

        if (isRoseQueen) {
            // Rose Queen - player can pick another queen
            isQueenCardSelected = true;
            // TODO: prompt user to select another queen card
        } else {
            isQueenCardSelected = false;
        }
        selectedQueenCard = null;
    }

    private void KingLogic() {
        isQueenCardSelected = true;
        System.out.println("King card can be played");
    }

    private void WandLogic() {
        System.out.println("Wand card can be played");
    }

    private void PotionLogic() {
        System.out.println("Potion card can be played");
    }

    private void KnightLogic() {
        System.out.println("Knight card can be played");
    }

    private void JesterLogic(List<Integer> chosenCardIndices) {
        System.out.println("Jester card can be played");
        Card drawnCard = deckController.drawCard();
        System.out.println("Drawn card: " + drawnCard.getType());

        if (drawnCard.getType() == CardType.NUMBER) {
            System.out.println("Drawn card is a Number card, end turn");
            //endPlayerTurn();
        } else {
            System.out.println("Drawn card is a Function card, player gets another turn");
            replacePlayedCards(chosenCardIndices, drawnCard);
            setUpPlayerTurn();
        }
        //endPlayerTurn();
    }

    private void replacePlayedCards(List<Integer> chosenCardIndices) {
        for (int index : chosenCardIndices) {
            Card card = deckController.drawCard();
            playerList.get(currentTurnPlayerIndex).setNormalCard(index, card);

            mainPlayerCardFieldController.setCard(index, card);
        }
    }

    private void replacePlayedCards(List<Integer> chosenCardIndices, Card card) {
        for (int index : chosenCardIndices) {
            playerList.get(currentTurnPlayerIndex).setNormalCard(index, card);

            mainPlayerCardFieldController.setCard(index, card);
        }
    }


    public void handleQueenCardSelection(int index) {
        selectedQueenCard = queenFieldController.getQueenCard(index);
        if (selectedQueenCard != null) {
            System.out.println("Selected Queen Card: " + selectedQueenCard.getCardImgPath());
        } else {
            System.out.println("Invalid queen card selection.");
        }
    }

    private void removeCardsFromPlayerDeck(List<Card> cardsTobeRemove) {
        playerList.get(currentTurnPlayerIndex).removeNormalCards(cardsTobeRemove);
        // add discarded cards to the deck
        for (Card card : cardsTobeRemove) {
            deckController.discardCard(card);
        }
    }

    private void PlayCard(List<Card> cards) {
    }

    private void handlePlayNowButtonClick() {
        if (isQueenCardSelected) {
            System.out.println("Picked Queen Card from field");
            pickQueenCardFromField();
            return;
        }
        System.out.println("Play Now Button Clicked");

        int numberCardsCount = 0;
        List<Integer> chosenCardIndices = mainPlayerCardFieldController.getChosenCardIndexes();


        List<Card> cards = mainPlayerCardFieldController.getChosenCards();

        // unselect all cards after retrieving the chosen cards
        mainPlayerCardFieldController.resetChosenCards();

        if (cards.isEmpty()) {
            System.out.println("No card selected");
            return;
        }

        for (Card card : cards) {
            if (card.getType() == CardType.NUMBER) {
                numberCardsCount++;
            }
        }
        if (numberCardsCount == cards.size()) {
            System.out.println("All cards are number cards");
            int sumOfCards = 0;

            // Filter and collect only NumberCard objects and sort numbers
            List<NumberCard> numberCards = cards.stream()
                    .filter(card -> card instanceof NumberCard)
                    .map(card -> (NumberCard) card).sorted(Comparator
                            .comparingInt(NumberCard::GetNumberCardValue))
                    .toList();

            // Print the sorted values
            for (NumberCard numberCard : numberCards) {
                System.out.println(numberCard.GetNumberCardValue());
            }

            for (int i = 0; i < numberCards.size() - 1; i++) {
                sumOfCards += numberCards.get(i).GetNumberCardValue();
            }

            //Check the sum of number cards is equal to the last card
            if (cards.size() == 1) {
                System.out.println("can Play those card");
                removeCardsFromPlayerDeck(cards);
                replacePlayedCards(chosenCardIndices);
            } else if (cards.size() == 2) {
                NumberCard firstCard = (NumberCard) cards.get(0);
                NumberCard secondCard = (NumberCard) cards.get(1);
                if (firstCard.GetNumberCardValue() == secondCard.GetNumberCardValue()) {
                    System.out.println("can Play those card");
                    removeCardsFromPlayerDeck(cards);
                    replacePlayedCards(chosenCardIndices);

                }
            } else if (sumOfCards == numberCards.getLast().GetNumberCardValue()) {
                removeCardsFromPlayerDeck(cards);
                replacePlayedCards(chosenCardIndices);
                System.out.println(cards.getFirst().getType() + " card can be played");
            } else {
                System.out.println("Cant play those card");

            }
        } else {
            if (cards.size() == 1) {
                //Handle logic for each card type
                switch (cards.getFirst().getType()) {
                    case KING:
                        KingLogic();
                        removeCardsFromPlayerDeck(cards);
                        replacePlayedCards(chosenCardIndices);
                        break;
                    case JESTER:
                        JesterLogic(chosenCardIndices);
                        // Todo Add JESTER case logic
                        removeCardsFromPlayerDeck(cards);
                        break;
                    case KNIGHT:
                        KnightLogic();
                        // Todo Add KNIGHT case logic
                        removeCardsFromPlayerDeck(cards);
                        break;
                    case POTION:
                        PotionLogic();
                        // Todo Add POTION case logic
                        removeCardsFromPlayerDeck(cards);
                        break;
                    case WAND:
                        WandLogic();
                        // Todo Add Wand case Logic
                        removeCardsFromPlayerDeck(cards);
                        break;
                }
            }
        }
    }


}