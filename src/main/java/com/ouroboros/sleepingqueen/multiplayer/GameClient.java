package com.ouroboros.sleepingqueen.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    /**
     * Usage: java GameClient <playerName> <gameId>
     */
    public static void main(String[] args) {

        String playerName = args[0]; // playerName
        String gameId = args[1]; // gameId
        int playerId = 0; // playerId

        new GameClient().startClient(playerName, gameId, playerId);
    }

    public void startClient(String playerName, String gameId, int playerId) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Create and send a game object to the server
            Game game = new Game();
            game.setGameId(gameId);
            out.writeObject(game);

            // Create and send a player object to the server
            Player player = new Player();
            player.setPlayerName(playerName);
            player.setPlayerId(String.valueOf(playerId));
            out.writeObject(player);

            // Read response from the server
            String response = (String) in.readObject();
            System.out.println("Server response: " + response);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}