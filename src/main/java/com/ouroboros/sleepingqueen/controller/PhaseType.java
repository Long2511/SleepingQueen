/**
 * @author Thanh Phuoc Nguyen - 1584468
 */


package com.ouroboros.sleepingqueen.controller;

/**
 * Enum representing different phases in the game.
 * <p>
 * - PICK_SLEEPING_QUEEN: Player attempts to pick a sleeping queen.
 * - KNIGHT_ATTACK: Player attacks with a knight card.
 * - KNIGHT_DEFEND: Opponent defends against a knight attack.
 * - POTION_ATTACK: Player attacks with a potion card.
 * - WAND_DEFEND: Opponent defends against a potion attack with a wand.
 * - NORMAL_TURN: Regular turn where the player can play or discard cards.
 */
public enum PhaseType {
    PICK_SLEEPING_QUEEN,
    KNIGHT_ATTACK,
    KNIGHT_DEFEND,
    POTION_ATTACK,
    WAND_DEFEND,
    NORMAL_TURN
}