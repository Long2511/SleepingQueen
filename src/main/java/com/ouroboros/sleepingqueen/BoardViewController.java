package com.ouroboros.sleepingqueen;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardDeck;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class BoardViewController {
//    @FXML
//    private GridPane centerPlayingBoard;
//
//    @FXML
//    public SplitPane splitPaneLeft;
//
//    @FXML
//    public void initialize() {
//        splitPaneLeft.getStyleClass().add("static-split");
//
//        CardDeck cardDeck = new CardDeck();
//        addCardsToGridPane(centerPlayingBoard, cardDeck);
//
//        for (Node splitPaneDivider : splitPaneLeft.lookupAll(".split-pane-divider")) {
//            splitPaneDivider.setMouseTransparent(true);
//        }
//    }
//
//    private Image getImage(Card card) {
//        return new Image(Objects.requireNonNull(BoardViewController.class.getResourceAsStream(card.getCardImgPath())));
//    }
//
//    private void addCardsToGridPane(GridPane gridPane, CardDeck cardDeck) {
//        int row = 0;
//        int col = 1;
//
//        ImageView imageView = new ImageView(getImage(cardDeck.draw()));
//        imageView.setFitWidth(100);
//        imageView.setFitHeight(150);
//        GridPane.setHalignment(imageView, HPos.CENTER);
//        GridPane.setValignment(imageView, VPos.CENTER);
//
//        gridPane.add(imageView, col, row);
//    }
}