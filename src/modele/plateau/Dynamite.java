package modele.plateau;

public class Dynamite extends EntiteStatique {
    public Dynamite(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }

    @Override
    public Type getType() {
        return Type.DYNAMITE;
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
