import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ile {

    // l'ile est une grille de taille 6/6
    // attribut
    int nbJoueur = 4;
    final int width = 6 , height = 6;
    Zone[][] grille ;

    int nbEliport = 2;

    public Ile (){
        Random rand = new Random();
        this.grille = new Zone[width][height] ;




        // on remplis d'abord avec que des Zone Ordinaire
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                 grille[i][j] = new ZoneOrdinaire();
            }

        }

        // ajout des zone avec artefact
        // EAU
        ZoneElement zoneEau = new ZoneElement(new Artefact(Artefact.Element.EAU).getType());
        grille [rand.nextInt(width)][rand.nextInt(height)] = zoneEau;

        // AIR
        ZoneElement zoneAir = new ZoneElement(new Artefact(Artefact.Element.AIR).getType());
        grille [rand.nextInt(width)][rand.nextInt(height)] = zoneAir;

        //TERRE
        ZoneElement zoneTerre = new ZoneElement(new Artefact(Artefact.Element.TERRE).getType());
        grille [rand.nextInt(width)][rand.nextInt(height)] = zoneTerre;

        //FEU
        ZoneElement zoneFeu = new ZoneElement(new Artefact(Artefact.Element.FEU).getType());
        grille [rand.nextInt(width)][rand.nextInt(height)] = zoneFeu;


        // initialisation des Eliports
        // il y en a 2
        for(int i =0; i<nbEliport; i++){
            ZoneEliport zEli = new ZoneEliport();

            //choix au hasard des coordonnées
            int j = rand.nextInt(width);
            int k = rand.nextInt(height);

            //on ne change que zone ordinaire
            while (! (grille[j][k] instanceof ZoneOrdinaire)){
                j = rand.nextInt(width);
                k = rand.nextInt(height);
            }

            grille[j][k] = zEli;
        }




    }

    public Zone[][] getGrille() {
        return grille;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Zone getZone(int i, int j){
        return grille[i][j];
    }
}
