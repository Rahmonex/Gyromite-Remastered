package modele.plateau;

public class SolTerre extends EntiteStatique {
    public SolTerre(Jeu _jeu) { super(_jeu); }

    @Override
    public Type getType() {
        return Type.SOLTERRE;
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
