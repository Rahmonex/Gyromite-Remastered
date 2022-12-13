package modele.plateau;

import modele.deplacements.Direction;

import java.awt.*;

/**
 * Entités amenées à bouger (colonnes, ennemis)
 */
public abstract class EntiteDynamique extends Entite {

    protected Direction regarderDirection = null;
    protected Direction DirectionAvant = null;

    public EntiteDynamique(Jeu _jeu) { super(_jeu); }

    public boolean avancerDirectionChoisie(Direction d) {
        return jeu.deplacerEntite(this, d);
    }
    public Entite regarderDansLaDirection(Direction d) {return jeu.regarderDansLaDirection(this, d);}
    public Entite regarderDiagonaleBas(Direction d) {return jeu.regarderDiagonaleBas(this, d);}
    public Entite regarderAMaPosition(Entite d) {return jeu.regarderAMaPosition(this);}

    public Direction setregarderDansLaDirection(Direction d){return this.regarderDirection = d;}

    public Direction getregarderDansLaDirection(){return this.regarderDirection;}

    @Override
    public boolean peutEtreEcrase() {
        return false;
    }

    public Direction getDirectionAvant() {
        return DirectionAvant;
    }

    public void setDirectionAvant(Direction directionAvant) {
        DirectionAvant = directionAvant;
    }

    public abstract Direction getDirectionColonneBleuAvant();

    public abstract Direction getDirectionColonneOrangeAvant();
}
