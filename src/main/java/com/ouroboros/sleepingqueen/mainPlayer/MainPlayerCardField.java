/**
 * @author Thanh Phuoc Nguyen - 1584468
 * @author Hai Long Mac - 1534413
 */

package com.ouroboros.sleepingqueen.mainPlayer;

import com.ouroboros.sleepingqueen.card.CardController;
import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the main player's card field.
 * Handles card selection, visual effects, and interactions.
 */
public class MainPlayerCardField {
    private final int NUMBER_OF_CARD = 5;
    boolean isChosen[] = new boolean[NUMBER_OF_CARD];

    @FXML
    private VBox MainCard1Box;
    @FXML
    private VBox MainCard2Box;
    @FXML
    private VBox MainCard3Box;
    @FXML
    private VBox MainCard4Box;
    @FXML
    private VBox MainCard5Box;

    private List<CardController> cardControllers;
    private DropShadow choosingEffect;

    /**
     * Initializes the card field, loads cards, and sets up effects.
     */
    public void initialize() {
        cardControllers = new ArrayList<>();
        loadCard(MainCard1Box);
        loadCard(MainCard2Box);
        loadCard(MainCard3Box);
        loadCard(MainCard4Box);
        loadCard(MainCard5Box);

        for (int i = 0; i < NUMBER_OF_CARD; i++) {
            CardController cardController = cardControllers.get(i);
            isChosen[i] = false;
            cardController.setIndex(i);
            cardController.setFaceup(true);
            cardController.setIdle(false);
            cardController.setOnCardSelected(this::handleCardSelected);
        }

        choosingEffect = new DropShadow();
        choosingEffect.setBlurType(BlurType.TWO_PASS_BOX);
        choosingEffect.setRadius(10.0);
        choosingEffect.setSpread(1.0);
        choosingEffect.setColor(Color.web("#37ff00"));
    }

    /**
     * Loads a card into a given VBox container.
     *
     * @param CardBox The VBox where the card will be added.
     */
    private void loadCard(VBox CardBox) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/mainPlayerView/main-player-card.fxml"));
            CardBox.getChildren().add((VBox) loader.load());
            cardControllers.add(loader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles card selection and applies visual effects.
     *
     * @param index The index of the selected card.
     */
    public void handleCardSelected(int index) {
        if (isChosen[index]) {
            isChosen[index] = false;
            cardControllers.get(index).setCardEffect(null);
        } else {
            isChosen[index] = true;
            cardControllers.get(index).setCardEffect(choosingEffect);
        }
    }

    /**
     * Retrieves the list of selected cards.
     *
     * @return A list of selected cards.
     */
    public List<Card> getChosenCards() {
        List<Card> chosenCards = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CARD; i++) {
            if (isChosen[i]) {
                chosenCards.add(cardControllers.get(i).getCard());
            }
        }
        return chosenCards;
    }

    /**
     * Retrieves the indexes of selected cards.
     *
     * @return A list of selected card indexes.
     */
    public List<Integer> getChosenCardIndexes() {
        List<Integer> chosenCardIndexes = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CARD; i++) {
            if (isChosen[i]) {
                chosenCardIndexes.add(i);
            }
        }
        return chosenCardIndexes;
    }

    /**
     * Resets all selected cards.
     */
    public void resetChosenCards() {
        for (int i = 0; i < NUMBER_OF_CARD; i++) {
            isChosen[i] = false;
            cardControllers.get(i).setCardEffect(null);
        }
    }

    /**
     * Sets multiple cards at once.
     *
     * @param cards Array of cards to be set.
     */
    public void setCard(Card[] cards) {
        for (int i = 0; i < cards.length; i++) {
            cardControllers.get(i).setCard(cards[i]);
        }
    }

    /**
     * Sets a specific card at a given index.
     *
     * @param index The index of the card.
     * @param card  The card to be set.
     */
    public void setCard(int index, Card card) {
        cardControllers.get(index).setCard(card);
    }

    /**
     * Sets all cards to idle or active state.
     *
     * @param idle Boolean value to set idle state.
     */
    public void setIdle(boolean idle) {
        for (CardController cardController : cardControllers) {
            cardController.setIdle(idle);
        }
    }
}
