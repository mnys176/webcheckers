package com.webcheckers.application;

import java.util.*;

import com.webcheckers.model.Player;

/**
 * Provides services for accessing the {@link Player} class when
 * not in a game.
 *
 * @author Mike Nystoriak
 * @author Jack Gilbert
 * @author Aaron Segal
 * @author Caleb Eldridge
 */
public class PlayerLobby {
    // Private Fields
    /**
     * A {@link HashMap} of {@link Player}s that are currently signed in.
     */
    private final HashMap<String, Player> users;
    /**
     * The {@link Player}s that are currently signed in.
     */
    private final int MAX_CHARS = 13;

    // Constructors
    /**
     * The constructor for a {@link PlayerLobby} object.
     *
     * @param users a list of {@link Player} objects
     */
    public PlayerLobby(HashMap<String, Player> users) {
        this.users = users;
    }

    // Public Methods
    /**
     * Logs a user into the {@link PlayerLobby}.
     *
     * @param username the user's name
     *
     * @return a message evaluating the entry
     */
    public String userSignIn(String username) {
        //check that name is valid and not already taken
        if (!isValidUsername(username)) return "NAME_INVALID";
        if (isUserSignedIn(username)) return "DUPE_INVALID";

        Player newPlayer = new Player(username);
        this.users.put(username, newPlayer);
        return "VALID";
    }

    // Public Methods
    /**
     * Logs a user out of the {@link PlayerLobby}.
     *
     * @param username the user's name
     *
     */
    public void userSignOut(String username) {
        this.users.remove(username);
    }
    /**
     * Validates the user entry.
     *
     * @param username the name entered by the user
     *
     * @return boolean that determines validity
     *
     *         TRUE: username is valid
     *         FALSE: username is invalid
     */
    public boolean isValidUsername(String username) {
        //check length of entry
        if (username.equals("")) return false;
        if (username.length() > MAX_CHARS) return false;

        char firstChar = username.charAt(0);
        char lastChar = username.charAt(username.length() - 1);

        //check that first character is a letter and that the last character is not a space
        if (!Character.isLetter(firstChar) || lastChar == ' ') return false;

        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if (!Character.isLetterOrDigit(c) && !(c==' ')) return false;
        }
        return true;
    }
    /**
     * Checks if user is signed in.
     *
     * @param username the user's name
     *
     * @return boolean that determines if a user is signed in
     *
     *         TRUE: user is signed in
     *         FALSE: user is not signed in
     */
    public boolean isUserSignedIn(String username) {
        return this.users.containsKey(username);
    }
    /**
     * Gets a user by name.
     *
     * @param username the user's name
     *
     * @return the {@link Player} object with the name of username
     */
    public Player getUser(String username) {
        return this.users.get(username);
    }
    /**
     * Gets the number of active users in the {@link PlayerLobby}.
     *
     * @return the number of users in the system
     */
    public int getNumberOfUsers() {
        return this.users.size();
    }
    /**
     * Gets the list of active users in the {@link PlayerLobby}.
     *
     * @return the list of users
     */
    public HashMap<String,Player> getUsers() {
        return this.users;
    }

    public ArrayList<Player> getUsersSorted() {
        Collection<Player> players = users.values();
        ArrayList<Player> playerList = new ArrayList<>(players);
        Collections.sort(playerList, Comparator.comparing(Player::getName));
        return playerList;
    }
}