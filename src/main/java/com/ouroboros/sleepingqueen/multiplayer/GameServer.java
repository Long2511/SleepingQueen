package com.ouroboros.sleepingqueen.multiplayer;

import com.ouroboros.sleepingqueen.deck.Card;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private static final int PORT = 8080;
    private static final int BROADCAST_PORT = 8888;
    private List<Game> games = new ArrayList<>();

    public static void main(String[] args) {
        new GameServer().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, games).start();

                // Print the number of games in the server
                System.out.println("Number of games: " + games.size());
                System.out.println("A new Player joined");

                // Print the game details
                for (Game game : games) {
                    System.out.println("Game ID: " + game.getGameId());
                    System.out.println("Game status: " + game.isGameStatus());
                    System.out.println("Game winner: " + game.getGameWinner());
                    System.out.println("Players: ");
                    for (Player player : game.getPlayers()) {
                        System.out.println("Player ID: " + player.getPlayerId());
                        System.out.println("Player name: " + player.getPlayerName());
                        System.out.println("Player score: " + player.getPlayerScore());
                        System.out.println("Player cards: ");
                        for (Card card : player.getPlayerCards()) {
                            System.out.println(card);
                        }
                        System.out.println("Player turn: " + player.isPlayerTurn());
                    }
                    System.out.println("Queens cards deck: ");
                    for (Card card : game.getQueensCardsDeck()) {
                        System.out.println(card);
                    }
                    System.out.println("Game deck: ");
                    for (Card card : game.getGameDeck()) {
                        System.out.println(card);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForBroadcasts() {
        try (DatagramSocket socket = new DatagramSocket(BROADCAST_PORT)) {
            byte[] buffer = new byte[256];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                if ("DISCOVER_SERVER_REQUEST".equals(message)) {
                    InetAddress clientAddress = packet.getAddress();
                    int clientPort = packet.getPort();
                    String response = "DISCOVER_SERVER_RESPONSE:" + InetAddress.getLocalHost().getHostAddress();
                    byte[] responseData = response.getBytes();
                    DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                    socket.send(responsePacket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}