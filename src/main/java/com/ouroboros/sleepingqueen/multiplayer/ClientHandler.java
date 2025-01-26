/**
 * ClientHandler.java
 * <p>
 * This class is used to handle client requests.
 *
 * @Author Hai Long Mac
 */

package com.ouroboros.sleepingqueen.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private List<Game> games;

    /**
     * Constructor
     *
     * @param socket Socket
     * @param games  List of games
     */
    public ClientHandler(Socket socket, List<Game> games) {
        this.clientSocket = socket;
        this.games = games;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            // Handle client requests here
            Object receivedObject = in.readObject();
            if (receivedObject instanceof Game) {
                Game game = (Game) receivedObject;
                games.add(game);
                out.writeObject("Game received");
            } else if (receivedObject instanceof Player) {
                Player player = (Player) receivedObject;
                // Handle player data
                out.writeObject("Player received");
            }

            out.writeObject("Welcome to the game!");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}