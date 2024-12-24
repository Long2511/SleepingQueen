package com.ouroboros.sleepingqueen.deck;

import com.ouroboros.sleepingqueen.deck.cardcollection.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class helps read data from a JSON file which contains the information about the Cards in the Deck.
 */
public class CardReader {
    private final String cardDataPath = "/com/ouroboros/sleepingqueen/cardData/cardData.json";

    List<Card> queenCardList;
    List<Card> cardNotQueenList; // List of all cards EXCEPT Queen Cards

    public CardReader() {
        queenCardList = new ArrayList<Card>();
        cardNotQueenList = new ArrayList<Card>();
    }

    public Card createCard(String name, String description, String image, String cardType) {
        return switch (cardType) {
            case "QUEEN" -> new QueenCard(name, description, image);
            case "KING" -> new KingCard(name, description, image);
            case "JESTER" -> new JesterCard(name, description, image);
            case "KNIGHT" -> new KnightCard(name, description, image);
            case "POTION" -> new PotionCard(name, description, image);
            case "WAND" -> new WandCard(name, description, image);
            case "DRAGON" -> new DragonCard(name, description, image);
            default -> new NumberCard(name, description, image);
        };
    }


    public void Read() {
        JSONParser parser = new JSONParser();

        // Use getResourceAsStream to read the file from the resources folder
        try (FileReader reader = new FileReader(getClass().getResource(cardDataPath).toURI().getPath())) {
            JSONArray cards = (JSONArray) parser.parse(reader);

            for (Object card : cards) {
                JSONObject cardObject = (JSONObject) card;
                String name = (String) cardObject.get("cardName");
                int quantity = Integer.parseInt((String) cardObject.get("quantity"));
                String type = (String) cardObject.get("cardType");
                String description = (String) cardObject.get("cardDescription");
                String image = (String) cardObject.get("cardImage");
                System.out.println("Name: " + name + ", Description: " + description + ", Image: " + image);
                for (int i = 0; i < quantity; i++) {
                    if (type.equals("QUEEN")) {
                        queenCardList.add(createCard(name, description, image, type));
                    } else {
                        cardNotQueenList.add(createCard(name, description, image, type));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("File not found or error reading file");
            e.printStackTrace();
        }
    }

    public List<Card> getQueenCardList() {
        return queenCardList;
    }
    public List<Card> getCardNotQueenList() {
        return cardNotQueenList;
    }

//    public static void main(String[] args) {
//        CardReader cardReader = new CardReader();
//        try {
//            cardReader.Read();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}