package modele.deplacements;

import modele.plateau.Jeu;
import modele.plateau.StatutJeu;
import modele.plateau.Type;
import modele.deplacements.Colonne;

import java.util.ArrayList;
import java.util.Observable;

import static java.lang.Thread.*;

public class Ordonnanceur extends Observable implements Runnable {
    private Jeu jeu;
    private ArrayList<RealisateurDeDeplacement> lstDeplacements = new ArrayList<RealisateurDeDeplacement>();
    private long pause;
    private boolean enJeu;
    public void add(RealisateurDeDeplacement deplacement) {
        lstDeplacements.add(deplacement);
    }

    public Ordonnanceur(Jeu _jeu) {
        jeu = _jeu;
    }

    public void start(long _pause) {
        pause = _pause;
        new Thread(this).start();
        enJeu = true;
    }
    public void mettreJeuEnPause(){
        enJeu = false;
    }
    public void reprendreJeu(){
        enJeu = true;
    }
    public void chargement(){
        //On reinitialise ici aussi
        mettreJeuEnPause();
        jeu.resetCmptDepl();
        lstDeplacements = new ArrayList<RealisateurDeDeplacement>();
    }

    @Override
    public void run() {
        boolean update = false;

        boolean colonnebouge = false;
        while (enJeu) {

            jeu.resetCmptDepl();

            colonnebouge = false;
            Direction directionColonne = null;
            Type typeColonne = null;

            for (RealisateurDeDeplacement d : lstDeplacements) {
                if (d.realiserDeplacement()) {
                    Type type = d.getType();
                    if (type == Type.COLONNE_BLEUE || type == Type.COLONNE_ORANGE) {
                        colonnebouge = true;
                        directionColonne = d.getDirectionCourante();
                        typeColonne = type;
                    }
                }
                update = true;
            }


            Controle4Directions.getInstance().resetDirection();
            IA.getInstance().resetDirection();
            Colonne.getInstance().resetDirection();

            if (colonnebouge)
                Colonne.getInstance().setDirectionCourante(directionColonne, typeColonne);


            if (update) {
                setChanged();
                notifyObservers();

            }
            try {

                if(jeu.getStatutJeu() == StatutJeu.JOUER){


                    if (jeu.getTemps() <= 0) {
                        jeu.setStatutJeu(StatutJeu.TEMPSFINNIT);
                        jeu.changerStatutJeu();
                    }
                    //diminuer le temps
                    jeu.setTemps(jeu.getTemps() - 1);

                }

                sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

