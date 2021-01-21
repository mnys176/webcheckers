package com.webcheckers.model;

import java.util.HashMap;

public class Stats {
    private int moves;
    private int winnerPiecesLeft;
    private Player winner;
    private boolean wonByResignation;

    public Stats (){
        moves = 0;
        winner = new Player("Unknown");
        wonByResignation = false;
        winnerPiecesLeft = 0;
    }

    public void addMove(){
        this.moves++;
    }

    public void setWinnerPiecesLeft(int count){
        winnerPiecesLeft = count;
    }

    public void setWinner(Player winner){
        this.winner = winner;
    }

    public void winByResignation(){
        this.wonByResignation = true;
    }


    public HashMap<String,String> getData(){
        HashMap<String,String> data = new HashMap<>();
        data.put("Moves",Integer.toString(moves));
        data.put("Winner pieces left",Integer.toString(winnerPiecesLeft));
        data.put("Won by resignation",Boolean.toString(wonByResignation));
        data.put("Winner",winner.getName());
        return data;
    }

}
