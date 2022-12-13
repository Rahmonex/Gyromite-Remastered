package modele.plateau;

public class Radis extends EntiteStatique {
    public Radis(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }

    @Override
    public Type getType() {
        return Type.RADIS;
    }

    @Override
    public boolean peutServirDeSupport() {
        return true;
    }

    @Override
    public boolean peutEtreEcrase() {
        return false;
    }


}
