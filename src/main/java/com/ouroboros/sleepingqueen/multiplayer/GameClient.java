package com.ouroboros.sleepingqueen.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class GameClient {
    private static final int SERVER_PORT = 8080;
    private static final int BROADCAST_PORT = 8888;
    private static final int TIMEOUT = 5000; // 5 seconds

    /**
     * Usage: java GameClient <playerName> <gameId>
     */
    public static void main(String[] args) {
        String playerName = args[0];
        String gameId = args[1];
        int playerId = 0;

        String serverAddress = discoverServerAddress();
        if (serverAddress != null) {
            new GameClient().startClient(playerName, gameId, playerId, serverAddress);
        } else {
            System.out.println("Server not found.");
        }
    }

    public static String discoverServerAddress() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            String request = "DISCOVER_SERVER_REQUEST";
            byte[] requestData = request.getBytes();
            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, InetAddress.getByName("255.255.255.255"), BROADCAST_PORT);
            socket.send(requestPacket);

            socket.setSoTimeout(TIMEOUT);
            byte[] buffer = new byte[256];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            if (response.startsWith("DISCOVER_SERVER_RESPONSE:")) {
                return response.split(":")[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startClient(String playerName, String gameId, int playerId, String serverAddress) {
        try (Socket socket = new Socket(serverAddress, SERVER_PORT);
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