package com.webcheckers.model;
import com.webcheckers.util.Color;
import com.webcheckers.util.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Replay implements Comparable<Replay>{
    private Player redPlayer;
    private Player whitePlayer;
    private Color color;
    private int currentPosRed;
    private int currentPosWhite;
    private String id;
    private ArrayList<String> boardFramesRed;
    private ArrayList<String> boardFramesWhite;
    private BoardView replayBoardRed;
    private BoardView replayBoardWhite;
    public Replay(Player redPlayer,Player whitePlayer,String gameKey){
        this.currentPosRed = -1;
        this.currentPosWhite = -1;
        this.color = Color.RED;
        this.boardFramesRed = new ArrayList<>();
        this.boardFramesWhite = new ArrayList<>();
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.replayBoardRed = BoardView.generateBoard(Color.RED);
        this.replayBoardWhite = BoardView.generateBoard(Color.WHITE);
        this.id = gameKey;
    }

    public void addBoardView(BoardView frame,Color color){
        if (color.equals(Color.RED)) {
            this.boardFramesRed.add(frame.boardToString());
        }
        else {
            this.boardFramesWhite.add((frame.boardToString()));
        }
    }

    public void setFrame(Color color){
        try {
            if (color.equals(Color.RED)) {
                this.replayBoardRed = buildFromStr(boardFramesRed.get(currentPosRed));
            }
            else {
                this.replayBoardWhite = buildFromStr(boardFramesWhite.get(currentPosWhite));
            }
        }
        catch (IndexOutOfBoundsException npe){
            if (color.equals(Color.RED)) {
                this.replayBoardRed = BoardView.generateBoard(Color.RED);
            }
            else{
                this.replayBoardWhite = BoardView.generateBoard(Color.WHITE);
            }
        }
    }

    public boolean hasNext(Color color){
        if (color.equals(Color.RED)) {
            return currentPosRed < size() - 1;
        }
        return currentPosWhite < size() - 1;
    }

    public boolean hasPrevious(Color color){
        if (color.equals(Color.RED)) {
            return currentPosRed >= 0;
        }
        return currentPosWhite>=0;
    }

    public int size(){
        return boardFramesRed.size();
    }

    public int getCurrentPos(Color color){
        if (color.equals(Color.RED)) {
            return currentPosRed;
        }
        return currentPosWhite;
    }

    public void incrementCurrentPos(boolean positive,Color color){
        if (color.equals(Color.RED)) {
            if (positive) {
                currentPosRed++;
            } else {
                currentPosRed--;
            }
        }
        if (color.equals(Color.WHITE)) {
            if (positive) {
                currentPosWhite++;
            } else {
                currentPosWhite--;
            }
        }
    }

    public BoardView getReplayBoard(Color color){
        if (color.equals(Color.RED)){
            return replayBoardRed;
        }
        return replayBoardWhite;
    }

    public Player getRedPlayer(){
        return redPlayer;
    }

    public Player getWhitePlayer(){
        return whitePlayer;
    }

    public Color getActiveColor(){
        return color;
    }

    public void reset(Color color){
        if (color.equals(Color.RED)) {
            currentPosRed = -1;
            replayBoardRed = BoardView.generateBoard(Color.RED);
        }
        else{
            currentPosWhite = -1;
            replayBoardWhite = BoardView.generateBoard(Color.WHITE);
        }
    }

    public Player getOtherPlayer(Player player){
        if (redPlayer.equals(player)){
            return whitePlayer;
        }
        else {
            return redPlayer;
        }
    }

    public String getLastCharID(){
        return id.substring(id.length()-1);
    }

    public int SumELO(){
        return redPlayer.getELO()+whitePlayer.getELO();
    }

    @Override
    public int compareTo(Replay replay) {
        return (this.SumELO() - replay.SumELO());
    }

    public Piece handleString(char letter){
        switch (letter){
            case 'w':
                return new Piece(Type.SINGLE,Color.WHITE);
            case 'r':
                return new Piece(Type.SINGLE,Color.RED);
            case 'W':
                return new Piece(Type.KING,Color.WHITE);
            case 'R':
                return new Piece(Type.KING,Color.RED);
            default:
                return null;
        }
    }

    public BoardView buildFromStr(String boardRep){
        String[] lines = boardRep.split("\n");
        List<Row> rowList = new ArrayList<>();
        for (int row=0;row<8;row++){
            List<Space> spaceList = new ArrayList<>();
            for (int col=0;col<8;col++){
                Piece piece = handleString(lines[row].charAt(col));
                boolean isValid = (piece==null)&&(row%2!=col%2);
                spaceList.add(new Space(col,isValid,piece));
            }
            rowList.add(new Row(row,spaceList));
        }
        return new BoardView(rowList);
    }
}
