package modele.deplacements;

import modele.plateau.*;
import modele.plateau.Colonne;

/**
 * Controle4Directions permet d'appliquer une direction (connexion avec le clavier) à un ensemble d'entités dynamiques
 */
public class Controle4Directions extends RealisateurDeDeplacement {
    private Direction directionCourante;
    private Type type;
    private Direction directiontmp;

    private Direction directionColonneBleu;

    private Direction directionColonneOrange;
    // Design pattern singleton
    private static Controle4Directions c3d;

    public static void reinitialiserInstance(){
        c3d = null;
    }

    public static Controle4Directions getInstance() {
        if (c3d == null) {
            c3d = new Controle4Directions();
        }
        return c3d;
    }

    public void setDirectionCourante(Direction _directionCourante, Type t) {
        directionCourante = _directionCourante;
        type = t;
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (directionCourante != null) {
                if (e.getType() == type) {
                    if (e instanceof Heros) {
                        switch (directionCourante) {
                            case gauche:
                                Entite eBasGauche = e.regarderDansLaDirection(Direction.gauche);
                                e.setregarderDansLaDirection(Direction.gauche);

                                if (eBasGauche.getType() == Type.VIDE) {
                                    if (e.avancerDirectionChoisie(directionCourante))
                                        ret = true;

                                }
                                else{
                                    if (eBasGauche.getType()==Type.BOTAVECRADIS){
                                        if (e.avancerDirectionChoisie(directionCourante))
                                            ret = true;
                                    }
                                    if (eBasGauche.peutEtreEcrase()){

                                        if (e.avancerDirectionChoisie(directionCourante))
                                            ret = true;
                                    }
                                }
                                break;

                            case droite:

                                Entite eBasDroite = e.regarderDansLaDirection(Direction.droite);
                                e.setregarderDansLaDirection(Direction.droite);

                                if (eBasDroite.getType() == Type.VIDE) {
                                    if (e.avancerDirectionChoisie(directionCourante))
                                        ret = true;
                                } else {
                                    if (eBasDroite.getType()==Type.BOTAVECRADIS){
                                        if (e.avancerDirectionChoisie(directionCourante))
                                            ret = true;
                                    }
                                    if (eBasDroite.peutEtreEcrase()) {
                                        if (e.avancerDirectionChoisie(directionCourante))
                                            ret = true;
                                    }
                                }
                                break;

                            case haut:
                                // on ne peut pas sauter sans prendre appui
                                // (attention, test d'appui réalisé à partir de la position courante, si la gravité à été appliquée, il ne s'agit pas de la position affichée, amélioration possible)
                                Entite eBashaut = e.regarderDansLaDirection(Direction.bas);
                                if (eBashaut != null && (eBashaut.peutServirDeSupport() || eBashaut.peutPermettreDeMonterDescendre())) {
                                    if (e.avancerDirectionChoisie(Direction.haut))
                                        ret = true;
                                }
                                break;

                            case bas:

                                Entite eBasbas = e.regarderDansLaDirection(Direction.bas);
                                if (eBasbas != null && (eBasbas.peutPermettreDeMonterDescendre()) && !(eBasbas.peutServirDeSupport())) {
                                    if (e.avancerDirectionChoisie(Direction.bas))
                                        ret = true;
                                }

                                break;
                        }

                    }


                }
            }

        }

        return ret;

    }

    @Override
    public Type getType() {
        return null;
    }
    public void reset() {
        directionCourante = null;
        type = null;
    }

    @Override
    public void resetDirection() {
        directiontmp = directionCourante;
        directionCourante = null;
        type = null;
    }

    @Override
    public Direction getDirectionCourante() {
        return directionCourante;
    }

    @Override
    public void setDirectionCourante(Direction d) {

    }

    public Direction getDirectionCourante(Type type) {
        if (type == this.type) {
            return directionCourante;
        }
        return null;
    }

    public Direction getDirectiontmp(Type type) {
        return directiontmp;
    }

}
