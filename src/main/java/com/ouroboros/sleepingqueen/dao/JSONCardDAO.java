package com.ouroboros.sleepingqueen.dao;


import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.cardcollection.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JSONCardDAO implements CardDAO {

    private final String NORMAL_CARD_DATA_PATH = "/com/ouroboros/sleepingqueen/cardData/normalCardData.json";
    private final String QUEEN_CARD_DATA_PATH = "/com/ouroboros/sleepingqueen/cardData/queenCardData.json";

    List<Card> queenCardList;
    List<Card> normalCardList; // List of all cards EXCEPT Queen Cards

    public JSONCardDAO() {
        queenCardList = new ArrayList<Card>();
        normalCardList = new ArrayList<Card>();
        readFromJSON(queenCardList, QUEEN_CARD_DATA_PATH);
        readFromJSON(normalCardList, NORMAL_CARD_DATA_PATH);
    }

    public void readFromJSON(List<Card> cardList, String path) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(getClass().getResource(path).toURI().getPath())) {
            JSONObject cardData = (JSONObject) parser.parse(reader);
            JSONArray cards = (JSONArray) cardData.get("cards");
            String backImage = (String) cardData.get("backImage");

            for (Object card : cards) {
                JSONObject cardObject = (JSONObject) card;
                String name = (String) cardObject.get("cardName");
                int quantity = (int) (long) cardObject.get("quantity");
                String description = (String) cardObject.get("cardDescription");
                String image = (String) cardObject.get("cardImage");
                String cardType = (String) cardObject.get("cardType");

                for (int i = 0; i < quantity; i++) {
                    if (cardType.equals("QUEEN")) {
                        int point = (int) (long) cardObject.get("point");
                        cardList.add(createQueenCard(name, image, backImage, point));
                    } else {
                        cardList.add(createNormalCard(name, image, backImage, cardType));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("File not found or error reading file");
            e.printStackTrace();
        }
    }


    public Card createQueenCard(String name, String image, String backImage, int point) {
        return new QueenCard(name, image, backImage, point);
    }

    public Card createNormalCard(String name, String image, String backImage, String cardType) {
        return switch (cardType) {
            case "KING" -> new KingCard(name, image, backImage);
            case "JESTER" -> new JesterCard(name, image, backImage);
            case "KNIGHT" -> new KnightCard(name, image, backImage);
            case "POTION" -> new PotionCard(name, image, backImage);
            case "WAND" -> new WandCard(name, image, backImage);
            case "DRAGON" -> new DragonCard(name, image, backImage);
            default -> new NumberCard(name, image, backImage);
        };
    }


    @Override
    public List<Card> getAllNormalCard() {
        return normalCardList;
    }

    @Override
    public List<Card> getAllQueenCard() {
        return queenCardList;
    }
}

