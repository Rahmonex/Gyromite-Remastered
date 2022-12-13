package modele.deplacements;

import modele.plateau.*;

public class IA extends RealisateurDeDeplacement {
    private Direction directionCourante;
    private Type type;
    private Direction directiontmp;
    private static IA ia;


    public static IA getInstance() {
        if (ia == null) {
            ia = new IA();
        }
        return ia;
    }


    public void setDirectionCourante(Direction _directionCourante, Type t) {
        directionCourante = _directionCourante;
        type = t;
    }

    protected boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (directionCourante == null) {
                if (e.getType() == Type.BOT) {

                    if (e instanceof Bot) {

                        if (((Bot) e).getDirectionAvant() == null) {
                            //if the bot is not moving make it move in a random direction and if the bot is on a corde make it move on the corde
                            int random = (int) (Math.random() * 2);
                            switch (random) {
                                case 0:
                                    directionCourante = Direction.gauche;
                                    break;
                                case 1:
                                    directionCourante = Direction.droite;
                                    break;
                                case 2:
                                    directionCourante = Direction.haut;
                                    break;
                                case 3:
                                    directionCourante = Direction.bas;
                                    break;

                            }


                            Entite regard = e.regarderDansLaDirection(directionCourante);
                            Entite regarddiag = e.regarderDiagonaleBas(directionCourante);
                            e.setregarderDansLaDirection(directionCourante);
                            if (regarddiag.getType() != Type.VIDE) {
                                if ( regard.getType() == Type.VIDE) {
                                    if (e.avancerDirectionChoisie(directionCourante)) {
                                        e.setDirectionAvant(directionCourante);
                                        ret = true;
                                    }
                                } else {
                                    if  (regard.peutEtreEcrase()) {

                                            if (e.avancerDirectionChoisie(directionCourante)) {
                                                e.setDirectionAvant(directionCourante);
                                                ret = true;

                                        }
                                    }

                                }
                            }


                        }
                        //if the bot is moving make it move in the same direction
                        else {
                            Entite regardmoi = e.regarderAMaPosition(e);
                            Entite regard = e.regarderDansLaDirection(((Bot) e).getDirectionAvant());
                            Entite regarddiag = e.regarderDiagonaleBas(((Bot) e).getDirectionAvant());
                            e.setregarderDansLaDirection(((Bot) e).getDirectionAvant());
                            //if the bot is on a corde make it move on the corde
                            if (regardmoi.getType() == Type.CORDE) {
                                //move randomly on the corde or on the ground
                                int random = (int) (Math.random() * 4);
                                
                                switch (random) {
                                    case 0:
                                        directionCourante = Direction.gauche;
                                        break;
                                    case 1:
                                        directionCourante = Direction.droite;
                                        break;
                                    case 2:
                                        directionCourante = Direction.haut;
                                        break;
                                    case 3:
                                        directionCourante = Direction.bas;
                                        break;

                                }
                                Entite regard2 = e.regarderDansLaDirection(directionCourante);
                                Entite regarddiag2 = e.regarderDiagonaleBas(directionCourante);
                                e.setregarderDansLaDirection(directionCourante);
                                if (regarddiag2.getType() != Type.VIDE) {
                                    if (regard2.getType() == Type.VIDE) {
                                        if (e.avancerDirectionChoisie(directionCourante)) {
                                            e.setDirectionAvant(directionCourante);
                                            ret = true;
                                        }
                                    } else {
                                        if (regard2.peutEtreEcrase()) {

                                            if (e.avancerDirectionChoisie(directionCourante)) {
                                                e.setDirectionAvant(directionCourante);
                                                ret = true;

                                            }
                                        }

                                    }
                                }
                            } else {
                                if (regarddiag.getType() != Type.VIDE) {
                                    if (regard.getType() == Type.VIDE) {
                                        if (e.avancerDirectionChoisie(((Bot) e).getDirectionAvant())) {
                                            ret = true;
                                        }
                                    } else if (regard.getType() == Type.CORDE) {
                                        int random = (int) (Math.random() * 2);
                                        switch (random) {
                                            case 0:
                                                directionCourante = Direction.gauche;
                                                break;
                                            case 1:
                                                directionCourante = Direction.droite;
                                                break;
                                            case 2:
                                                directionCourante = Direction.haut;
                                                break;
                                            case 3:
                                                directionCourante = Direction.bas;
                                                break;

                                        }
                                        if (e.avancerDirectionChoisie(directionCourante)) {
                                            e.setDirectionAvant(directionCourante);
                                            ret = true;
                                        }
                                    } else {
                                        if (regard.peutEtreEcrase()) {
                                            if (e.avancerDirectionChoisie(e.getDirectionAvant())) {
                                                ret = true;
                                            }
                                        } else {
                                            ((Bot) e).setDirectionAvant(null);
                                        }
                                    }
                                } else {
                                    ((Bot) e).setDirectionAvant(null);
                                }

                            }
                        }
                    }
                }
            }

        }

        return ret;

    }

    @Override
    public void resetDirection() {
        directiontmp = directionCourante;
        directionCourante = null;
        type = null;
    }

    @Override
    public Direction getDirectionCourante() {
        return this.directionCourante;
    }

    @Override
    public void setDirectionCourante(Direction d) {
        this.directionCourante = d;
    }

    @Override
    public Type getType() {
        return null;
    }

    public Direction getDirectiontmp(Type type) {
        return directiontmp;
    }
}
