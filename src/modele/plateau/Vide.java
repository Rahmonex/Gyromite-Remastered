package modele.plateau;

public class Vide extends EntiteStatique {
    public Vide(Jeu _jeu) { super(_jeu); }

    @Override
    public Type getType() {
        return Type.VIDE;
    }

    @Override
    public boolean peutServirDeSupport() {
        return false;
    }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }

    @Override
    public boolean peutEtreEcrase() {
        return false;
    }

}
