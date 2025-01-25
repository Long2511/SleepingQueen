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
import java.util.stream.Collectors;

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
            FXMLLoader SubPlayerFieldloader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-field.fxml"));
            GridPane subPlayerPane = SubPlayerFieldloader.load();
            subPlayerField.getChildren().add(subPlayerPane);
            subPlayerFieldController = SubPlayerFieldloader.getController();

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

    private void setUpPlayerTurn() {
        // load cards of the CurrentTurnPlayer to the main player card field
        renderMainPlayerNormalCard(currentTurnPlayerIndex);
        renderMainPlayerQueenCard(currentTurnPlayerIndex);
    }

    private void endPlayerTurn() {
        int nextTurnPlayerIndex = (currentTurnPlayerIndex + 1) % getPlayerCount();

        // TODO: swap cards between sub&main players

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
//        CardDeck cardDeck = new CardDeck();
//        List<Card> cards = new ArrayList<>();
//        cards.add(cardDeck.draw());
//        cards.add(cardDeck.draw());
//        cards.add(cardDeck.draw());
//
//        PlayCard(cards);
        List<Card> cards = mainPlayerCardFieldController.getChosenCards();
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

            // Filter and collect only NumberCard objects
            List<NumberCard> numberCards = cards.stream()
                    .filter(card -> card instanceof NumberCard)
                    .map(card -> (NumberCard) card)
                    .collect(Collectors.toList());

            // Sort the NumberCard objects by their value
            numberCards.sort(Comparator.comparingInt(NumberCard::GetNumberCardValue));

            // Print the sorted values
            for (NumberCard numberCard : numberCards) {
                System.out.println(numberCard.GetNumberCardValue());
            }

            for (int i = 0; i < numberCards.size() - 1; i++) {
                sumOfCards += numberCards.get(i).GetNumberCardValue();
            }

            //Check the sum of number cards is equal to the last card
            if (sumOfCards == numberCards.getLast().GetNumberCardValue()) {
                System.out.println("can Play those card");
                removeCardsFromPlayerDeck(cards);
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
                        break;
                    case JESTER:
                        // Todo Add JESTER case logic
                        removeCardsFromPlayerDeck(cards);
                        break;
                    case KNIGHT:
                        // Todo Add KNIGHT case logic
                        removeCardsFromPlayerDeck(cards);
                        break;
                    case POTION:
                        // Todo Add POTION case logic
                        removeCardsFromPlayerDeck(cards);
                        break;
                    case WAND:
                        // Todo Add Wand case Logic
                        removeCardsFromPlayerDeck(cards);
                        break;
                    default:

                }
            }
        }
    }
}