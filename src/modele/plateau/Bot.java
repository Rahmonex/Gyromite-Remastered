/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Direction;

import java.util.Random;

/**
 * Ennemis (Smicks)
 */
public class Bot extends EntiteDynamique {
    private Random r = new Random();
    private Type type = Type.BOT;

    public Bot(Jeu _jeu) {
        super(_jeu);
    }

    public boolean peutEtreEcrase() { return false; }

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
