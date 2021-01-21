package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.BoardSeed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Provides services the the {@link Player} when in a game.
 *
 * @author Mike Nystoriak
 * @author Jack Gilbert
 * @author Aaron Segal
 * @author Caleb Eldridge
 */
public class GameLobby {

    // Private Fields
    /**
     * A {@link HashMap} of {@link Game}s that are currently in progress.
     */
    private final HashMap<String, Game> games = new HashMap<>();

    private final HashMap<String,Integer> gameCounter = new HashMap<>();

    private final boolean demoMode = true;

    // Constructors

    /**
     * The constructor for a {@link GameLobby} object.
     */
    public GameLobby() {
    }

    /**
     * Checks if a game exist then adds {@link Game} to {@link GameLobby} object.
     *
     * @param currentUser
     *         a {@link Player} object
     * @param opponent
     *         a {@link Player} object
     */
    public void createGameIfNoGameExists(Player currentUser, Player opponent) {
        if (!this.games.containsKey(getKey(currentUser, opponent))||!this.games.get(getKey(currentUser, opponent)).getActive()) {
            try {
                gameCounter.put(getSimpleKey(currentUser,opponent),gameCounter.get(getSimpleKey(currentUser,opponent))+1);
            }
            catch (NullPointerException npe){
                System.out.println("No games between these people yet!");
                gameCounter.put(getSimpleKey(currentUser,opponent),0);
            }
            this.games.put(getKey(currentUser, opponent), new Game(currentUser, opponent));
            if (this.demoMode) {
                this.games.put("simple-move", new Game(currentUser, opponent, BoardSeed.SIMPLE_MOVE));
                this.games.put("single-capture", new Game(currentUser, opponent, BoardSeed.SINGLE_CAPTURE));
                this.games.put("multiple-capture", new Game(currentUser, opponent, BoardSeed.MULTIPLE_CAPTURE));
                this.games.put("king-me", new Game(currentUser, opponent, BoardSeed.KING_ME));
            }
        }
    }

    /**
     * Finds and return a {@link Game}.
     *
     * @param gameKey
     *         a {@link String} that is a {@link Game} key
     *
     * @return a {@link Game}
     */
    public Game getGame(String gameKey) {
        return games.get(gameKey);
    }

    /**
     * Finds and return a {@link Game}.
     *
     * @param currentUser
     *         a {@link Player} object
     * @param opponent
     *         a {@link Player} object
     *
     * @return a {@link String} that represent a game key
     */
    public String getKey(Player currentUser, Player opponent) {
        int addend;
        String gameKey;
        if(currentUser.getName().compareTo(opponent.getName())<0){
            gameKey = (currentUser.getName().hashCode() + opponent.getName().hashCode()) + "";
        }
        else {
            gameKey = (opponent.getName().hashCode() + currentUser.getName().hashCode()) + "";
        }
        try{
            gameKey+=gameCounter.get(gameKey);
        }
        catch (NullPointerException npe){
            gameKey = gameKey;
        }
        return gameKey;
    }

    public String getSimpleKey(Player currentUser,Player opponent){
        if(currentUser.getName().compareTo(opponent.getName())<0){
            return (currentUser.getName().hashCode() + opponent.getName().hashCode()) + "";
        }
        else {
            return (opponent.getName().hashCode() + currentUser.getName().hashCode()) + "";
        }
    }

    private HashMap<String,Game> getGames(boolean wantDoneGames){
        HashMap<String,Game> selection = new HashMap<>();
        games.forEach((gameID,gameObj) -> {
            if (gameObj.getActive()!=wantDoneGames && !gameObj.getIsSeeded()) {
                selection.put(gameID,gameObj);
            }
        });
        return selection;
    }



    public HashMap<String,Game> getMyGames(Player player,boolean wantDoneGames){
        HashMap<String,Game> myGames = getGames(wantDoneGames);
        ArrayList<String> removeKeys = new ArrayList<>();
        for(HashMap.Entry<String, Game> entry : myGames.entrySet()) {
            String key = entry.getKey();
            Game game = entry.getValue();
            if (!player.equals(game.getRedPlayer())&&!player.equals(game.getWhitePlayer())||(!key.contains("~")&&wantDoneGames)){
                removeKeys.add(key);
            }
        }
        for (String key:removeKeys){
            myGames.remove(key);
        }
        return myGames;
    }


    public int getCount(String gameKey){
        try {
            return gameCounter.get(gameKey);
        }
        catch (NullPointerException npe){
            return -1;
        }
    }

}
