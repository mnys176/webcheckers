package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.ui.WebServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class ReplayLobby {
    private final GameLobby gameLobby = WebServer.gameLobby;

    private final PlayerLobby playerLobby = WebServer.playerLobby;

    private final HashMap<String, Replay> replays = new HashMap<>();

    public Replay getReplay(String gameKey){
        return replays.get(gameKey);
    }

    public void addReplay(Player redPlayer,Player whitePlayer,String gameKey){
        this.replays.put(gameKey,new Replay(redPlayer,whitePlayer,gameKey));
    }

    public TreeMap<String,Replay> getReplays(boolean done){
        TreeMap<String,Replay> toReturn = new TreeMap<>();
        for(HashMap.Entry<String, Replay> replay : replays.entrySet()) {
            if (gameLobby.getGame(replay.getKey()).getActive()!=done){
                toReturn.put(replay.getKey(),replay.getValue());
            }
        }
    return toReturn;
    }

    public String getFavoriteOpponent(Player player){
        ArrayList<Player> players = playerLobby.getUsersSorted();
        int maxGames = -1;
        String friendName = "";
        for (Player opponent:players){
            int gameCount = gameLobby.getCount(gameLobby.getSimpleKey(player,opponent));
            if (gameCount>=maxGames){
                maxGames = gameCount;
                friendName = opponent.getName();
            }
        }
        if (maxGames<0){
            return "Nobody yet!";
        }
        return friendName;
    }
}
