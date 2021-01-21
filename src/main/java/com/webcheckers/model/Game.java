package com.webcheckers.model;

import com.webcheckers.util.Color;
import com.webcheckers.util.BoardSeed;

/**
 * Provides services the the {@link Player} when in a game.
 *
 * @author Mike Nystoriak
 * @author Jack Gilbert
 * @author Aaron Segal
 * @author Caleb Eldridge
 */
public class Game {
    // Private Fields
    /**
     * The {@link Player} that started the game.
     */
    private Player redPlayer;
    /**
     * The {@link Player} that accepted the game.
     */
    private Player whitePlayer;
    /**
     * The red {@link Player}'s {@link BoardView}.
     */
    private BoardView redBoard;
    /**
     * The white {@link Player}'s {@link BoardView}.
     */
    private BoardView whiteBoard;
    /**
     * Indicator for the red {@link Player}'s turn to make a {@link Move}.
     */
    private boolean isRedTurn;
    /**
     * The {@link Color} of the {@link Player} who is taking their turn.
     */
    private Color activeColor;
    /**
     * The validated {@link Move} to be made by the active {@link Player}.
     */
    private Move stagedMove;
    /**
     * Indicator for when the game has ended.
     */
    private boolean gameOver;
    /**
     * Indicator for what type of gameOver
     */
    private String gameOverReason;
    /**
     * The {@link Player} that won the game.
     */
    private Player winner;
    /**
     * The {@link Player} that lost the game.
     */
    private Player loser;
    /**
     * The amount of {@link Piece} left for the red player
     */
    private int redPieces = 12;
    /**
     * The amount of {@link Piece} left for the white player
     */
    private int whitePieces = 12;

    /**
     * The amount of {@link Player} in the game
     */
    private int playerLeft = 0;
    private boolean isActive;
    private boolean isSeeded;
    private boolean isMultiCapture;

    private Position onlyValidAttacker = null;

    private Stats stats;

    // Constructors

    /**
     * The constructor for a {@link Game} object.
     *
     * @param red
     *         the {@link Player} that started the game
     * @param white
     *         the {@link Player} that accepted the game
     */
    public Game(Player red, Player white) {
        this.redPlayer = red;
        this.whitePlayer = white;
        this.isRedTurn = true;
        this.activeColor = Color.RED;
        this.redBoard = BoardView.generateBoard(Color.RED);
        this.whiteBoard = BoardView.generateBoard(Color.WHITE);
        this.stagedMove = null;
        this.gameOver = false;
        this.winner = null;
        this.loser = null;
        this.isSeeded = false;
        this.gameOverReason = "resignation";
        this.isActive = true;
        this.isMultiCapture = false;
        this.stats = new Stats();
    }

    /**
     * The constructor for a {@link Game} object.
     *
     * @param red
     *         the {@link Player} that started the game
     * @param white
     *         the {@link Player} that accepted the game
     * @param seed
     *         the {@link BoardSeed} to seed the {@link BoardView}
     */
    public Game(Player red, Player white, BoardSeed seed) {
        this.redPlayer = red;
        this.whitePlayer = white;
        this.isRedTurn = true;
        this.activeColor = Color.RED;
        this.redBoard = BoardView.generateBoard(Color.RED, seed);
        this.whiteBoard = BoardView.generateBoard(Color.WHITE, seed);
        this.stagedMove = null;
        this.gameOver = false;
        this.winner = null;
        this.loser = null;
        this.gameOverReason = "resignation";
        this.isActive = false;
        this.isSeeded = true;
        this.isMultiCapture = false;
        this.stats = new Stats();
    }

    // Public Methods

    /**
     * The accessor to the {@link Player} that started the game.
     *
     * @return the {@link Player} that started the game
     */
    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    /**
     * The accessor to the {@link Player} that accepted the game.
     *
     * @return the {@link Player} that accepted the game
     */
    public Player getRedPlayer() {
        return this.redPlayer;
    }

    /**
     * Toggles the turns between the red {@link Player} and the white
     * {@link Player}.
     */
    public void toggle() {
        this.isRedTurn = !this.isRedTurn;
        this.activeColor = this.isRedTurn ? Color.RED : Color.WHITE;
    }

    /**
     * The accessor to the active {@link Color}.
     *
     * @return the {@link Color} of the {@link Player} making a {@link Move}
     */
    public Color getActiveColor() {
        return this.activeColor;
    }

    /**
     * Determines whether or not it is a {@link Player}'s turn.
     *
     * @param player
     *         the {@link Player} in question
     *
     * @return a boolean that states whether or not it is the {@link Player}'s turn
     *
     *         TRUE: it is the {@link Player}'s turn
     *         FALSE: it is not the {@link Player}'s turn
     */
    public boolean isPlayersTurn(Player player) {
        if (player.equals(redPlayer) && isRedTurn) return true;
        else if (player.equals(whitePlayer) && !isRedTurn) return true;
        else return false;
    }

    /**
     * The accessor to the {@link BoardView} of the given {@link Player}.
     *
     * @param player
     *         the {@link Player} in question
     *
     * @return the {@link BoardView} of the {@link Player}
     */
    public BoardView getBoard(Player player) {
        if (player.equals(redPlayer)) return this.redBoard;
        else return this.whiteBoard;
    }

    /**
     * Retrieves the opponent {@link Player} of the current {@link Player}.
     *
     * @param player
     *         the {@link Player} in question
     *
     * @return the opponent {@link Player} of the {@link Player}
     */
    public Player getOtherPlayer(Player player) {
        if (player.equals(redPlayer)) return this.whitePlayer;
        else return this.redPlayer;
    }

    /**
     * Updates the {@link BoardView}s of each {@link Player} after a valid {@link Move}.
     *
     * @param player
     *         the {@link Player} that made the {@link Move}
     */
    public void submit(Player player) {
        if (player.equals(redPlayer)) {
            redBoard.updateBoard(this.stagedMove, false);
            whiteBoard.updateBoard(this.stagedMove, true);
        } else if (player.equals(whitePlayer)) {
            redBoard.updateBoard(this.stagedMove, true);
            whiteBoard.updateBoard(this.stagedMove, false);
        }
        this.stagedMove = null; //un-stage the move after move is submitted
    }

    /**
     * Sets the validated {@link Move} that will update each
     * {@link Player}'s {@link BoardView}.
     *
     * @param move
     *         the {@link Move} to be staged
     */
    public void setStagedMove(Move move) {
        this.stagedMove = move;
    }

    public boolean hasStagedMove() {
        return this.stagedMove != null;
    }

    /**
     * Handler for when a {@link Player} chooses to resign the game.
     *
     * @param player
     *         the {@link Player} that resigned the game
     *
     * @return the status of the {@link Player}'s resignation
     *
     *         TRUE: the {@link Player} has resigned
     *         FALSE: the {@link Player} cannot resign
     */
    public boolean canResignGame(Player player) {
        if (!this.isPlayersTurn(player)) return false;
        this.gameOver = true;
        this.loser = player.equals(this.redPlayer) ? this.redPlayer : this.whitePlayer;
        this.winner = player.equals(this.redPlayer) ? this.whitePlayer : this.redPlayer;
        return true;
    }

    private boolean checkGameOver() {
        this.redPieces = this.redBoard.checkScores(Color.RED);
        this.whitePieces = this.whiteBoard.checkScores(Color.WHITE);
        return redPieces == 0 || whitePieces == 0;
    }

    /**
     * The accessor to the game over flag.
     *
     * @return a boolean that determines whether or not the game is over
     *
     *         TRUE: the game is over
     *         FALSE: the game is not over
     */
    public boolean getGameOver() {
        if (checkGameOver()) {
            this.winner = this.redPieces == 0 ? this.whitePlayer : this.redPlayer;
            this.loser = this.getOtherPlayer(this.winner);
            this.gameOverReason = "pieces";
            this.gameOver = true;
        }
        return this.gameOver;
    }

    /**
     * The accessor to the {@link Player} that lost the game.
     *
     * @return the {@link Player} that lost the game
     */
    public Player getLoser() {
        return this.loser;
    }

    public int getWinnerPiecesLeft(){
        if (this.winner.getName().equals(this.getRedPlayer().getName())){
            return redPieces;
        }
        return whitePieces;
    }

    /**
     * The accessor to the {@link Player} that won the game.
     *
     * @return the {@link Player} that won the game
     */
    public Player getWinner() {
        return this.winner;
    }

    /**
     * The accessor to the {@link String} for game over.
     *
     * @return the {@link String} that won the game
     */
    public String getGameOverReason() {
        return this.gameOverReason;
    }

    /**
     * Increment method that is used to determine the amount of {@link Player} in the game.
     */
    public void playerLeft() {
        ++playerLeft;
    }

    /**
     * The accessor to the {@link Player} still in the game.
     *
     * @return the {@link Integer} of the amount of players left in game.
     */
    public int playersLeft() {
        return playerLeft;
    }


    public boolean getIsSeeded(){
        return isSeeded;
    }
    public boolean getActive(){
        return this.isActive;
    }

    public void setActive(boolean active){
        this.isActive = active;
    }
    public boolean getMultiCapture() {
        return this.isMultiCapture;
    }
    public void toggleMulti() {
        this.isMultiCapture = !isMultiCapture;
    }
    public void setMulti(boolean multiReset) {
        this.isMultiCapture = multiReset;
    }

    public void setOnlyValidAttacker(Position position){
        onlyValidAttacker = position;
    }

    public Position getOnlyValidAttacker(){
        return onlyValidAttacker;
    }

    public Move getStagedMove(){
        return stagedMove;
    }

    public Stats getStats(){
        return stats;
    }

}