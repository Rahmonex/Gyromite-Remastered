/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Direction;

/**
 * Héros du jeu
 */
public class Heros extends EntiteDynamique {
    public Heros(Jeu _jeu) {
        super(_jeu);
    }

    private Type type = Type.HEROS;

    public boolean peutEtreEcrase() { return true; }

    @Override
    public Direction getDirectionColonneBleuAvant() {
        return null;
    }

    @Override
    public Direction getDirectionColonneOrangeAvant() {
        return null;
    }

    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
