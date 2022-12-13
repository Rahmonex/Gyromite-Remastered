/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import VueControleur.VueControleurGyromite;
import modele.deplacements.*;
import VueControleur.VueControleurGyromite;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Actuellement, cette classe gère les postions
 * (ajouter conditions de victoire, chargement du plateau, etc.)
 */
public class Jeu {

    public static final int SIZE_X = 40;
    public static final int SIZE_Y = 20;

    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private ArrayList<Bot> grillesBot = new ArrayList<Bot>(); //array of all the smicks

    private ArrayList<Colonne> grillesColonne = new ArrayList<Colonne>(); //array of all the columns
    private Heros hector;

    private Bot smik;

    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][][] grilleEntites; // permet de récupérer une entité à partir de ses coordonnées
    private Point dimensionNiveau;
    private int niveauActuel = 1;
    private int vie = 5;
    private int nbDynamites = 0;
    private boolean possedeUnRadis;
    private StatutJeu statutJeu = StatutJeu.DEBUT;
    private int temps = 999;
    private int score = 0;
    private Gravite g=null;
    private boolean nivDejaCharge = false;

    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);


    public Jeu() {
        //initialisationDesEntites();
        System.out.println("Appuyer sur Entree pour commencer");
        this.dimensionNiveau = calculDimensionNiveau(niveauActuel);

        this.grilleEntites = new Entite[dimensionNiveau.x][dimensionNiveau.y][2];

        chargerNiveau(1);

    }

    private Point calculDimensionNiveau(int numeroNiveau){

        System.out.println("En cours de chargement du niveau " + numeroNiveau);

        int levelWidth = 0;
        int levelHeight = 0;

        InputStream fileTravaux = getClass().getResourceAsStream("/DispositionNiveau/niveau"+numeroNiveau+".txt");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileTravaux));
            String line = null;
            int i=0;
            while((line = reader.readLine()) != null) {
                String[] singles = line.split(" ");
                levelWidth = singles.length;
                for(int j=0;j<singles.length;j++) {
                }
                i++;
            }
            levelHeight = i;

        } catch(IOException e) {
            System.err.println("Erreur au niveau "+numeroNiveau);
        }

        return new Point(levelHeight, levelWidth);
    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }
    
    public Entite[][][] getGrille() {
        return grilleEntites;
    }
    
    public Heros getHector() {
        return this.hector;
    }


    public void chargerNiveau(int numeroNiveau){

        Controle4Directions.reinitialiserInstance();

        InputStream fileTravaux = getClass().getResourceAsStream("/DispositionNiveau/niveau"+numeroNiveau+".txt");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileTravaux));
            String line = null;
            int i=0;
            while((line = reader.readLine()) != null) {
                String[] singles = line.split(" ");
                for(int j=0;j<singles.length;j++) {
                    switch(singles[j]) {
                        case "1":
                            addEntite(new Mur(this), j, i, 1);
                            break;
                        case "2":
                            addEntite(new Sol(this), j, i, 1);
                            break;
                        case "3":
                            addEntite(new SolTerre(this), j, i, 1);
                            break;
                        case "0":
                            addEntite(new Vide(this), j, i, 1);
                            break;
                        case "C":
                            addEntite(new Corde(this), j, i, 1);
                            break;
                        case "I":
                            Colonne col = new Colonne(this, Type.COLONNE_BLEUE);
                            addEntite(col, j, i, 1);
                            grillesColonne.add(col);
                            break;
                       case "O":
                            Colonne col2 = new Colonne(this, Type.COLONNE_ORANGE);
                            addEntite(col2, j, i, 1);
                            grillesColonne.add(col2);
                            break;
                        case "D":
                            addEntite(new Dynamite(this), j, i, 0);
                            addEntite(new Vide(this), j, i, 1);
                            this.nbDynamites++;
                            break;
                        case "B":
                            smik = new Bot(this);
                            addEntite(smik, j, i, 0);
                            addEntite(new Vide(this), j, i, 1);
                            grillesBot.add(smik);
                            break;
                        case "R":
                            addEntite(new Radis(this), j, i, 0);
                            addEntite(new Vide(this), j, i, 1);
                            break;
                        case "H":
                            hector = new Heros(this);
                            addEntite(hector, j, i, 0);
                            this.hector = (Heros) this.grilleEntites[j][i][0];
                            break;
                    }
                }
                i++;

            }

            setDirectionRegardColonne();
            if (g==null){

                g = new Gravite();
            }
            else {
                g.reinitialiserRealisateurDeDeplacement();
            }
            g.addEntiteDynamique(hector);

            for(Bot bot : grillesBot) {
                g.addEntiteDynamique(bot);
                IA.getInstance().addEntiteDynamique(bot);
                ordonnanceur.add(IA.getInstance());
            }

            for(Colonne col : grillesColonne) {
                modele.deplacements.Colonne.getInstance().addEntiteDynamique(col);
                ordonnanceur.add(modele.deplacements.Colonne.getInstance());
            }

            setDirectionRegardColonne();

            ordonnanceur.add(g);

            Controle4Directions.getInstance().addEntiteDynamique(this.hector);
            ordonnanceur.add(Controle4Directions.getInstance());


        } catch(IOException e) {
            System.err.println("Erreur au niveau"+numeroNiveau);
        }
    }
    private void reinitialiserComposants(){
        nbDynamites = 0;
        score = 0;
        vie=5;
        temps = 999;
        cmptDeplH = new HashMap<Entite, Integer>();
        cmptDeplV = new HashMap<Entite, Integer>();
        hector = null;
        map = new HashMap<Entite, Point>();
        grillesBot = new ArrayList<Bot>();
        grilleEntites = null;
        dimensionNiveau = null;
    }

    private void chargerNiveauSuivant(int niveauSuivant){


        if(getClass().getResourceAsStream("/DispositionNiveau/niveau"+niveauSuivant+".txt") == null){
            setStatutJeu(StatutJeu.FINNIT);
            return;
        }

        niveauActuel = niveauSuivant;
        reinitialiserComposants();

        this.ordonnanceur.chargement();

        this.dimensionNiveau = calculDimensionNiveau(niveauActuel);

        this.grilleEntites = new Entite[dimensionNiveau.x][dimensionNiveau.y][2];

        chargerNiveau(niveauActuel);

        nivDejaCharge=true;

    }


    private void addEntite(Entite e, int x, int y, int z) {
        grilleEntites[x][y][z] = e;
        map.put(e, new Point(x, y));
    }
    
    /** Permet par exemple a une entité  de percevoir sont environnement proche et de définir sa stratégie de déplacement
     *
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetStatiqueALaPosition(calculerPointCible(positionEntite, d));
    }

    public Entite regarderDiagonaleBas(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetStatiqueALaPosition(calculerPointCibleDiag(positionEntite, d));
    }
    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = false;
        
        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);
        
        if (contenuDansGrille(pCible) && !(objetStatiqueALaPosition(pCible).peutServirDeSupport())) { // a adapter (collisions murs, etc.) --> ahjouter peutServir de support ?
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité
            switch (d) {
                case bas:
                case haut:
                    if (cmptDeplV.get(e) == null) {
                        cmptDeplV.put(e, 1);
                        retour = true;
                    }
                    break;
                case gauche:
                case droite:
                    if (cmptDeplH.get(e) == null) {
                        cmptDeplH.put(e, 1);
                        retour = true;

                    }
                    break;
            }
        }

        if (retour || e instanceof Bot || grilleEntites[pCible.x][pCible.y][0] instanceof Dynamite) {//Rajouter peut etre Radis ?
            ((EntiteDynamique) e).setregarderDansLaDirection(d);//A tester encore
            deplacerEntite(pCourant, pCible, e,d);
        }

        return retour;
    }
    
    
    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
        
        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;     
            
        }
        
        return pCible;
    }

    private Point calculerPointCibleDiag(Point pCourant, Direction d) {
        Point pCible = null;

        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y + 1); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y + 1); break;

        }

        return pCible;
    }

    
    private void deplacerEntite(Point pCourant, Point pCible, Entite e,Direction d) {
        nivDejaCharge= false;

        if(vie<0){
            vie ++;
        }
        /** we need to add the professor interaction with dynamite and Radish
         */
        if(grilleEntites[pCourant.x][pCourant.y][0] instanceof Heros){
            if(grilleEntites[pCible.x][pCible.y][0] instanceof Dynamite){
                nbDynamites --;

                //Augmenter le score mais de combien ?
                score+=50;

                if (nbDynamites == 0){
                    //Niveau suivant
                    chargerNiveauSuivant(++niveauActuel); //Bug sur le chargement ?? Affiche Heros 2 fois et applique pas la gravite sur nouveau Heros -_-
                    //
                }
            }
            if(grilleEntites[pCible.x][pCible.y][0] instanceof Bot){
                if(grilleEntites[pCible.x][pCible.y][0].getType()==Type.BOT) {

                    if((!possedeUnRadis) && (vie>0)){
                    System.out.println("Vous avez été tué par le smick");
                    vie--;
                    return;

                }
                    if(possedeUnRadis){
                        possedeUnRadis = false;
                        //On change type de heros et de bot
                        ((Heros) grilleEntites[pCourant.x][pCourant.y][0]).setType(Type.HEROS);
                        ((Bot) grilleEntites[pCible.x][pCible.y][0]).setType(Type.BOTAVECRADIS);
                        System.out.println("Le smick mange le radis");
                        return;
                    }
                    if(vie<=1){
                        setStatutJeu(StatutJeu.FINNIT);
                        changerStatutJeu();
                        return;
                    }
                }

                if(grilleEntites[pCible.x][pCible.y][0].getType()==Type.BOTAVECRADIS) {
                    System.out.println("Le smick est occupé a mangé le radis");
                    return;
                }

            }

        }

        if(grilleEntites[pCourant.x][pCourant.y][0] instanceof  Heros){
            if(grilleEntites[pCible.x][pCible.y][0] instanceof  Radis){
                if(!possedeUnRadis){
                    possedeUnRadis = true;
                    ((Heros) grilleEntites[pCourant.x][pCourant.y][0]).setType(Type.HEROSAVECRADIS);
                    System.out.println("le personnage a un radis :  "+possedeUnRadis);
                }
            }
        }

        if(!nivDejaCharge){
            grilleEntites[pCible.x][pCible.y][0] = e;
            map.put(grilleEntites[pCourant.x][pCourant.y][0],pCourant);
            map.put(e, pCible);
        }
        grilleEntites[pCourant.x][pCourant.y][0] = null;

    }
    
    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }


    
    private Entite objetStatiqueALaPosition(Point p) {
        Entite retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y][1];
        }
        
        return retour;
    }

    private Entite objetDynamiqueALaPosition(Point p) {
        Entite retour = null;

        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y][0];
        }

        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }
    public void changerStatutJeu(){
        switch(statutJeu){
            case DEBUT:
                setStatutJeu(StatutJeu.JOUER);
                break;
            case PAUSE:
                System.out.println("Le Jeu Reprend");
                setStatutJeu(StatutJeu.JOUER);
                break;
            case FINNIT:
                chargerNiveauSuivant(1);
                setStatutJeu(StatutJeu.DEBUT);
                System.out.println("Jeu Finnit !");
                break;
            case JOUER:
                System.out.println("Le jeu commence");
                //En Jeu
                break;
            case TEMPSFINNIT:
                System.out.println("Temps Ecoulé !");
                setStatutJeu(StatutJeu.FINNIT);
                break;
        }
    }

    public void setDirectionRegardColonne(){// Pour definir la partie de la colonne !! jespere que cava marcherr comme ca la colonne est plus belle
        for(int i=0;i<dimensionNiveau.x;i++){
            for(int j=0;j<dimensionNiveau.y;j++){
                if(grilleEntites[i][j][1] instanceof Colonne){
                    //if the column is at the border of the map, it is either a top or a bottom
                    if(i == 0){

                        ((Colonne) grilleEntites[i][j][1]).setregarderDansLaDirection(Direction.bas);
                    }

                    if(i == dimensionNiveau.x-1) {

                        ((Colonne) grilleEntites[i][j][1]).setregarderDansLaDirection(Direction.haut);
                    }

                    else{
                        //if it is a column above and there's still a column under
                        if(grilleEntites[i][j-1][1] instanceof Colonne){
                            if(grilleEntites[i][j+1][1] instanceof Colonne)
                                ((Colonne) grilleEntites[i][j][1]).setregarderDansLaDirection(null);
                        }

                        if(!(grilleEntites[i][j+1][1] instanceof Colonne)) {
                            ((Colonne) grilleEntites[i][j][1]).setregarderDansLaDirection(Direction.bas);
                        }

                        if(!(grilleEntites[i][j-1][1] instanceof Colonne)) {
                            ((Colonne) grilleEntites[i][j][1]).setregarderDansLaDirection(Direction.haut);
                        }

                    }
                }
            }
        }
    }

    public int getTemps(){ return this.temps; }
    public void setTemps(int temps ){ this.temps = temps;}
    public int getScore(){ return this.score; }

    public StatutJeu getStatutJeu(){ return this.statutJeu; }
    public void setStatutJeu(StatutJeu js){
        statutJeu = js;
    }


    public Entite regarderAMaPosition(Entite entiteDynamique) {
        Point p = map.get(entiteDynamique);
        return objetStatiqueALaPosition(p);
    }

    public int getNbDynamites(){
        return  this.nbDynamites;
    }
    public int getVieRestante(){
        return  this.vie;
    }

    public int getHauteur() {
        return SIZE_Y;
    }

    public int getX(Entite entite) {
        return map.get(entite).x;
    }

    public int getY(Entite entite) {
        return map.get(entite).y;
    }
}
