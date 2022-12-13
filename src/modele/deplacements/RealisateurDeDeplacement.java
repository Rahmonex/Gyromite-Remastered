package modele.deplacements;

import modele.plateau.EntiteDynamique;
import modele.plateau.Type;

import java.util.ArrayList;

/**
Tous les déplacement sont déclenchés par cette classe (gravité, controle clavier, IA, etc.)
 */
public abstract class RealisateurDeDeplacement {
    protected ArrayList<EntiteDynamique> lstEntitesDynamiques = new ArrayList<EntiteDynamique>();
    protected abstract boolean realiserDeplacement();
    public abstract Type getType();

    public void addEntiteDynamique(EntiteDynamique ed) {lstEntitesDynamiques.add(ed);};
    public void reinitialiserRealisateurDeDeplacement(){lstEntitesDynamiques = new ArrayList<EntiteDynamique>();}

    public abstract void resetDirection();

    public abstract Direction getDirectionCourante();

    public abstract void setDirectionCourante(Direction d);

    public abstract void setDirectionCourante(Direction d, Type t);
}
