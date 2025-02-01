/**
 * JSONCardDAO.java
 * This class reads card data from JSON files and creates card objects.
 * It implements CardDAO interface.
 * It reads card data from two JSON files: normalCardData.json and queenCardData.json
 * It creates card objects based on the data read from JSON files.
 * It returns a list of all normal cards and a list of all queen cards.
 *
 * @author: Thanh Phuoc Nguyen - 1584468
 * @author Hai Long Mac - 1534413
 */

package com.ouroboros.sleepingqueen.dao;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.cardcollection.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class JSONCardDAO implements CardDAO {

    private final String NORMAL_CARD_DATA_PATH = "/com/ouroboros/sleepingqueen/cardData/normalCardData.json";
    private final String QUEEN_CARD_DATA_PATH = "/com/ouroboros/sleepingqueen/cardData/queenCardData.json";

    private List<Card> queenCardList;
    private List<Card> normalCardList; // List of all cards EXCEPT Queen Cards

    /**
     * Constructor initializes card lists and loads data from JSON files.
     */
    public JSONCardDAO() {
        queenCardList = new ArrayList<>();
        normalCardList = new ArrayList<>();
        readFromJSON(queenCardList, QUEEN_CARD_DATA_PATH);
        readFromJSON(normalCardList, NORMAL_CARD_DATA_PATH);
    }

    /**
     * Reads card data from a JSON file and adds it to the provided list.
     *
     * @param cardList The list to store card objects.
     * @param path     The file path of the JSON data.
     */
    public void readFromJSON(List<Card> cardList, String path) {
        JSONParser parser = new JSONParser();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(path))) {
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
                        cardList.add(createQueenCard(name, image, backImage, point, description));
                    } else {
                        cardList.add(createNormalCard(name, image, backImage, cardType, description));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("File not found or error reading file");
            e.printStackTrace();
        }
    }

    /**
     * Creates a Queen card.
     *
     * @param name        The name of the card.
     * @param image       The image path of the card.
     * @param backImage   The back image path of the card.
     * @param point       The point value of the Queen card.
     * @param description The description of the card.
     * @return A QueenCard object.
     */
    public Card createQueenCard(String name, String image, String backImage, int point, String description) {
        return new QueenCard(name, image, backImage, point, description);
    }

    /**
     * Creates a normal card based on the card type.
     *
     * @param name        The name of the card.
     * @param image       The image path of the card.
     * @param backImage   The back image path of the card.
     * @param cardType    The type of the card.
     * @param description The description of the card.
     * @return A Card object of the corresponding type.
     */
    public Card createNormalCard(String name, String image, String backImage, String cardType, String description) {
        return switch (cardType) {
            case "KING" -> new KingCard(name, image, backImage, description);
            case "JESTER" -> new JesterCard(name, image, backImage, description);
            case "KNIGHT" -> new KnightCard(name, image, backImage, description);
            case "POTION" -> new PotionCard(name, image, backImage, description);
            case "WAND" -> new WandCard(name, image, backImage, description);
            case "DRAGON" -> new DragonCard(name, image, backImage, description);
            default -> new NumberCard(name, image, backImage, description);
        };
    }

    /**
     * Retrieves a list of all normal cards.
     *
     * @return List of normal cards.
     */
    @Override
    public List<Card> getAllNormalCard() {
        return normalCardList;
    }

    /**
     * Retrieves a list of all Queen cards.
     *
     * @return List of Queen cards.
     */
    @Override
    public List<Card> getAllQueenCard() {
        return queenCardList;
    }
}
