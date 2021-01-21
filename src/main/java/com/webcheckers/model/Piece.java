package com.webcheckers.model;

import com.webcheckers.util.Color;
import com.webcheckers.util.Type;

/**
 * Entity for a token that represents a {@link Player} on
 * a {@link Space}.
 *
 * @author Mike Nystoriak
 */
public class Piece {
    // Private Fields
    /**
     * The {@link Color} of the {@link Color}.
     */
    private final Color color;
    /**
     * The {@link Type} of the {@link Piece}.
     */
    private Type type;

    // Constructors
    /**
     * The constructor for a {@link Piece} object.
     *
     * @param type
     *         the {@link Type} of the {@link Piece}
     *
     *         SINGLE: not kinged
     *         KINGED: kinged
     * @param color
     *         the {@link Color} of the {@link Piece}
     *
     *         RED: red
     *         WHITE: white
     */
    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    // Public Methods
    /**
     * Checks that two {@link Piece}s have the same {@link Color} and
     * {@link Type}.
     *
     * @return boolean that determines equality of the two {@link Piece}s
     *
     *         TRUE: {@link Piece}s are equal
     *         FALSE: {@link Piece}s are not equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Piece)) return false;
        return this.type == ((Piece) obj).getType() && this.color == ((Piece) obj).getColor();
    }
    /**
     * The accessor to the {@link Type} of the {@link Piece}.
     *
     * @return the {@link Type} of the {@link Piece}
     */
    public Type getType() {
        return this.type;
    }
    /**
     * The accessor to the {@link Color} of the {@link Piece}.
     *
     * @return the {@link Color} of the {@link Piece}
     */
    public Color getColor() {
        return this.color;
    }

    public Piece clonePiece(){
        return new Piece(this.type,this.color);
    }
}
