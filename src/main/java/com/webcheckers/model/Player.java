package com.webcheckers.model;

import java.util.Objects;

/**
 * Entity for a player of checkers.
 *
 * @author Mike Nystoriak
 * @author Jack Gilbert
 * @author Aaron Segal
 * @author Caleb Eldridge
 */
public class Player {
    // Private Fields
    /**
     * The name of the {@link Player}.
     */
    private final String name;
    /**
     * The number of wins the {@link Player} has.
     */
    private int wins;
    /**
     * The name of the losses the {@link Player} has.
     */
    private int losses;

    private int ELO;

    // Constructors
    /**
     * The constructor for a {@link Player} object.
     *
     * @param name
     *         the name of the {@link Player}
     */
    public Player(String name) {
        this.name = name;
        this.ELO = 500;
    }

    // Public Methods
    /**
     * The accessor for the name of the {@link Player}.
     *
     * @return the name of the {@link Player}
     */
    public String getName() { return this.name; }
    /**
     * Checks that two {@link Player}s have the same name.
     *
     * @return boolean that determines equality of the two {@link Piece}s
     *
     * TRUE: {@link Player}s are equal
     * FALSE: {@link Player}s are not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player)o;
        return this.name.equals(player.name);
    }
    /**
     * The accessor for the name of the {@link Player}.
     *
     * @return the name of the {@link Player}
     */
    public void addWin(){
        ++wins;
    }
    /**
     * The incrementer for the number of losses the {@link Player}.
     *
     */
    public void addLoss(){
        ++losses;
    }
    /**
     * The accessor for the ELO of the {@link Player}.
     *
     * @return the win loss ratio of the {@link Player}
     */
    public int getELO(){
        return ELO;
    }

    public void modifyELO(boolean winner,Player opponent){
        if (this.ELO==0){
            this.ELO = 1;
        }
        float difference = -1*(this.getELO()-opponent.getELO());
        float myELO = (float) this.getELO();
        System.out.println(difference/myELO*24.55);
        int change = (int) (difference/myELO*24.55+100);
        if (winner){
            this.ELO+=change;
        }
        else {
            this.ELO-=change;
        }
        /*
        Change = Difference / ELO  *30 + 100
         */
    }
    /**
     * Creates a hash code from the name of the {@link Player}.
     *
     * @return a hash code of the name
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public int winRate(){
        if (wins+losses==0){
            return 0;
        }
        return (100*wins)/(wins+losses);
    }
}