package com.ouroboros.sleepingqueen.deck;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.InputStream;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



/**
 * This class help read data from a JSON file which contain the information about the Cards in the Deck.
 */

public class CardReader {

    public void CardReader() throws FileNotFoundException {

    }


    public static void main(String[] args) {
        CardDeck cardDeck = new CardDeck();
        for (int i = 0; i < 44; i++) {
            System.out.println(cardDeck.draw().getCardImgPath());
        }
    }
}
