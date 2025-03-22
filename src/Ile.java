import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ile {

    // l'ile est une grille de taille 12/12
    int nbJoueur = 4;
    final int width = 12 , height = 12;
    Zone[][] grille ;

    int nbEliport = 2;

    public Ile (){
        Random rand = new Random();
        this.grille = new Zone[width][height] ;




        // on remplis d'abord avec que des Zone Ordinaire
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                 grille[i][j] = new ZoneOridinaire();
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




    }

    public Zone[][] getGrille() {
        return grille;
    }




}
