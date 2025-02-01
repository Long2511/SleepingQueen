/**
 * BoardViewController.java
 * Java class that controls the game board view
 * It handles the game logic and the interaction between the player and the game board
 * It also handles the card selection logic and the card playing logi cand the player win condition
 * It also handles the card selection logic and the card playing logic for each card type (King, Jester, Knight, Potion, Wand, Dragon) and Number card type
 *
 * @author Hai Long Mac
 * @author Phuoc Thanh Nguyen
 */

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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
    private Stack<Integer> nextTurnPlayerIndexStack;  // a stack to store the next player index
    private SubPlayerFieldController subPlayerFieldController;
    private DeckController deckController;
    private MainPlayerCardField mainPlayerCardFieldController;

    private List<Integer> currentSubPlayerIndex;

    private int selectedSleepingQueenIndex;
    private Card selectedSleepingQueenCard;

    private int selectedAwakenQueenIndex;
    private Card selectedAwakenQueenCard;

    private PhaseType currentPhase;

    private DropShadow choosingEffect;
    private MediaPlayer mediaPlayer;

    /**
     * Set the player count
     *
     * @return player count (i.e. the number of players)
     */
    public static int getPlayerCount() {
        return Integer.parseInt(playerCount);
    }

    /**
     * Set the player count
     *
     * @param count player count
     */
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

        initializePhase();
        setUpPlayer();
        setupEffect();

        // add event listener for queen card selection
        queenFieldController.setOnQueenCardSelected(this::handleSleepingQueenCardSelection);
        subPlayerFieldController.setOnAwakenQueenCardSelected(this::handleAwakenQueenCardSelection);
    }

    /**
     * Set up the effect for selecting card
     */
    private void setupEffect() {
        choosingEffect = new DropShadow();
        choosingEffect.setBlurType(BlurType.TWO_PASS_BOX);
        choosingEffect.setRadius(10.0);
        choosingEffect.setSpread(0.4);
        choosingEffect.setColor(Color.web("#37ff00"));
    }

    /**
     * Initialize the phase
     */
    private void initializePhase() {
        selectedAwakenQueenIndex = -1;  // no awaken queen card selected
        selectedAwakenQueenCard = null;
        selectedSleepingQueenIndex = -1;  // no queen card selected
        selectedSleepingQueenCard = null;

        // Everything is idle at the beginning
        queenFieldController.setIdleQueenField(true);
        subPlayerFieldController.setIdle(true);
        mainPlayerCardFieldController.setIdle(true);

        // start the first phase
        currentPhase = PhaseType.NORMAL_TURN;
        startCurrentPhase();
    }

    private void setIdleCurrentPhase(boolean isIdle) {
        switch (currentPhase) {
            case PICK_SLEEPING_QUEEN:
                queenFieldController.setIdleQueenField(isIdle);
                break;
            case KNIGHT_ATTACK:
                subPlayerFieldController.setIdle(isIdle);
                break;
            case KNIGHT_DEFEND, NORMAL_TURN, WAND_DEFEND:
                mainPlayerCardFieldController.setIdle(isIdle);
                break;
            case POTION_ATTACK:
                subPlayerFieldController.setIdle(isIdle);
                queenFieldController.setIdleQueenField(isIdle);
                break;
        }
    }

    private void startCurrentPhase() {
        setIdleCurrentPhase(false);
    }

    private void endCurrentPhase() {
        setIdleCurrentPhase(true);
    }

    /**
     * Set up the player
     */
    private void setUpPlayer() {
        playerList = new ArrayList<>();
        currentSubPlayerIndex = new ArrayList<>(getPlayerCount());

        int awakenQueenHolderIndex = 1;
        for (int i = 0; i < getPlayerCount(); i++) {
            // currentSubPlayerIndex is used to store the index of the sub player holder
            // the sub player will be rendered in. (0, 1, 2, 3, 4)
            // currentSubPlayerIndex[i] = -1 if the player is the main player
            currentSubPlayerIndex.add(i - 1);

            Player player = new Player("Player " + (i + 1));

            // Draw 5 cards for each player
            for (int j = 0; j < player.getMAX_NORMAL_CARDS(); j++) {
                player.setNormalCard(j, deckController.drawCard());
            }
            // Player has no queen card at the beginning
            for (int j = 0; j < player.getMAX_QUEEN_CARDS(); j++) {
                player.setQueenCard(j, null);
                // Queen Index is used to define which queen card is picked to steal/put to sleep
                player.setQueenIndex(j, awakenQueenHolderIndex);
                awakenQueenHolderIndex += 1;
            }
            playerList.add(player);
        }
        // Player 1 makes a move first
        currentTurnPlayerIndex = 0;
        nextTurnPlayerIndexStack = new Stack<>();
        nextTurnPlayerIndexStack.push(-1);  // -1 means no constraint about the next player
        setUpPlayerTurn();
    }

    /**
     * Set up the player turn
     */
    private void setUpPlayerTurn() {
        // render main player
        // load cards of the CurrentTurnPlayer to the main player card field
        renderMainPlayerNormalCard(currentTurnPlayerIndex);
        renderMainPlayerQueenCard(currentTurnPlayerIndex);
        MainPlayer.setText(playerList.get(currentTurnPlayerIndex).getName());

        // render sub players
        for (int i = 0; i < getPlayerCount(); i++) {
            if (i != currentTurnPlayerIndex) {
                renderSubPlayer(i);
            }
        }
    }

    /**
     * Render the main player normal card
     *
     * @param playerIndex index of the player that will be rendered
     */
    private void renderMainPlayerNormalCard(int playerIndex) {
        mainPlayerCardFieldController.setCard(playerList.get(playerIndex).getNormalCards());
    }

    /**
     * Render the main player queen card
     *
     * @param playerIndex index of the player that will be rendered
     */
    private void renderMainPlayerQueenCard(int playerIndex) {
        mainPlayerQueenFieldController.setCard(playerList.get(playerIndex).getQueenCards());
    }

    /**
     * Render the main player
     *
     * @param playerIndex index of the player
     */
    private void renderMainPlayer(int playerIndex) {
        renderMainPlayerNormalCard(playerIndex);
        renderMainPlayerQueenCard(playerIndex);
        MainPlayer.setText(playerList.get(currentTurnPlayerIndex).getName());
    }

    /**
     * Render the sub player
     *
     * @param playerIndex index of the player
     */
    private void renderSubPlayer(int playerIndex) {
        subPlayerFieldController.setPlayer(currentSubPlayerIndex.get(playerIndex), playerList.get(playerIndex));
    }

    /**
     * Handle the card selection logic
     *
     * @param index index of the selected card
     */
    public void handleSleepingQueenCardSelection(int index) {
        if (selectedSleepingQueenIndex != -1) {
            // reselect -> remove effect of the old card
            queenFieldController.setCardEffectByIndex(selectedSleepingQueenIndex, null);
        }
        selectedSleepingQueenIndex = index;
        selectedSleepingQueenCard = queenFieldController.getQueenCard(index);
        // add effect to the selected card
        queenFieldController.setCardEffectByIndex(selectedSleepingQueenIndex, choosingEffect);
    }

    /**
     * Handle the awaken queen card selection logic
     */
    public void handleAwakenQueenCardSelection(int index) {
        if (selectedAwakenQueenIndex != -1) {
            // reselect -> remove effect of the old card
            subPlayerFieldController.setCardEffectByIndex(selectedAwakenQueenIndex, null);
        }
        selectedAwakenQueenIndex = index;
        selectedAwakenQueenCard = getAwakenQueenCard(index);
        // add effect to the selected card
        subPlayerFieldController.setCardEffectByIndex(selectedAwakenQueenIndex, choosingEffect);
    }

    /**
     * Get awaken queen card from player by index
     */
    private Card getAwakenQueenCard(int index) {
        for (Player player : playerList) {
            if (player.ownQueenCardByIndex(index)) {
                return player.getQueenCardByIndex(index);
            }
        }
        return null;
    }

    /**
     * Check if all queen cards are selected
     *
     * @return true if all queen cards are selected
     */
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

    /**
     * end the turn of the currentTurnPlayerIndex
     *
     * @param nextTurnPlayerIndex index of the next turn player
     */
    private void endPlayerTurn(int nextTurnPlayerIndex) {
        if (currentTurnPlayerIndex != nextTurnPlayerIndex) {  // next player is different from the current player => end turn
            Toast.show((Stage) rootPane.getScene().getWindow(), playerList.get(currentTurnPlayerIndex).getName() + " has ended the turn", toastTimeOut);
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(rootPane.getScene() != null ? 2 : 0));

        pause.setOnFinished(event -> {
            //Check for win condition
            checkWinCondition();

            if (nextTurnPlayerIndex != currentTurnPlayerIndex) {  // swap only when change main player
                // Swap currentSubPlayerIndex between current player and next player
                int temp = currentSubPlayerIndex.get(currentTurnPlayerIndex);
                currentSubPlayerIndex.set(currentTurnPlayerIndex, currentSubPlayerIndex.get(nextTurnPlayerIndex));
                currentSubPlayerIndex.set(nextTurnPlayerIndex, temp);

                // render the new sub player
                renderSubPlayer(currentTurnPlayerIndex);
                // update currentTurnPlayerIndex as the nextTurnPlayerIndex
                currentTurnPlayerIndex = nextTurnPlayerIndex;
                renderMainPlayer(currentTurnPlayerIndex);
                Toast.show((Stage) rootPane.getScene().getWindow(), playerList.get(currentTurnPlayerIndex).getName() + " turn.", toastTimeOut);
            } else {
                Toast.show((Stage) rootPane.getScene().getWindow(), playerList.get(currentTurnPlayerIndex).getName() + " continue to play", toastTimeOut);
            }
            startCurrentPhase();
        });
        // Delay for 2 seconds before next player turn
        pause.play();
    }

    /**
     * End the player turn
     * using nextTurnPlayerIndexForReal if it's not -1
     */
    private void endPlayerTurn() {
        if (nextTurnPlayerIndexStack.peek() == -1) {
            // There is no constraint about the next player
            // => the next player is the player after the current player in the list
            endPlayerTurn((currentTurnPlayerIndex + 1) % getPlayerCount());
        } else {
            // pop the next player from the stack to use for the next turn
            endPlayerTurn(nextTurnPlayerIndexStack.pop());
        }
    }

    /**
     * Replace the played cards with the new card
     *
     * @param chosenCardIndices indices of the chosen cards
     */
    private void replacePlayedCards(List<Integer> chosenCardIndices) {
        for (int index : chosenCardIndices) {
            Card card = deckController.drawCard();
            playerList.get(currentTurnPlayerIndex).setNormalCard(index, card);

            mainPlayerCardFieldController.setCard(index, card);
        }
    }

    /**
     * Replace the played cards with the new card
     *
     * @param chosenCardIndices indices of the chosen cards
     * @param card              new card to be replaced
     */
    private void replacePlayedCards(List<Integer> chosenCardIndices, Card card) {
        for (int index : chosenCardIndices) {
            playerList.get(currentTurnPlayerIndex).setNormalCard(index, card);
            mainPlayerCardFieldController.setCard(index, card);
        }
    }

    /**
     * remove cards from players
     *
     * @param chosenIndexes indices of the chosen cards
     */
    private void removeCardsFromPlayerDeck(List<Integer> chosenIndexes) {
        for (int index : chosenIndexes) {
            deckController.discardCard(playerList.get(currentTurnPlayerIndex).getNormalCard(index));
            playerList.get(currentTurnPlayerIndex).removeNormalCard(index);
        }
    }

    /**
     * @param index index of the awaken queen card
     * @return position of the queen card in the player's queen card list
     */
    private int getPlayerIndexByAwakenQueenIndex(int index) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).ownQueenCardByIndex(index)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Check the win condition and show the winning screen
     */
    private void checkWinCondition() {
        int playerCount = getPlayerCount();
        int queenCardLimit = (playerCount <= 3) ? 5 : 4;
        int queenPointLimit = (playerCount <= 3) ? 50 : 40;
        int totalQueenCard = 0;

        for (Player player : playerList) {
            int queenCardCount = 0;
            int queenPointCount = 0;

            for (Card card : player.getQueenCards()) {
                if (card != null) {
                    queenCardCount++;
                    queenPointCount += ((QueenCard) card).getPoint();
                }
            }
            totalQueenCard += queenCardCount;

            if (queenCardCount >= queenCardLimit || queenPointCount >= queenPointLimit) {
                showWinningScreen(player.getName());
                return;
            }
        }
        if (totalQueenCard < queenFieldController.numberOfAwakenQueens()) {
            // must be the current player has just picked up the 5th queens
            // => The current player is the winner
            showWinningScreen(playerList.get(currentTurnPlayerIndex).getName());
        }

        if (isAllQueenCardSelected()) {
            playerList.stream()
                    .max(Comparator.comparingInt(player -> Arrays.stream(player.getQueenCards())
                            .filter(Objects::nonNull)
                            .mapToInt(card -> ((QueenCard) card).getPoint())
                            .sum())).ifPresent(highestPointPlayer -> showWinningScreen(highestPointPlayer.getName()));

        }
    }

    /**
     * Show the winning screen
     *
     * @param playerName name of the winning player
     */
    private void showWinningScreen(String playerName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/winning-screen.fxml"));
            Scene scene = new Scene(loader.load());
            WinningScreenController controller = loader.getController();
            controller.setWinningPlayerName(playerName);

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetSelectedSleepingQuen() {
        queenFieldController.setCardEffectByIndex(selectedSleepingQueenIndex, null);
        selectedSleepingQueenCard = null;
        selectedSleepingQueenIndex = -1;
    }

    private void resetSelectedAwakenQueen() {
        subPlayerFieldController.setCardEffectByIndex(selectedAwakenQueenIndex, null);
        selectedAwakenQueenCard = null;
        selectedAwakenQueenIndex = -1;
    }

    private boolean hasQueenByName(String queenName) {
        for (Card card : playerList.get(currentTurnPlayerIndex).getQueenCards()) {
            if (card != null && card.getCardName().equals(queenName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * process the special queens: Rose queen, Dog queen, Cat queen
     */
    private void processSpecialQueen() {
        boolean addQueen = true;
        boolean isRoseQueen = selectedSleepingQueenCard.getCardName().equals(SPECIAL_QUEENS[0]);
        Toast.show((Stage) rootPane.getScene().getWindow(), "Selected Queen: " + selectedSleepingQueenCard.getCardName(), toastTimeOut);

        if (selectedSleepingQueenCard.getCardName().equals(SPECIAL_QUEENS[1])) {
            // Dog Queen - check if player has Cat Queen
            if (hasQueenByName(SPECIAL_QUEENS[2])) {
                // Player has Cat Queen - cannot have the same Dog & Cat at the same time => Put back Dog Queen to the field
                Toast.show((Stage) rootPane.getScene().getWindow(), "Dog Queen and Cat Queen cannot be pick together", toastTimeOut);
                addQueen = false;
            }
        } else if (selectedSleepingQueenCard.getCardName().equals(SPECIAL_QUEENS[2])) {
            // Cat Queen - check if player has Dog Queen
            if (hasQueenByName(SPECIAL_QUEENS[1])) {
                // Player has Dog Queen - cannot have the same Dog & Cat at the same time => Put back Cat Queen to the field
                Toast.show((Stage) rootPane.getScene().getWindow(), "Dog Queen and Cat Queen cannot be pick together", toastTimeOut);
                addQueen = false;
            }
        }
        // Add queen: add to player's queen card and remove from the field
        if (addQueen) {
            playerList.get(currentTurnPlayerIndex).addQueenCard(selectedSleepingQueenCard);
            queenFieldController.removeQueenFromField(selectedSleepingQueenCard);
            // rerender the new queen card on main player queen card
            renderMainPlayerQueenCard(currentTurnPlayerIndex);
        }

        if (isRoseQueen && !isAllQueenCardSelected()) {
            // Rose Queen - player can pick another queen
            // Do not change phase
            // do not change player
            nextTurnPlayerIndexStack.push(currentTurnPlayerIndex);
            Toast.show((Stage) rootPane.getScene().getWindow(), "Picked Rose Queen, please select another Queen", toastTimeOut);
        } else if (!isAllQueenCardSelected()) {
            // End phase
            endCurrentPhase();
            // Move to next phase
            currentPhase = PhaseType.NORMAL_TURN;
        }

        // Erase data once done processing
        resetSelectedSleepingQuen();
    }

    private void handlePickSleepingQueenPhase() {
        if (selectedSleepingQueenCard == null) {
            // request reselect
            Toast.show((Stage) rootPane.getScene().getWindow(), "Please select a valid queen card first!", toastTimeOut);
            return;
        }
        processSpecialQueen();
        endPlayerTurn();
    }

    private void handleKnightAttackPhase() {
        if (selectedAwakenQueenCard == null) {
            // request reselect
            Toast.show((Stage) rootPane.getScene().getWindow(), "Please pick a queen card to steal", toastTimeOut);
            return;
        }

        if (selectedAwakenQueenCard.getCardName().equals(SPECIAL_QUEENS[1])) {
            // Dog Queen
            if (hasQueenByName(SPECIAL_QUEENS[2])) {
                // has Cat Queen
                Toast.show((Stage) rootPane.getScene().getWindow(), "Dog Queen and Cat Queen cannot be pick together", toastTimeOut);
                return;
            }
        }
        if (selectedAwakenQueenCard.getCardName().equals(SPECIAL_QUEENS[2])) {
            // Cat Queen
            if (hasQueenByName(SPECIAL_QUEENS[1])) {
                // has Dog Queen
                Toast.show((Stage) rootPane.getScene().getWindow(), "Dog Queen and Cat Queen cannot be pick together", toastTimeOut);
                return;
            }
        }

        Toast.show((Stage) rootPane.getScene().getWindow(), playerList.get(currentTurnPlayerIndex).getName() + " want to steal " + selectedAwakenQueenCard.getCardName(), toastTimeOut);
        endCurrentPhase();

        // remove effect only. Keep the selected awaken queen for defending phase
        subPlayerFieldController.setCardEffectByIndex(selectedAwakenQueenIndex, null);

        // Enter Knight Defend phase: the targeted player can play Dragon card to defend
        currentPhase = PhaseType.KNIGHT_DEFEND;
        int targetPlayerIndex = getPlayerIndexByAwakenQueenIndex(selectedAwakenQueenIndex);

        // Save next turn player index to return to the original flow once the defend phase is done
        nextTurnPlayerIndexStack.push((currentTurnPlayerIndex + 1) % getPlayerCount());

        // Let the target player defend
        endPlayerTurn(targetPlayerIndex);
        Toast.show((Stage) rootPane.getScene().getWindow(), "Play a dragon card to protect the queen", toastTimeOut);
    }

    private void handlePotionAttackPhase() {
        if (selectedAwakenQueenCard == null) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Pick a queen to be fall asleep", toastTimeOut);
            return;
        }
        if (selectedSleepingQueenIndex == -1 || selectedSleepingQueenCard != null) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Choose an empty place to put the queen to asleep", toastTimeOut);
            return;
        }

        Toast.show((Stage) rootPane.getScene().getWindow(), playerList.get(currentTurnPlayerIndex).getName() + " want to put " + selectedAwakenQueenCard.getCardName() + " to fall asleep", toastTimeOut);
        endCurrentPhase();

        // remove selected player queen card effect
        subPlayerFieldController.setCardEffectByIndex(selectedAwakenQueenIndex, null);
        // remove selected queen card on field effect
        queenFieldController.setCardEffectByIndex(selectedSleepingQueenIndex, null);

        // Enter Potion Defend phase: the targeted player can play Wand card to defend
        currentPhase = PhaseType.WAND_DEFEND;
        int targetPlayerIndex = getPlayerIndexByAwakenQueenIndex(selectedAwakenQueenIndex);

        // Save next turn player index to return to the original flow once the defend phase is done
        nextTurnPlayerIndexStack.push((currentTurnPlayerIndex + 1) % getPlayerCount());

        // let the target player defend
        endPlayerTurn(targetPlayerIndex);
        Toast.show((Stage) rootPane.getScene().getWindow(), "Play a wand card to protect the queen", toastTimeOut);
    }

    private void handleKnightDefendPhase(List<Integer> chosenCardIndices, List<Card> cards) {
        if (cards.size() > 1) {
            // request reselect
            Toast.show((Stage) rootPane.getScene().getWindow(), "Please select 1 card only", toastTimeOut);
            return;
        }
        if (cards.size() == 1 && cards.getFirst().getType() != CardType.DRAGON) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Only Dragon card can be play", toastTimeOut);
            return;
        }
        // End phase
        endCurrentPhase();

        if (cards.isEmpty()) {  // no card is played
            // The queen will be stolen
            // the stolen player is the previous player of the TRUE next player
            Toast.show((Stage) rootPane.getScene().getWindow(), "The queen has been stolen", toastTimeOut);
            int stolenPlayerIndex = (nextTurnPlayerIndexStack.peek() - 1 + getPlayerCount()) % getPlayerCount();
            playerList.get(currentTurnPlayerIndex).removeQueenCardByIndex(selectedAwakenQueenIndex);
            playerList.get(stolenPlayerIndex).addQueenCard(selectedAwakenQueenCard);
            // rerender
            renderSubPlayer(stolenPlayerIndex);
            renderMainPlayerQueenCard(currentTurnPlayerIndex);
        } else if (cards.getFirst().getType() == CardType.DRAGON) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Steal blocked", toastTimeOut);
            // played card is dragon => the queen is defended
            // refill the player's card
            removeCardsFromPlayerDeck(chosenCardIndices);
            replacePlayedCards(chosenCardIndices);
        }
        currentPhase = PhaseType.NORMAL_TURN;

        // Erase data once done processing
        resetSelectedAwakenQueen();
        endPlayerTurn();
    }

    private void handlePotionDefendPhase(List<Integer> chosenCardIndices, List<Card> cards) {
        if (cards.size() > 1) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Use 1 Wand card to defend.", toastTimeOut);
            return;
        }
        if (cards.size() == 1 && cards.getFirst().getType() != CardType.WAND) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Please only use Wand card.", toastTimeOut);
            return;
        }
        // End phase
        endCurrentPhase();

        if (cards.isEmpty()) {  // no card is played
            // The queen will be put to sleep
            // the stolen player is the previous player of the TRUE next player
            Toast.show((Stage) rootPane.getScene().getWindow(), "The queen has been slept.", toastTimeOut);
            queenFieldController.setQueenCard(selectedSleepingQueenIndex, selectedAwakenQueenCard);
            playerList.get(currentTurnPlayerIndex).removeQueenCardByIndex(selectedAwakenQueenIndex);
            // rerender
            renderMainPlayerQueenCard(currentTurnPlayerIndex);
        } else if (cards.getFirst().getType() == CardType.WAND) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "Defend successfully.", toastTimeOut);
            // played card is wand => the queen is defended
            // refill the player's card
            removeCardsFromPlayerDeck(chosenCardIndices);
            replacePlayedCards(chosenCardIndices);
        }
        currentPhase = PhaseType.NORMAL_TURN;

        // Erase data once done processing
        resetSelectedAwakenQueen();
        resetSelectedSleepingQuen();
        endPlayerTurn();
    }

    /**
     * Handle the play now button click and check the playing logic
     */
    private void handlePlayNowButtonClick() {
        ConfirmButtonSound.playButtonClickSound();
        if (currentPhase == PhaseType.PICK_SLEEPING_QUEEN) {
            handlePickSleepingQueenPhase();
            return;
        } else if (currentPhase == PhaseType.KNIGHT_ATTACK) {
            handleKnightAttackPhase();
            return;
        } else if (currentPhase == PhaseType.POTION_ATTACK) {
            handlePotionAttackPhase();
            return;
        }

        List<Integer> chosenCardIndices = mainPlayerCardFieldController.getChosenCardIndexes();
        List<Card> cards = mainPlayerCardFieldController.getChosenCards();

        // unselect all cards after retrieving the chosen cards
        mainPlayerCardFieldController.resetChosenCards();

        if (currentPhase == PhaseType.KNIGHT_DEFEND) {
            handleKnightDefendPhase(chosenCardIndices, cards);
            return;
        } else if (currentPhase == PhaseType.WAND_DEFEND) {
            handlePotionDefendPhase(chosenCardIndices, cards);
            return;
        }

        // Current phase is NORMAL_TURN
        if (cards.isEmpty()) {
            Toast.show((Stage) rootPane.getScene().getWindow(), "No card selected", toastTimeOut);
            return;
        }
        if (cards.size() > 1) {
            // multiple cards are allowed only for number cards
            if (isValidNumberCards(chosenCardIndices, cards)) {
                // valid number cards
                endCurrentPhase();
                removeCardsFromPlayerDeck(chosenCardIndices);
                replacePlayedCards(chosenCardIndices);
                // continue the NORMAL_TURN phase with the next player
                endPlayerTurn();
                return;
            } else {
                // request reselect
                Toast.show((Stage) rootPane.getScene().getWindow(), "Invalid Card, Please chose different card.", toastTimeOut);
                return;
            }
        }
        // Only 1 card is played => all options are valid
        endCurrentPhase();

        if (cards.getFirst().getType() == CardType.JESTER) {  // Jester is a special case
            removeCardsFromPlayerDeck(chosenCardIndices);
            JesterLogic(chosenCardIndices);
            return;
        }

        //Handle logic for each card type
        switch (cards.getFirst().getType()) {
            case KING:
                KingLogic();
                break;
            case KNIGHT:
                KnightLogic();
                break;
            case POTION:
                PotionLogic();
                break;
            case WAND:
                // Play wand card alone do not affect the game
                WandLogic();
                break;
            case DRAGON:
                // Play dragon card alone do not affect the game
                DragonLogic();
                break;
        }
        removeCardsFromPlayerDeck(chosenCardIndices);
        replacePlayedCards(chosenCardIndices);
        endPlayerTurn();
    }

    /**
     * Number card logic
     *
     * @param chosenCardIndices indices of the chosen cards
     * @param cards             chosen cards
     * @return true if the cards can be played, false otherwise
     */
    private boolean isValidNumberCards(List<Integer> chosenCardIndices, List<Card> cards) {
        int numberCardsCount = 0;
        for (Card card : cards) {
            if (card.getType() == CardType.NUMBER) {
                numberCardsCount++;
            }
        }
        if (numberCardsCount != cards.size()) {  // all cards must be number cards
            return false;
        }

        int sumOfCards = 0;
        // Filter and collect only NumberCard objects and sort numbers
        List<NumberCard> numberCards = cards.stream()
                .filter(card -> card instanceof NumberCard)
                .map(card -> (NumberCard) card).sorted(Comparator
                        .comparingInt(NumberCard::GetNumberCardValue))
                .toList();
        for (int i = 0; i < numberCards.size() - 1; i++) {
            sumOfCards += numberCards.get(i).GetNumberCardValue();
        }

        //Check the sum of number cards is equal to the last card
        if (cards.size() == 2) {
            // Can play a pair of number cards with the same value
            NumberCard firstCard = (NumberCard) cards.get(0);
            NumberCard secondCard = (NumberCard) cards.get(1);
            if (firstCard.GetNumberCardValue() == secondCard.GetNumberCardValue()) {
                return true;
            }
        } else if (sumOfCards == numberCards.getLast().GetNumberCardValue()) {
            // Can play 3 or more number cards, if there is 1 card
            // that is equal to sum of the rest of the cards
            return true;
        }
        return false;
    }


    /**
     * Handle the card selection logic
     */
    private void DragonLogic() {
        Toast.show((Stage) rootPane.getScene().getWindow(), "Play wand dragon alone do not have any effect", toastTimeOut);
    }

    /**
     * Wand card logic
     */
    private void WandLogic() {
        Toast.show((Stage) rootPane.getScene().getWindow(), "Play wand card alone do not have any effect", toastTimeOut);
    }

    /**
     * King card logic
     */
    private void KingLogic() {
        currentPhase = PhaseType.PICK_SLEEPING_QUEEN;
        // Player can select a queen card
        nextTurnPlayerIndexStack.push(currentTurnPlayerIndex);
        Toast.show((Stage) rootPane.getScene().getWindow(), "Please select a Queen card and confirm", toastTimeOut);
    }

    /**
     * @return True if there is a Queen card to put to asleep, False otherwise
     */
    private boolean hasAwakenQueen() {
        for (int i = 0; i < getPlayerCount(); i++)
            if (i != currentTurnPlayerIndex) {
                for (Card card : playerList.get(i).getQueenCards()) {
                    if (card != null) {
                        return true;
                    }
                }
            }
        return false;
    }

    private boolean isStealable() {
        for (int i = 0; i < getPlayerCount(); i++) {
            if (i != currentTurnPlayerIndex) {
                for (Card card : playerList.get(i).getQueenCards()) {
                    if (card != null) {
                        if (card.getCardName().equals(SPECIAL_QUEENS[1])) {  // Dog queen
                            if (hasQueenByName(SPECIAL_QUEENS[2])) {
                                continue;
                            }
                        }
                        if (card.getCardName().equals(SPECIAL_QUEENS[2])) {  // Cat queen
                            if (hasQueenByName(SPECIAL_QUEENS[1])) {
                                continue;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Knight card logic
     * Player can select opponent queen
     */
    private void KnightLogic() {
        if (!isStealable()) {
            // There is non queen to steal
            Toast.show((Stage) rootPane.getScene().getWindow(), "No queen to steal", toastTimeOut);
            return;
        }
        currentPhase = PhaseType.KNIGHT_ATTACK;
        // Player can select opponent queen
        nextTurnPlayerIndexStack.push(currentTurnPlayerIndex);
        Toast.show((Stage) rootPane.getScene().getWindow(), "Select Queen Card from other players", toastTimeOut);
    }

    /**
     * Potion card logic
     */
    private void PotionLogic() {
        if (!hasAwakenQueen()) {
            // There is non queen to put to sleep
            Toast.show((Stage) rootPane.getScene().getWindow(), "No queen to put to sleep", toastTimeOut);
            return;
        }
        currentPhase = PhaseType.POTION_ATTACK;
        // Player can select opponent queen
        nextTurnPlayerIndexStack.push(currentTurnPlayerIndex);

        Toast.show((Stage) rootPane.getScene().getWindow(), "Pick opponent queen card from other players", toastTimeOut);
        Toast.show((Stage) rootPane.getScene().getWindow(), "Pick position for the sleeping queen", toastTimeOut);
    }

    /**
     * Jester card logic
     *
     * @param chosenCardIndices indices of the chosen cards
     */
    private void JesterLogic(List<Integer> chosenCardIndices) {
        // Usage of Jester card depends on the drawn card
        Card drawnCard = deckController.drawCard();

        if (drawnCard.getType() == CardType.NUMBER) {
            // if draw card is number card, a player will be able to awake a queen
            replacePlayedCards(chosenCardIndices, deckController.drawCard());

            // get player granted the privilege to awake a queen
            int indexOffset = ((NumberCard) drawnCard).GetNumberCardValue() % getPlayerCount() - 1;
            indexOffset = (indexOffset + getPlayerCount()) % getPlayerCount();  // make sure it's positive
            int nextTurnPlayerIndex = (currentTurnPlayerIndex + indexOffset) % getPlayerCount();
            Toast.show((Stage) rootPane.getScene().getWindow(), "Drawn card is a Number card " + ((NumberCard) drawnCard).GetNumberCardValue(), toastTimeOut);
            Toast.show((Stage) rootPane.getScene().getWindow(), playerList.get(nextTurnPlayerIndex).getName() + " can awake a queen", toastTimeOut);

            nextTurnPlayerIndexStack.push((currentTurnPlayerIndex + 1) % getPlayerCount());

            // Set up pick queen phase for the player granted the privilege
            currentPhase = PhaseType.PICK_SLEEPING_QUEEN;
            endPlayerTurn(nextTurnPlayerIndex);
            deckController.discardCard(drawnCard);
            return;
        }
        // if draw card is function card, player will get another turn
        Toast.show((Stage) rootPane.getScene().getWindow(), "Drawn card is a Function card, player gets another turn", toastTimeOut);
        replacePlayedCards(chosenCardIndices, drawnCard);
        // Player gets another turn
        endPlayerTurn(currentTurnPlayerIndex);
    }

}