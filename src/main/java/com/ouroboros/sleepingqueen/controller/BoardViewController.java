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
                player.addCard(j, deckController.drawCard());
            }
            playerList.add(player);
        }
        // Player 1 makes a move first
        currentTurnPlayerIndex = 0;
        setUpPlayerTurn();
    }

    private void setUpPlayerTurn() {
        // load cards of the CurrentTurnPlayer to the main player card field
        mainPlayerCardFieldController.setCard(playerList.get(currentTurnPlayerIndex).getNormalCards());
        mainPlayerQueenFieldController.setCard(playerList.get(currentTurnPlayerIndex).getQueenCards());
    }

    private void endPlayerTurn() {
        int nextTurnPlayerIndex = (currentTurnPlayerIndex + 1) % getPlayerCount();

        // TODO: swap cards between sub&main players

        currentTurnPlayerIndex = nextTurnPlayerIndex;
    }

    private void KingLogic() {
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
            endPlayerTurn();
        } else {
            System.out.println("Drawn card is a Function card, player gets another turn");
            replacePlayedCards(chosenCardIndices, drawnCard);
            setUpPlayerTurn();
        }

    }

    private void replacePlayedCards(List<Integer> chosenCardIndices, Card card) {
        for (int index : chosenCardIndices) {
            playerList.get(currentTurnPlayerIndex).addCard(index, card);
        }
    }


    public void handleQueenCardSelection(int index) {
        Card selectedCard = queenFieldController.selectQueenCard(index);
        if (selectedCard != null) {
            System.out.println("Selected Queen Card: " + selectedCard.getCardImgPath());
        } else {
            System.out.println("Invalid queen card selection.");
        }
    }

    private void removeCardsFromPlayerDeck(List<Card> cardsTobeRemove) {
        //todo: implement logic to remove cards from players deck
    }

    private void PlayCard(List<Card> cards) {
    }

    private void handlePlayNowButtonClick() {
        int numberCardsCount = 0;
        List<Integer> chosenCardIndices = mainPlayerCardFieldController.getChosenCardIndex();


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
            } else if (cards.size() == 1) {
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