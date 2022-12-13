package modele.plateau;

public class Sol extends EntiteStatique {
    public Sol(Jeu _jeu) { super(_jeu); }

    @Override
    public Type getType() {
        return Type.SOL;
    }

    @Override
    public boolean peutEtreEcrase() {
        return false;
    }

    @Override
    public boolean peutServirDeSupport() {
        return true;
    }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }
}
