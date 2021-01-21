package com.webcheckers.application;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for the {@link PlayerLobby} class.
 *
 * @author Jack Gilbert
 */
@Tag("Appl-tier")
public class PlayerLobbyTest {
    // Private Fields
    /**
     * This {@link Player} has a valid name.
     */
    private final String NAME_IS_VALID = "Name is valid";
    /**
     * This {@link Player} has a non valid name.
     */
    private final String NAME_NOT_VALID = "Name is not valid";
    /**
     * This {@link Player} can not be found in the {@link PlayerLobby}.
     */
    private final String PLAYER_NOT_IN_LOBBY = "Player not found in current lobby";
    /**
     * This {@link PlayerLobby} has an invalid count of players currently in the lobby.
     */
    private final String WRONG_TOTAL = "Too few or too many players in lobby";
    /**
     * This {@link Player} name has invalid characters and is not being flagged.
     */
    private final String NAME_DETECTION = "Name is not being flagged for non alphanumeric characters";
    /**
     * This {@link Player} has a dupe name that is not being detected.
     */
    private final String NAME_DUPE = "Name is not being flagged duplicate username";
    /**
     * This {@link PlayerLobby} has a non matching hashmap to the original hashmap.
     */
    private final String USERS_NOT_FOUND = "The hashmaps do not match";

    // Public Methods
    /**
     * The constructor test for {@link PlayerLobby}.
     *
     * Tests construction, instantiation, and field assignments.
     */
    @Test
    public void constructorTest() {
        HashMap<String, Player> users = new HashMap<>();
        PlayerLobby CuT = new PlayerLobby(users);
        assertEquals(0, CuT.getNumberOfUsers());
    }
    /**
     * Test for proper adding of a {@link Player} to a {@link PlayerLobby}
     *
     * Tests that {@link Player} can be found in the {@link PlayerLobby}
     * and that the detection of Dupe names and invalid names works.
     */
    @Test
    public void userSignInTest() {
        HashMap<String, Player> users = new HashMap<>();
        PlayerLobby CuT = new PlayerLobby(users);

        // test basic user sign in of 2 players
        Player player1 = new Player("Test1");
        CuT.userSignIn(player1.getName());
        assertEquals(player1, CuT.getUser("Test1"), PLAYER_NOT_IN_LOBBY);
        assertEquals(1, CuT.getNumberOfUsers(), WRONG_TOTAL);

        // test invalid usernames
        Player player2 = new Player("!Test2");
        assertEquals("NAME_INVALID", CuT.userSignIn(player2.getName()), NAME_DETECTION);

        // test duplicate usernames
        assertEquals("DUPE_INVALID", CuT.userSignIn(player1.getName()), NAME_DUPE);
    }
    /**
     * Test that the string username of a {@link Player} is valid.
     *
     * Tests that the username of a player cannot contain non alphanumeric characters, does not go outside 13 characters
     * and has no leading or trailing white space. As well as not containing an empty username.
     */
    @Test
    public void validUsernameTest() {
        HashMap<String, Player> users = new HashMap<>();
        PlayerLobby CuT = new PlayerLobby(users);

        // test for empty name
        Player player1 = new Player("");
        assertFalse(CuT.isValidUsername(player1.getName()), NAME_IS_VALID);

        // test for alphanumeric characters
        Player player2 = new Player("!Test");
        assertFalse(CuT.isValidUsername(player2.getName()), NAME_IS_VALID);

        // test spaces valid within name
        Player player3 = new Player("Test Test");
        assertTrue(CuT.isValidUsername(player3.getName()), NAME_NOT_VALID);

        // test max char bound
        Player player4 = new Player("AAAAAAAAAAAAAA");
        assertFalse(CuT.isValidUsername(player4.getName()), NAME_IS_VALID);

        // test leading white space
        Player player5 = new Player(" Test");
        assertFalse(CuT.isValidUsername(player5.getName()), NAME_IS_VALID);

        // test trailing white space
        Player player6 = new Player("Test ");
        assertFalse(CuT.isValidUsername(player6.getName()), NAME_IS_VALID);

        // test non alphanumeric character in front
        Player player7 = new Player("Test!Test");
        assertFalse(CuT.isValidUsername(player7.getName()), NAME_IS_VALID);
    }
    /**
     * Test for getting a single {@link Player} in the {@link PlayerLobby}
     *
     * Tests that both players made when added to the lobby can be received with this method.
     */
    @Test
    public void getUserTest() {
        // test to make sure a single user can be accessed from lobby
        HashMap<String, Player> users = new HashMap<>();
        PlayerLobby CuT = new PlayerLobby(users);
        Player player1 = new Player("Test1");
        Player player2 = new Player("Test2");

        CuT.userSignIn(player1.getName());
        CuT.userSignIn(player2.getName());

        assertEquals(player2, CuT.getUser("Test2"), PLAYER_NOT_IN_LOBBY);
        assertEquals(player1, CuT.getUser("Test1"), PLAYER_NOT_IN_LOBBY);
    }
    /**
     * Test for getting an int total of all users in the {@link PlayerLobby}
     *
     * Tests that when 2 users are signed in, it verifies that the function receives a 2 back
     */
    @Test
    public void getNumberOfUsersTest() {
        // test to get # of users when added to the lobby
        HashMap<String, Player> users = new HashMap<>();
        PlayerLobby CuT = new PlayerLobby(users);
        Player player1 = new Player("Test1");
        Player player2 = new Player("Test2");

        CuT.userSignIn(player1.getName());
        CuT.userSignIn(player2.getName());
        assertEquals(2, CuT.getNumberOfUsers(), WRONG_TOTAL);
    }
    /**
     * Test for getting all users in the {@link PlayerLobby}
     *
     * Tests that when a user is added to the {@link PlayerLobby} that we get a hashmap that matches the users hashmap
     */
    @Test
    public void getUsersTest() {
        HashMap<String, Player> users = new HashMap<>();
        PlayerLobby CuT = new PlayerLobby(users);
        Player player1 = new Player("Test1");

        CuT.userSignIn(player1.getName());
        assertEquals(users, CuT.getUsers(), USERS_NOT_FOUND);
    }
}
