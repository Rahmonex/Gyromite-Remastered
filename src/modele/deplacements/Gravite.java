package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.Type;

public class Gravite extends RealisateurDeDeplacement {
    @Override
    public boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {
            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            if (eBas == null || (eBas != null && !(eBas.peutServirDeSupport() || eBas.peutPermettreDeMonterDescendre()))) {
                if (e.avancerDirectionChoisie(Direction.bas))
                    ret = true;
            }
        }

        return ret;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public void resetDirection() {

    }

    @Override
    public Direction getDirectionCourante() {
        return null;
    }

    @Override
    public void setDirectionCourante(Direction d) {

    }

    @Override
    public void setDirectionCourante(Direction d, Type t) {

    }
}
