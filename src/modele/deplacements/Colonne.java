package modele.deplacements;

import modele.plateau.*;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class Colonne extends RealisateurDeDeplacement {


    private Direction directiontmp;
    private boolean colonneBouge = false;

    private Direction directionCourante;

    Type colonneType = null;

    private static Colonne colonne;

    public static Colonne getInstance() {
        if (colonne == null) {
            colonne = new Colonne();
        }
        return colonne;
    }

    protected boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (directionCourante != null) {
                if (e.getType() == Type.COLONNE_ORANGE || e.getType() == Type.COLONNE_BLEUE) {
                    if (e instanceof modele.plateau.Colonne) {
                        switch (directionCourante) {
                            case haut:
                                Entite eTop = e.regarderDansLaDirection(Direction.haut);
                                if (eTop != null && !(eTop.peutServirDeSupport()) && !(eTop instanceof modele.plateau.Colonne)) {
                                    if (e.avancerDirectionChoisie(Direction.haut)) {
                                        Entite temp = e.regarderDansLaDirection(Direction.haut);
                                        Entite eBas = temp.regarderDansLaDirection(Direction.bas);

                                        while ((eBas instanceof modele.plateau.Colonne) && ((modele.plateau.Colonne) eBas).avancerDirectionChoisie(Direction.haut)) {
                                            temp = eBas.regarderDansLaDirection(Direction.haut);
                                            eBas = temp.regarderDansLaDirection(Direction.bas);
                                        }
                                        ret = true;
                                    }
                                }
                                break;
                            case bas:
                                Entite eBas = e.regarderDansLaDirection(Direction.bas);
                                if (eBas != null && !(eBas.peutServirDeSupport()) && !(eBas instanceof modele.plateau.Colonne)) {
                                    if (e.avancerDirectionChoisie(Direction.bas)) {
                                        Entite temp = e.regarderDansLaDirection(Direction.haut);
                                        Entite eTop2 = temp.regarderDansLaDirection(Direction.haut);

                                        while ((eTop2 instanceof modele.plateau.Colonne) && ((modele.plateau.Colonne) eTop2).avancerDirectionChoisie(Direction.bas)) {
                                            temp = eTop2.regarderDansLaDirection(Direction.haut);
                                            eTop2 = temp.regarderDansLaDirection(Direction.haut);
                                        }
                                        ret = true;
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }





        /*for (EntiteDynamique e : lstEntitesDynamiques) {
            if (e.getType() == Type.COLONNE_ORANGE || e.getType() == Type.COLONNE_BLEUE) {

                colonneBouge = true;
                colonneType = e.getType();
                directionColonne = getDirectionCourante();
                if (colonneBouge){
                    //Controle4Directions.getInstance().setDirectionCourante(directionColonne, colonneType);
                    //jeu.setDirectionRegardColonne();
                    ret = true;
                }

            }

        }*/


        return ret;
    }
    public boolean getColonneBouge() {
        return this.colonneBouge;
    }

    public Type getTypeColonne() {
        return colonneType;
    }

    @Override
    public Type getType() {
        return null;
    }



    @Override
    public void resetDirection() {
        directionCourante = null;
    }

    @Override
    public Direction getDirectionCourante() {
        return directionCourante;
    }

    @Override
    public void setDirectionCourante(Direction d) {
        directionCourante = d;
    }

    @Override
    public void setDirectionCourante(Direction d, Type t) {
        directionCourante = d;
        colonneType = t;
    }
}
