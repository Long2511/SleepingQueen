package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.card.DeckController;
import com.ouroboros.sleepingqueen.card.QueenFieldController;
import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;
import com.ouroboros.sleepingqueen.deck.cardcollection.NumberCard;
import com.ouroboros.sleepingqueen.deck.cardcollection.QueenCard;
import com.ouroboros.sleepingqueen.mainPlayer.MainPlayerCardField;
import com.ouroboros.sleepingqueen.mainPlayer.MainPlayerQueenField;
import com.ouroboros.sleepingqueen.player.Player;
import com.ouroboros.sleepingqueen.subPlayer.SubPlayerFieldController;
import com.ouroboros.sleepingqueen.ults.ButtonSound;
import com.ouroboros.sleepingqueen.ults.ConfirmButtonSound;
import com.ouroboros.sleepingqueen.ults.Toast;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class BoardViewController {

    private static String playerCount;
    private final String[] SPECIAL_QUEENS = {"Rose Queen", "Dog Queen", "Cat Queen"};
    private final int toastTimeOut = 4000;
    @FXML
    public HBox mainPlayerQueenFieldBox;
    public Button menuBtn;
    @FXML
    Text MainPlayer;
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
    private int nextTurnPlayerIndexForReal;  // next player index value (if can be determined), -1 If needed to calculate.
    private SubPlayerFieldController subPlayerFieldController;
    private DeckController deckController;
    private MainPlayerCardField mainPlayerCardFieldController;
    private Card selectedQueenCard;
    private boolean isQueenCardSelected;
    private List<Integer> currentSubPlayerIndex;

    private int selectedSleepingQueenIndex;
    private Card selectedAwakenQueenCard;
    private int awakenQueenHolderIndex;
    private int selectedAwakenQueenIndex;
    private boolean isKnightPhase;
    private boolean isDragonPhase;
    private MediaPlayer mediaPlayer;
    private boolean isPotionPhase;
    private boolean isWandPhase;
    private DropShadow choosingEffect;


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
            menuBtn.setOnMouseClicked(event -> ButtonSound.playButtonClickSound());
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

        initialzePhase();
        setUpPlayer();
        setupEffect();
        queenFieldController.setOnQueenCardSelected(this::handleQueenCardSelection);
        subPlayerFieldController.setOnAwakenQueenCardSelected(this::handleAwakenQueenCardSelection);
    }

    private void setupEffect() {
        choosingEffect = new DropShadow();
        choosingEffect.setBlurType(BlurType.TWO_PASS_BOX);
        choosingEffect.setRadius(10.0);
        choosingEffect.setSpread(0.4);
        choosingEffect.setColor(Color.web("#37ff00"));
    }

    private void initialzePhase() {
        isQueenCardSelected = false;
        isKnightPhase = false;
        isDragonPhase = false;
        isPotionPhase = false;
        isWandPhase = false;
        selectedAwakenQueenIndex = -1;  // no awaken queen card selected
        selectedSleepingQueenIndex = -1;
        selectedQueenCard = null;  // no queen card selected

        queenFieldController.setIdleQueenField(true);
        subPlayerFieldController.setIdle(true);
    }

    private void setUpPlayer() {
        // Set up players
        playerList = new ArrayList<>();

        currentSubPlayerIndex = new ArrayList<>(getPlayerCount());

        awakenQueenHolderIndex = 0;
        for (int i = 0; i < getPlayerCount(); i++) {
            currentSubPlayerIndex.add(i - 1);
            Player player = new Player("Player " + (i + 1));
            // Draw 5 cards for each player
            for (int j = 0; j < player.getMAX_NORMAL_CARDS(); j++) {
                player.setNormalCard(j, deckController.drawCard());
            }
            // Player has no queen card at the beginning
            for (int j = 0; j < player.getMAX_QUEEN_CARDS(); j++) {
                player.setQueenCard(j, null);
                player.setQueenIndex(j, awakenQueenHolderIndex);
                awakenQueenHolderIndex += 1;
            }
            playerList.add(player);
        }
        // Player 1 makes a move first
        currentTurnPlayerIndex = 0;
        nextTurnPlayerIndexForReal = -1;
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

        MainPlayer.setText(playerList.get(currentTurnPlayerIndex).getName());
    }

    private boolean isAllQueenCardSelected() {
        int totalQueenCard = 0;
        for (Player player : playerList) {
            for (Card card : player.getQueenCards()) {
                if (card != null) {
                    totalQueenCard++;
                }
            }
        }
        return totalQueenCard == 12;
    }

    private void endPlayerTurn() {
        Toast.show((Stage) rootPane.getScene().getWindow(), playerList.get(currentTurnPlayerIndex).getName() + " has ended the turn", toastTimeOut);
        PauseTransition pause = new PauseTransition(Duration.seconds(rootPane.getScene() != null ? 2 : 0));
        pause.setOnFinished(event -> {
            //Check for win condition
            checkWinCondition();

            int nextTurnPlayerIndex = nextTurnPlayerIndexForReal;
            if (nextTurnPlayerIndex == -1) {
                nextTurnPlayerIndex = (currentTurnPlayerIndex + 1) % getPlayerCount();
            }
            // reset next player
            nextTurnPlayerIndexForReal = -1;
            if (nextTurnPlayerIndex == currentTurnPlayerIndex) {
                // Player gets another turn
                return;
            }

            // Swap currentSubPlayerIndex between current player and next player
            int temp = currentSubPlayerIndex.get(currentTurnPlayerIndex);
            currentSubPlayerIndex.set(currentTurnPlayerIndex, currentSubPlayerIndex.get(nextTurnPlayerIndex));
            currentSubPlayerIndex.set(nextTurnPlayerIndex, temp);

            renderSubPlayer(currentTurnPlayerIndex, currentSubPlayerIndex.get(currentTurnPlayerIndex));

            currentTurnPlayerIndex = nextTurnPlayerIndex;
            setUpPlayerTurn();
            Toast.show((Stage) rootPane.getScene().getWindow(), playerList.get(nextTurnPlayerIndex).getName() + " turn.", toastTimeOut);

        });
        pause.play();
    }

    private void endPlayerTurn(int nextTurnPlayerIndex) {
        Toast.show((Stage) rootPane.getScene().getWindow(), playerList.get(currentTurnPlayerIndex).getName() + " has ended the turn", toastTimeOut);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            //Check win condition
            if (nextTurnPlayerIndex == currentTurnPlayerIndex) {
                // Player gets another turn
                return;
            }
            // Swap currentSubPlayerIndex between current player and next player
            int temp = currentSubPlayerIndex.get(currentTurnPlayerIndex);
            currentSubPlayerIndex.set(currentTurnPlayerIndex, currentSubPlayerIndex.get(nextTurnPlayerIndex));
            currentSubPlayerIndex.set(nextTurnPlayerIndex, temp);

            renderSubPlayer(currentTurnPlayerIndex, currentSubPlayerIndex.get(currentTurnPlayerIndex));
            currentTurnPlayerIndex = nextTurnPlayerIndex;

            setUpPlayerTurn();
        });
        pause.play();
    }

    private void pickQueenCardFromField() {

        if (selectedQueenCard == null) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Please select a valid queen card first!", toastTimeOut);
            return;
        }
        boolean addQueen = true;
        boolean isRoseQueen = selectedQueenCard.getCardName().equals(SPECIAL_QUEENS[0]);
        Toast.show((Stage) rootPane.getScene().getWindow(), "Selected Queen: " + selectedQueenCard.getCardName(), toastTimeOut);

        if (selectedQueenCard.getCardName().equals(SPECIAL_QUEENS[1])) {
            // Dog Queen - check if player has Cat Queen
            Card[] playerQueenCards = playerList.get(currentTurnPlayerIndex).getQueenCards();
            for (Card card : playerQueenCards) {
                if (card != null && card.getCardName().equals(SPECIAL_QUEENS[2])) {
                    // Player has Cat Queen - cannot have the same Dog & Cat at the same time => Put back Dog Queen to the field
                    Toast.show((Stage) rootPane.getScene().getWindow(), "Dog Queen and Cat Queen cannot be pick together", toastTimeOut);
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
                    Toast.show((Stage) rootPane.getScene().getWindow(), "Dog Queen and Cat Queen cannot be pick together", toastTimeOut);
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

        if (isRoseQueen && !isAllQueenCardSelected()) {
            // Rose Queen - player can pick another queen
            isQueenCardSelected = true;
            Toast.show((Stage) rootPane.getScene().getWindow(), "Picked Rose Queen, please select another Queen", toastTimeOut);
        } else if (!isAllQueenCardSelected()) {
            isQueenCardSelected = false;
        } else {
            endPlayerTurn();
        }

        // remove selected card effect
        queenFieldController.setCardEffectByIndex(selectedSleepingQueenIndex, null);
        selectedQueenCard = null;
        selectedSleepingQueenIndex = -1;
    }

    private void DragonLogic() {
        System.out.println("Dragon card can be played");
    }

    private void KingLogic() {
        isQueenCardSelected = true;
        System.out.println("King card can be played");
        queenFieldController.setIdleQueenField(false);
        Toast.show((Stage) rootPane.getScene().getWindow(), "Please select a Queen card and confirm", toastTimeOut);
    }

    private void WandLogic() {
        System.out.println("Wand card can be played");
    }

    private void PotionLogic() {
        isPotionPhase = true;
        Toast.show((Stage) rootPane.getScene().getWindow(), "Pick opponent queen card from other players", toastTimeOut);
        Toast.show((Stage) rootPane.getScene().getWindow(), "Pick position for the sleeping queen", toastTimeOut);
        subPlayerFieldController.setIdle(false);
        queenFieldController.setIdleQueenField(false);
        System.out.println("Potion card can be played");
    }

    private void KnightLogic() {
        isKnightPhase = true;
        Toast.show((Stage) rootPane.getScene().getWindow(), "Select Queen Card from other players", toastTimeOut);
        // Enter knight phase: player can select opponent queen
        subPlayerFieldController.setIdle(false);
    }

    private void JesterLogic(List<Integer> chosenCardIndices) {
        System.out.println("Jester card can be played");
        Card drawnCard = deckController.drawCard();

        System.out.println();

        if (drawnCard.getType() == CardType.NUMBER) {

            replacePlayedCards(chosenCardIndices, deckController.drawCard());
            int indexOffset = ((NumberCard) drawnCard).GetNumberCardValue() % getPlayerCount() - 1;
            indexOffset = (indexOffset + getPlayerCount()) % getPlayerCount();  // make sure it's positive
            int nextTurnPlayerIndex = (currentTurnPlayerIndex + indexOffset) % getPlayerCount();
            Toast.show((Stage) rootPane.getScene().getWindow(), "Drawn card is a Number card " + ((NumberCard) drawnCard).GetNumberCardValue() + ", moved to player " + playerList.get(nextTurnPlayerIndex).getName(), toastTimeOut);
            nextTurnPlayerIndexForReal = (currentTurnPlayerIndex + 1) % getPlayerCount();

            endPlayerTurn(nextTurnPlayerIndex);
            deckController.discardCard(drawnCard);
            KingLogic();
            return;
        }
        Toast.show((Stage) rootPane.getScene().getWindow(), "Drawn card is a Function card, player gets another turn", toastTimeOut);
        replacePlayedCards(chosenCardIndices, drawnCard);
        setUpPlayerTurn();
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
        if (selectedSleepingQueenIndex != -1) {
            // reselect -> remove effect of the old card
            queenFieldController.setCardEffectByIndex(selectedSleepingQueenIndex, null);
        }
        // add effect to the selected card
        queenFieldController.setCardEffectByIndex(index, choosingEffect);
        selectedSleepingQueenIndex = index;
        selectedQueenCard = queenFieldController.getQueenCard(index);
        if (isQueenCardSelected) {
            if (selectedQueenCard != null) {
                System.out.println("Selected Queen Card: " + selectedQueenCard.getCardImgPath());
            } else {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Invalid queen card selection.", toastTimeOut);
            }
        } else if (isPotionPhase) {
            if (selectedQueenCard != null) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Invalid position selection.", toastTimeOut);
            } else {
                System.out.println("Selected Position: " + selectedSleepingQueenIndex);
            }
        } else {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Queen card cannot be selected at this phase.", toastTimeOut);
        }
    }

    public void handleAwakenQueenCardSelection(int index) {
        if (selectedAwakenQueenIndex != -1) {
            // reselect -> remove effect of the old card
            subPlayerFieldController.setCardEffectByIndex(selectedAwakenQueenIndex, null);
        }
        // add effect to the selected card
        subPlayerFieldController.setCardEffectByIndex(index, choosingEffect);
        selectedAwakenQueenIndex = index;
        selectedAwakenQueenCard = getAwakenQueenCard(index);
        if (selectedAwakenQueenCard != null) {
            System.out.println("Selected Awaken Queen Card: " + selectedAwakenQueenCard.getCardImgPath());
        } else {
            System.out.println("Invalid awaken queen card selection.");
        }
    }

    private Card getAwakenQueenCard(int index) {
        for (Player player : playerList) {
            if (player.ownQueenCardByIndex(index)) {
                return player.getQueenCardByIndex(index);
            }
        }
        return null;
    }

    private void removeCardsFromPlayerDeck(List<Card> cardsTobeRemove) {
        playerList.get(currentTurnPlayerIndex).removeNormalCards(cardsTobeRemove);
        // add discarded cards to the discarded card deck
        for (Card card : cardsTobeRemove) {
            deckController.discardCard(card);
        }
    }

    private int getPlayerIndexByAwakenQueenIndex(int index) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).ownQueenCardByIndex(index)) {
                return i;
            }
        }
        return -1;
    }

    private void checkWinCondition() {
        int playerCount = getPlayerCount();
        int queenCardLimit = (playerCount <= 3) ? 5 : 4;
        int queenPointLimit = (playerCount <= 3) ? 50 : 40;

        for (Player player : playerList) {
            int queenCardCount = 0;
            int queenPointCount = 0;

            for (Card card : player.getQueenCards()) {
                if (card != null) {
                    queenCardCount++;
                    queenPointCount += ((QueenCard) card).getPoint();
                }
            }

            if (queenCardCount >= queenCardLimit || queenPointCount >= queenPointLimit) {
                Toast.show((Stage) rootPane.getScene().getWindow(), player.getName() + " wins the game!", toastTimeOut);
                // Handle win logic here, e.g., end the game, show a win screen, etc.
                return;
            }
        }

        if (isAllQueenCardSelected()) {
            playerList.stream()
                    .max(Comparator.comparingInt(player -> Arrays.stream(player.getQueenCards())
                            .filter(Objects::nonNull)
                            .mapToInt(card -> ((QueenCard) card).getPoint())
                            .sum())).ifPresent(highestPointPlayer -> Toast.show((Stage) rootPane.getScene().getWindow(),
                            highestPointPlayer.getName() + " wins the game with the most points!", toastTimeOut));

        }
    }

    private void handlePlayNowButtonClick() {
        ConfirmButtonSound.playButtonClickSound();
        if (isQueenCardSelected) {
            pickQueenCardFromField();
            if (!isQueenCardSelected) {
                endPlayerTurn();
                queenFieldController.setIdleQueenField(true);
            }
            return;
        } else if (isKnightPhase) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Pick opponent queen card from other players", toastTimeOut);
            System.out.println("Pick opponent queen card from sub-player");
            if (selectedAwakenQueenCard == null) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Please pick a queen card to steal", toastTimeOut);
                System.out.println("Please pick a queen card to steal");
                return;
            }
            subPlayerFieldController.setIdle(true);
            isKnightPhase = false;
            // remove selected player queen card effect
            subPlayerFieldController.setCardEffectByIndex(selectedAwakenQueenIndex, null);
            // Enter dragon phase: the targeted player can play Dragon card to defend
            isDragonPhase = true;
            int targetPlayerIndex = getPlayerIndexByAwakenQueenIndex(selectedAwakenQueenIndex);
            Toast.show((Stage) rootPane.getScene().getWindow(), "Player " + playerList.get(targetPlayerIndex).getName() + " can play Dragon card to defend", toastTimeOut);
            nextTurnPlayerIndexForReal = (currentTurnPlayerIndex + 1) % getPlayerCount();
            endPlayerTurn(targetPlayerIndex);
            Toast.show((Stage) rootPane.getScene().getWindow(), "Play a dragon card to protect the queen", toastTimeOut);
            return;
        } else if (isPotionPhase) {
            System.out.println("Pick opponent queen card from sub-player");
            if (selectedAwakenQueenCard == null) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Pick a queen to be fall asleep", toastTimeOut);
                System.out.println("Please pick a queen card to put to sleep");
                return;
            }
            if (selectedQueenCard != null) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Choose an empty place to put the queen to asleep", toastTimeOut);
                return;
            }
            subPlayerFieldController.setIdle(true);
            queenFieldController.setIdleQueenField(true);
            isPotionPhase = false;
            // remove selected player queen card effect
            subPlayerFieldController.setCardEffectByIndex(selectedAwakenQueenIndex, null);
            // remove selected queen card on field effect
            queenFieldController.setCardEffectByIndex(selectedSleepingQueenIndex, null);

            // Enter wand phase: the targeted player can play Wand card to defend
            isWandPhase = true;
            int targetPlayerIndex = getPlayerIndexByAwakenQueenIndex(selectedAwakenQueenIndex);
            nextTurnPlayerIndexForReal = (currentTurnPlayerIndex + 1) % getPlayerCount();
            endPlayerTurn(targetPlayerIndex);
            Toast.show((Stage) rootPane.getScene().getWindow(), "Play a wand card to protect the queen", toastTimeOut);
            return;
        }

        int numberCardsCount = 0;
        List<Integer> chosenCardIndices = mainPlayerCardFieldController.getChosenCardIndexes();
        List<Card> cards = mainPlayerCardFieldController.getChosenCards();

        // unselect all cards after retrieving the chosen cards
        mainPlayerCardFieldController.resetChosenCards();

        if (isDragonPhase) {
            if (cards.size() > 1) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Only one card can be played for Dragon phase", toastTimeOut);
                System.out.println("Invalid number of cards selected for Dragon phase");
                return;
            }
            if (cards.isEmpty()) {  // no card is played
                // The queen will be stolen
                // the stolen player is the previous player of the TRUE next player
                Toast.show((Stage) rootPane.getScene().getWindow(), "The queen has been stolen", toastTimeOut);
                int stolenPlayerIndex = (nextTurnPlayerIndexForReal - 1 + getPlayerCount()) % getPlayerCount();
                playerList.get(stolenPlayerIndex).addQueenCard(selectedAwakenQueenCard);
                playerList.get(currentTurnPlayerIndex).removeQueenCardByIndex(selectedAwakenQueenIndex);
                // rerender
                renderSubPlayer(stolenPlayerIndex, currentSubPlayerIndex.get(stolenPlayerIndex));
                renderMainPlayerQueenCard(currentTurnPlayerIndex);

            } else if (cards.get(0).getType() != CardType.DRAGON) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Only Dragon card can be play", toastTimeOut);
                System.out.println("Invalid card selected for Dragon phase");
                return;
            } else if (cards.get(0).getType() == CardType.DRAGON) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Steal blocked", toastTimeOut);
                // played card is dragon => the queen is defended
                removeCardsFromPlayerDeck(cards);
                replacePlayedCards(chosenCardIndices);
            }
            isDragonPhase = false;
            selectedAwakenQueenIndex = -1;
            selectedAwakenQueenCard = null;
            endPlayerTurn();
            return;
        } else if (isWandPhase) {
            if (cards.size() > 1) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Use 1 Wand card to defend.", toastTimeOut);
                System.out.println("Invalid number of cards selected for Wand phase");
                return;
            }
            if (cards.isEmpty()) {  // no card is played
                // The queen will be put to sleep
                // the stolen player is the previous player of the TRUE next player

                Toast.show((Stage) rootPane.getScene().getWindow(), "The queen has been slept.", toastTimeOut);
                queenFieldController.setQueenCard(selectedSleepingQueenIndex, selectedAwakenQueenCard);
                playerList.get(currentTurnPlayerIndex).removeQueenCardByIndex(selectedAwakenQueenIndex);
                // rerender
                renderMainPlayerQueenCard(currentTurnPlayerIndex);
            } else if (cards.get(0).getType() != CardType.WAND) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Please only use Wand card.", toastTimeOut);
                System.out.println("Invalid card selected for Wand phase");
                return;
            } else if (cards.get(0).getType() == CardType.WAND) {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Defend successfully.", toastTimeOut);
                // played card is wand => the queen is defended
                removeCardsFromPlayerDeck(cards);
                replacePlayedCards(chosenCardIndices);
            }
            isWandPhase = false;
            selectedAwakenQueenIndex = -1;
            selectedSleepingQueenIndex = -1;
            selectedQueenCard = null;
            selectedAwakenQueenCard = null;
            endPlayerTurn();
            return;
        }

        if (cards.isEmpty()) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "No card selected", toastTimeOut);
            return;
        }

        for (Card card : cards) {
            if (card.getType() == CardType.NUMBER) {
                numberCardsCount++;
            }
        }
        if (numberCardsCount == cards.size()) {
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
                removeCardsFromPlayerDeck(cards);
                replacePlayedCards(chosenCardIndices);
                endPlayerTurn();
            } else if (cards.size() == 2) {
                NumberCard firstCard = (NumberCard) cards.get(0);
                NumberCard secondCard = (NumberCard) cards.get(1);
                if (firstCard.GetNumberCardValue() == secondCard.GetNumberCardValue()) {
                    removeCardsFromPlayerDeck(cards);
                    replacePlayedCards(chosenCardIndices);
                    endPlayerTurn();
                } else {
                    Toast.show((Stage) rootPane.getScene().getWindow(), "Invalid Card, Please chose different card.", toastTimeOut);
                }
            } else if (sumOfCards == numberCards.getLast().GetNumberCardValue()) {
                removeCardsFromPlayerDeck(cards);
                replacePlayedCards(chosenCardIndices);
                endPlayerTurn();
            } else {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Invalid Card, Please chose different card.", toastTimeOut);
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
                        removeCardsFromPlayerDeck(cards);
                        JesterLogic(chosenCardIndices);
                        break;
                    case KNIGHT:
                        KnightLogic();
                        removeCardsFromPlayerDeck(cards);
                        replacePlayedCards(chosenCardIndices);
                        break;
                    case POTION:
                        PotionLogic();
                        // Todo Add POTION case logic
                        removeCardsFromPlayerDeck(cards);
                        replacePlayedCards(chosenCardIndices);
                        break;
                    case WAND:
                        WandLogic();
                        // Todo Add Wand case Logic
                        removeCardsFromPlayerDeck(cards);
                        break;
                    case DRAGON:
                        DragonLogic();
                        removeCardsFromPlayerDeck(cards);
                        replacePlayedCards(chosenCardIndices);
                        break;
                }
            } else {
                Toast.show((Stage) rootPane.getScene().getWindow(), "Invalid Card, Please chose different card.", toastTimeOut);
            }
        }
    }

}