package modele.plateau;

public class Corde extends EntiteStatique {
    public Corde(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return true;
    }

    @Override
    public Type getType() {
        return Type.CORDE;
    }

    @Override
    public boolean peutServirDeSupport() {
        return false;
    }

    @Override
    public boolean peutEtreEcrase() {
        return true;
    }
}
