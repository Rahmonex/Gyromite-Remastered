package modele.plateau;

import modele.deplacements.Direction;
import VueControleur.VueControleurGyromite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ImagesActions {
    //HashMap<Type, Direction, ImageIcon> imagesActions = new HashMap<>();
    public ImageIcon icoHeros;
    public ImageIcon icoHerosDroite;
    public ImageIcon icoHerosGauche;
    public ImageIcon icoDynamite;
    public ImageIcon icoRadis;
    public ImageIcon icoBot;
    public ImageIcon icoBotDroite;
    public ImageIcon icoBotGauche;
    public ImageIcon icoSol;
    public ImageIcon icoSolTerre;
    public ImageIcon icoCorde;
    public ImageIcon icoVide;
    public ImageIcon icoMur;
    public ImageIcon icoColonne;






    private void chargerLesIcones() {
        //Changer icones et separer sol et mur
        //Heros
        icoHeros = chargerIcone("Images/sprites.png", 92, 3, 20, 24);
        icoHerosDroite = chargerIcone("Images/sprites.png", 59, 164, 20, 24);
        icoHerosGauche = chargerIcone("Images/sprites.png", 73, 3, 20, 24);

        //Bot
        icoBot = chargerIcone("Images/sprites.png", 3, 140, 20, 17);
        icoBotGauche = chargerIcone("Images/sprites.png", 3, 140, 20, 17);
        icoBotDroite = chargerIcone("Images/sprites.png", 96, 178, 15, 18);

        //Elements stables
        icoDynamite = chargerIcone("Images/sprites.png",18,248,16,26);
        icoRadis = chargerIcone("Images/sprites.png",73,255,16,26);
        icoVide = chargerIcone("Images/Mur.png");
        icoMur = chargerIcone("Images/tileset.png",0,16,16,16);
        icoSolTerre = chargerIcone("Images/tileset.png",32,0,16,16);
        icoSol = chargerIcone("Images/tileset.png",0,0,16,16);
        icoCorde = chargerIcone("Images/tileset.png",16,0,16,16);

        //Elements Dynamiques
        icoColonne = chargerIcone("Images/Colonne.png");

    }

    // chargement d'une sous partie de l'image
    private ImageIcon chargerIcone(String urlIcone, int x, int y, int w, int h) {
        // charger une sous partie de l'image à partir de ses coordonnées dans urlIcone
        BufferedImage bi = getSubImage(urlIcone, x, y, w, h);
        // adapter la taille de l'image a la taille du composant (ici : 20x20)
        return new ImageIcon(bi.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
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



}
