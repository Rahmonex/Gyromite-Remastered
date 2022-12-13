package modele.plateau;

public class Mur extends EntiteStatique {
    public Mur(Jeu _jeu) { super(_jeu); }

    @Override
    public Type getType() {
        return Type.MUR;
    }

    @Override
    public boolean peutServirDeSupport() {
        return true;
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
