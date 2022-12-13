package modele.plateau;

import modele.deplacements.Direction;

public class Colonne extends EntiteDynamique {

    Type type = null;
    Direction directionCouranteColonneBleu = null;
    Direction directionCouranteColonneOrange = null;

    public Colonne(Jeu _jeu) {super(_jeu);}


    public Colonne(Jeu _jeu, Type t) {
        super(_jeu);
        setType(t);
    }

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type t){
        type = t;
    }

    @Override
    public Direction getDirectionColonneBleuAvant() {
        return directionCouranteColonneBleu;
    }

    @Override
    public Direction getDirectionColonneOrangeAvant() {
        return directionCouranteColonneOrange;
    }

    public void setDirectionColonneBleuAvant(Direction d){
        directionCouranteColonneBleu = d;
    }

    public void setDirectionColonneOrangeAvant(Direction d){
        directionCouranteColonneOrange = d;
    }
}
