package VueControleur;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;

import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.deplacements.IA;
import modele.deplacements.Gravite;
import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleurGyromite extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;
    private boolean menuPrincipal = false;

    public ImageIcon icoHeros;
    public ImageIcon icoHerosDroite;
    public ImageIcon icoHerosGauche;
    public ImageIcon icoHerosSaut;
    public ImageIcon icoHerosRadis;
    public ImageIcon icoHerosRadisDroite;
    public ImageIcon icoHerosRadisGauche;
    public ImageIcon icoHerosRadisSaut;
    public ImageIcon icoHerosGrimpe;
    public ImageIcon icoHerosGrimpeMove;
    public ImageIcon icoDynamite;
    public ImageIcon icoRadis;
    public ImageIcon icoBot;
    public ImageIcon icoBotRadis;
    public ImageIcon icoBotDroite;
    public ImageIcon icoBotGauche;
    public ImageIcon icoBotHaut;
    public ImageIcon icoBotBas;
    public ImageIcon icoSol;
    public ImageIcon icoSolTerre;
    public ImageIcon icoCorde;
    public ImageIcon icoVide;
    public ImageIcon icoMur;
    public ImageIcon icoColonneOrangeTete;
    public ImageIcon icoColonneOrangeCorps;
    public ImageIcon icoColonneOrangeBas;
    public ImageIcon icoColonneBleueTete;
    public ImageIcon icoColonneBleueCorps;
    public ImageIcon icoColonneBleueBas;
    private Direction directionColonneBleue = Direction.bas;
    private Direction directionColonneOrange = Direction.bas;
    private JMenuItem score;
    private JMenuItem temps ;
    private JMenuItem vie;
    private JMenuItem dynamites;


    private int resultatscore;
    private int dynamitesRestants;
    private int tempsrestant;
    private int vierestante;

    private boolean BleuUP = false;
;
    private boolean OrangeUP = false;




    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleurGyromite(Jeu _jeu) {
        //sizeX = _jeu.SIZE_X;
        //sizeY = _jeu.SIZE_Y;
        sizeX = 20;
        sizeY = 20;
        jeu = _jeu;

        //ImagesActions i = new ImagesActions();
        //this.imagesActions = i.getImagesAction();
        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();

    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : Controle4Directions.getInstance().setDirectionCourante(Direction.gauche,jeu.getHector().getType()); break;
                    case KeyEvent.VK_RIGHT : Controle4Directions.getInstance().setDirectionCourante(Direction.droite,jeu.getHector().getType()); break;
                    case KeyEvent.VK_DOWN : Controle4Directions.getInstance().setDirectionCourante(Direction.bas,jeu.getHector().getType()); break;
                    case KeyEvent.VK_UP : Controle4Directions.getInstance().setDirectionCourante(Direction.haut,jeu.getHector().getType()); break;
                    //Il faut appuyer sur entrer Pour que le Jeu demarre
                    //Apres peut etre rajouter Home Scree net changer ca
                    case KeyEvent.VK_ENTER:
                        if(!(jeu.getStatutJeu() == StatutJeu.JOUER)) {
                            jeu.changerStatutJeu();
                            System.out.println(jeu.getStatutJeu());
                        }
                        break;
                    case KeyEvent.VK_P:
                        if(jeu.getStatutJeu() == StatutJeu.JOUER){
                            jeu.setStatutJeu(StatutJeu.PAUSE);
                            System.out.println(jeu.getStatutJeu());
                        }
                        break;
                    //Appuyer sur E pour monter ou descendre la colonne beleue
                    case KeyEvent.VK_E:
                        if (!BleuUP) {
                            modele.deplacements.Colonne.getInstance().setDirectionCourante(Direction.haut, modele.plateau.Type.COLONNE_BLEUE);
                            BleuUP = true;
                        }
                        else {
                            modele.deplacements.Colonne.getInstance().setDirectionCourante(Direction.bas, modele.plateau.Type.COLONNE_BLEUE);
                            BleuUP = false;
                        }
                        break;
                    //Appuyer sur R pour monter ou descendre la colonne Orange
                    case KeyEvent.VK_R:
                        if (!OrangeUP) {
                            OrangeUP = true;
                            modele.deplacements.Colonne.getInstance().setDirectionCourante(Direction.haut, modele.plateau.Type.COLONNE_ORANGE);
                        }
                        else {
                            OrangeUP = false;
                            modele.deplacements.Colonne.getInstance().setDirectionCourante(Direction.bas, modele.plateau.Type.COLONNE_ORANGE);
                        }
                        break;
                }
            }
        });
    }

    private Direction changerDirectionColonneBleue(){
        if(directionColonneBleue == Direction.haut) {
            directionColonneBleue = Direction.bas;
        }
        else {
            directionColonneBleue = Direction.haut;
            System.out.println("hola");
        }
        return directionColonneBleue;
    }

    private Direction changerDirectionColonneOrange(){
        if(directionColonneOrange == Direction.haut) {
            directionColonneOrange = Direction.bas;
        }
        else {
            directionColonneOrange = Direction.haut;
            System.out.println("holee");
        }
        return directionColonneOrange;
    }


    private void placerLesComposantsGraphiques() {
        setTitle("Gyromite");
        setSize((sizeX*(sizeX-1)*2)-2, (sizeY*sizeY*2)+27);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre


        //Score + Timer + Life Header
        JMenuBar trackBar = new JMenuBar();

        //Recuperer els valuers
        tempsrestant = jeu.getTemps();
        resultatscore = jeu.getScore();
        vierestante = jeu.getVieRestante();
        dynamitesRestants = jeu.getNbDynamites();

        vie = new JMenuItem("Vie : " + jeu.getVieRestante());
        dynamites = new JMenuItem("Dynamites : "+ dynamitesRestants);
        score = new JMenuItem("Score : " + resultatscore);
        temps = new JMenuItem("Temps : "+ tempsrestant);



        vie.setBackground(Color.black);
        dynamites.setBackground(Color.black);
        score.setBackground(Color.black);
        temps.setBackground(Color.black);
        vie.setForeground(Color.gray);
        dynamites.setForeground(Color.gray);
        score.setForeground(Color.gray);
        temps.setForeground(Color.gray);
        trackBar.add(vie);
        vie.setIcon(chargerIcone("Images/sprites.png", 92, 3, 20, 24));
        trackBar.add(dynamites);
        dynamites.setIcon(chargerIcone("Images/sprites.png",18,252,20,24));
        trackBar.add(score);
        trackBar.add(temps);
        trackBar.setAlignmentX(CENTER_ALIGNMENT);
        trackBar.add(Box.createRigidArea(new Dimension(0,20)));
        trackBar.setBorder(new CompoundBorder(null,null));
        setJMenuBar(trackBar);

        //Panel
        JPanel panel= new JPanel(new GridLayout(sizeY, sizeX,0,0));
        JComponent grilleJLabels = panel;; // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille
        tabJLabel = new JLabel[sizeX][sizeY];
        setResizable(true);

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();

                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }


    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     *
     */

    private void mettreAJourAffichage(){
        switch(jeu.getStatutJeu()){
            case DEBUT:
                affichagemenuPrincipal();
                updateMenuBarValues();
                break;
            case PAUSE:
                affichagemenuPrincipal();
                break;
            case JOUER :
                affichageEnJeu();
                break;
            case FINNIT:
                System.out.println("Jeu finnit");
                break;
        }
    }

    private void affichagemenuPrincipal(){
        // murs extérieurs horizontaux
        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY; j++){

                if(j == 19){
                    tabJLabel[i][j].setIcon((icoSolTerre));
                }
                else {
                    tabJLabel[i][j].setIcon(icoVide);

                }
                tabJLabel[i][0].setIcon((icoSol));
                tabJLabel[0][j].setIcon(icoMur);
                tabJLabel[19][j].setIcon(icoMur);
                //START Text :
                tabJLabel[6][3].setIcon(chargerIcone("Images/Letters/S.png",0,0,27,27));
                tabJLabel[7][3].setIcon(chargerIcone("Images/Letters/T.png",0,0,27,27));
                tabJLabel[8][3].setIcon(chargerIcone("Images/Letters/A.png",0,0,27,27));
                tabJLabel[9][3].setIcon(chargerIcone("Images/Letters/R.png",0,0,27,27));
                tabJLabel[10][3].setIcon(chargerIcone("Images/Letters/T.png",0,0,27,27));
                tabJLabel[11][3].setIcon(chargerIcone("Images/Letters/2points.png",0,0,27,27));
                tabJLabel[12][3].setIcon(chargerIcone("Images/Letters/Enter.png",0,0,27,27));

                //COMMANDS Text :
                tabJLabel[4][8].setIcon(chargerIcone("Images/Letters/C.png",0,0,27,27));
                tabJLabel[5][8].setIcon(chargerIcone("Images/Letters/O.png",0,0,27,27));
                tabJLabel[6][8].setIcon(chargerIcone("Images/Letters/M.png",0,0,27,27));
                tabJLabel[7][8].setIcon(chargerIcone("Images/Letters/M.png",0,0,27,27));
                tabJLabel[8][8].setIcon(chargerIcone("Images/Letters/A.png",0,0,27,27));
                tabJLabel[9][8].setIcon(chargerIcone("Images/Letters/N.png",0,0,27,27));
                tabJLabel[10][8].setIcon(chargerIcone("Images/Letters/D.png",0,0,27,27));
                tabJLabel[11][8].setIcon(chargerIcone("Images/Letters/S.png",0,0,27,27));
                tabJLabel[12][8].setIcon(chargerIcone("Images/Letters/2points.png",0,0,27,27));
                tabJLabel[13][8].setIcon(chargerIcone("Images/Letters/P.png",0,0,27,27));

                //EXIT Text :
                tabJLabel[6][13].setIcon(chargerIcone("Images/Letters/E.png",0,0,27,27));
                tabJLabel[7][13].setIcon(chargerIcone("Images/Letters/X.png",0,0,27,27));
                tabJLabel[8][13].setIcon(chargerIcone("Images/Letters/I.png",0,0,27,27));
                tabJLabel[9][13].setIcon(chargerIcone("Images/Letters/T.png",0,0,27,27));
                tabJLabel[10][13].setIcon(chargerIcone("Images/Letters/2points.png",0,0,27,27));
                tabJLabel[11][13].setIcon(chargerIcone("Images/Letters/Esc.png",0,0,27,27));


            }
        }

    }
    private void affichageEnJeu() {
        updateMenuBarValues();
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {

                if (jeu.getGrille()[x][y][0] instanceof Heros) {// si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue

                    Direction directionCourante = Controle4Directions.getInstance().getDirectiontmp(jeu.getHector().getType());
                    if(jeu.getGrille()[x][y][0].getType() == modele.plateau.Type.HEROS){

                        if (directionCourante == Direction.droite) {
                            tabJLabel[x][y].setIcon(icoHerosDroite);

                        } else if (directionCourante == Direction.gauche) {
                            tabJLabel[x][y].setIcon(icoHerosGauche);

                        } else if (directionCourante == Direction.haut) {
                            if (jeu.getGrille()[x][y][1] instanceof Corde) {
                                tabJLabel[x][y].setIcon(icoHerosGrimpeMove);
                            } else {
                                tabJLabel[x][y].setIcon(icoHerosSaut);
                            }
                        }
                        else if (directionCourante == Direction.bas) {
                            if (jeu.getGrille()[x][y][1] instanceof Corde) {
                                tabJLabel[x][y].setIcon(icoHerosGrimpeMove);
                            } else {
                                tabJLabel[x][y].setIcon(icoHeros);
                            }
                        } else {
                            if (jeu.getGrille()[x][y][1] instanceof Corde) {
                                tabJLabel[x][y].setIcon(icoHerosGrimpe);
                            } else {
                                tabJLabel[x][y].setIcon(icoHeros);
                            }
                        }
                    }
                    if(jeu.getGrille()[x][y][0].getType() == modele.plateau.Type.HEROSAVECRADIS){

                        if (directionCourante == Direction.droite) {
                            tabJLabel[x][y].setIcon(icoHerosRadisDroite);

                        } else if (directionCourante == Direction.gauche) {
                            tabJLabel[x][y].setIcon(icoHerosRadisGauche);

                        } else if (directionCourante == Direction.haut) {
                            if (jeu.getGrille()[x][y][1] instanceof Corde) {
                                tabJLabel[x][y].setIcon(icoHerosGrimpeMove);
                            } else {
                                tabJLabel[x][y].setIcon(icoHerosRadisSaut);
                            }
                        }
                        else if (directionCourante == Direction.bas) {
                            if (jeu.getGrille()[x][y][1] instanceof Corde) {
                                tabJLabel[x][y].setIcon(icoHerosGrimpeMove);
                            } else {
                                tabJLabel[x][y].setIcon(icoHerosRadis);
                            }
                        } else {
                            if (jeu.getGrille()[x][y][1] instanceof Corde) {
                                tabJLabel[x][y].setIcon(icoHerosGrimpe);
                            } else {
                                tabJLabel[x][y].setIcon(icoHerosRadis);
                            }
                        }
                    }

                    // si transparence : images avec canal alpha + dessins manuels (voir ci-dessous + créer composant qui redéfinie paint(Graphics g)), se documenter
                    //BufferedImage bi = getImage("Images/smick.png", 0, 0, 20, 20);
                    //tabJLabel[x][y].getGraphics().drawImage(bi, 0, 0, null);

                } else if (jeu.getGrille()[x][y][0] instanceof Bot) {


                    Direction directionCourante = ((Bot) jeu.getGrille()[x][y][0]).getDirectionAvant();

                    if (directionCourante == Direction.droite) {
                        tabJLabel[x][y].setIcon(icoBotDroite);

                    } else if (directionCourante == Direction.gauche) {
                        tabJLabel[x][y].setIcon(icoBotGauche);
                    }
                    else if (directionCourante == Direction.haut) {
                        tabJLabel[x][y].setIcon(icoBotHaut);
                    }
                    else if (directionCourante == Direction.bas) {
                        tabJLabel[x][y].setIcon(icoBotBas);
                    }
                    else {
                        tabJLabel[x][y].setIcon(icoBot);
                    }

                    if(jeu.getGrille()[x][y][0].getType()==modele.plateau.Type.BOTAVECRADIS) {
                        if (directionCourante == Direction.droite) {
                            tabJLabel[x][y].setIcon(icoBotRadis);

                        } else if (directionCourante == Direction.gauche) {
                            tabJLabel[x][y].setIcon(icoBotRadis);
                        }
                        else if (directionCourante == Direction.haut) {
                            tabJLabel[x][y].setIcon(icoBotRadis);
                        }
                        else if (directionCourante == Direction.bas) {
                            tabJLabel[x][y].setIcon(icoBotRadis);
                        }
                        else {
                            tabJLabel[x][y].setIcon(icoBotRadis);
                        }
                    }


                } else if (jeu.getGrille()[x][y][1] instanceof Mur) {
                    tabJLabel[x][y].setIcon(icoMur);
                } else if (jeu.getGrille()[x][y][0] instanceof Dynamite) {
                    tabJLabel[x][y].setIcon(icoDynamite);
                } else if (jeu.getGrille()[x][y][0] instanceof Radis) {
                    tabJLabel[x][y].setIcon(icoRadis);
                } else if (jeu.getGrille()[x][y][1] instanceof Sol) {
                    tabJLabel[x][y].setIcon(icoSol);
                } else if (jeu.getGrille()[x][y][1] instanceof SolTerre) {
                    tabJLabel[x][y].setIcon(icoSolTerre);
                } else if (jeu.getGrille()[x][y][1] instanceof Corde) {
                    tabJLabel[x][y].setIcon(icoCorde);
                } else if (jeu.getGrille()[x][y][1] instanceof Colonne) {


                    Direction directionCourante = ((Colonne) jeu.getGrille()[x][y][1]).getregarderDansLaDirection();

                    if(jeu.getGrille()[x][y][1].getType() == modele.plateau.Type.COLONNE_ORANGE) {
                        if (directionCourante == Direction.haut) {
                            tabJLabel[x][y].setIcon(icoColonneOrangeTete);

                        } else if (directionCourante == Direction.bas) {
                            tabJLabel[x][y].setIcon(icoColonneOrangeBas);
                        }
                        else {
                            tabJLabel[x][y].setIcon(icoColonneOrangeCorps);
                        }
                    }
                    if(jeu.getGrille()[x][y][1].getType() == modele.plateau.Type.COLONNE_BLEUE) {
                        if (directionCourante == Direction.haut) {
                            tabJLabel[x][y].setIcon(icoColonneBleueTete);

                        } else if (directionCourante == Direction.bas) {
                            tabJLabel[x][y].setIcon(icoColonneBleueBas);
                        }
                        else {
                            tabJLabel[x][y].setIcon(icoColonneBleueCorps);
                        }
                    }

                } else {
                    tabJLabel[x][y].setIcon(icoVide);
                }
            }
        }
    }


    private void chargerLesIcones() {
        //Changer icones et separer sol et mur
        //Heros
        icoHeros = chargerIcone("Images/sprites.png", 92, 3, 20, 24);
        icoHerosDroite = chargerIcone("Images/sprites.png", 59, 156, 20, 24);
        icoHerosGauche = chargerIcone("Images/sprites.png", 73, 3, 19, 24);
        icoHerosGrimpe = chargerIcone("Images/sprites.png", 1, 54, 20, 24);
        icoHerosGrimpeMove = chargerIcone("Images/sprites.png", 18, 54, 20, 24);
        icoHerosSaut = chargerIcone("Images/sprites.png", 55, 3, 20, 24);

        //Heros Avec Radis
        icoHerosRadis = chargerIcone("Images/sprites.png", 23, 354, 20, 25);
        icoHerosRadisDroite = chargerIcone("Images/sprites.png", 1, 354, 22, 24);
        icoHerosRadisGauche = chargerIcone("Images/sprites.png", 42, 354, 22, 24);
        icoHerosRadisSaut = chargerIcone("Images/sprites.png", 61, 354, 21, 24);

        //Bot
        icoBotRadis = chargerIcone("Images/sprites.png", 85, 357, 20, 17);
        icoBot = chargerIcone("Images/sprites.png", 3, 140, 20, 17);
        icoBotGauche = chargerIcone("Images/sprites.png", 3, 140, 20, 17);
        icoBotDroite = chargerIcone("Images/sprites.png", 96, 178, 15, 18);
        icoBotHaut = chargerIcone("Images/sprites.png", 3, 200, 20, 17);
        icoBotBas = chargerIcone("Images/sprites.png", 3, 200, 20, 17);

        //Elements stables
        icoDynamite = chargerIcone("Images/sprites.png",18,248,16,26);
        icoRadis = chargerIcone("Images/sprites.png",73,255,16,26);
        icoVide = chargerIcone("Images/Mur.png");
        icoMur = chargerIcone("Images/tileset.png",0,16,16,16);
        icoSolTerre = chargerIcone("Images/tileset.png",32,0,16,16);
        icoSol = chargerIcone("Images/tileset.png",0,0,16,16);
        icoCorde = chargerIcone("Images/tileset.png",16,0,16,16);

        //Colonnes
        //Orange
        icoColonneOrangeTete = chargerIcone("Images/tileset.png",1,80,15,15);
        icoColonneOrangeCorps = chargerIcone("Images/tileset.png",17,80,15,15);
        icoColonneOrangeBas = chargerIcone("Images/tileset.png",33,80,15,15);
        //Bleue
        icoColonneBleueTete = chargerIcone("Images/tileset.png",1,48,15,15);
        icoColonneBleueCorps = chargerIcone("Images/tileset.png",17,48,15,15);
        icoColonneBleueBas = chargerIcone("Images/tileset.png",33,48,15,15);
    }

    // chargement d'une sous partie de l'image
    private ImageIcon chargerIcone(String urlIcone, int x, int y, int w, int h) {
        // charger une sous partie de l'image à partir de ses coordonnées dans urlIcone
        BufferedImage bi = getSubImage(urlIcone, x, y, w, h);
        // adapter la taille de l'image a la taille du composant (ici : 20x20)
        return new ImageIcon(bi.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH));
    }

    private BufferedImage getSubImage(String urlIcone, int x, int y, int w, int h) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        BufferedImage bi = image.getSubimage(x, y, w, h);
        return bi;
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


        return new ImageIcon(image);
    }
    public void updateMenuBarValues(){
        JMenuBar trackBar = new JMenuBar();

        //Recuperer els valuers
        tempsrestant = jeu.getTemps();
        resultatscore = jeu.getScore();
        vierestante = jeu.getVieRestante();
        dynamitesRestants = jeu.getNbDynamites();

        vie = new JMenuItem("Vie : " + jeu.getVieRestante());
        dynamites = new JMenuItem("Dynamites : "+ dynamitesRestants);
        score = new JMenuItem("Score : " + resultatscore);
        temps = new JMenuItem("Temps : "+ tempsrestant);



        vie.setBackground(Color.black);
        dynamites.setBackground(Color.black);
        score.setBackground(Color.black);
        temps.setBackground(Color.black);
        vie.setForeground(Color.gray);
        dynamites.setForeground(Color.gray);
        score.setForeground(Color.gray);
        temps.setForeground(Color.gray);
        trackBar.add(vie);
        vie.setIcon(chargerIcone("Images/sprites.png", 92, 3, 20, 24));
        trackBar.add(dynamites);
        dynamites.setIcon(chargerIcone("Images/sprites.png",18,252,20,24));
        trackBar.add(score);
        trackBar.add(temps);
        trackBar.setAlignmentX(CENTER_ALIGNMENT);
        trackBar.add(Box.createRigidArea(new Dimension(0,20)));
        trackBar.setBorder(new CompoundBorder(null,null));
        setJMenuBar(trackBar);
    }


    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();

        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }





}
