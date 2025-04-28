import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ile {

    // Constantes de taille
    final int width = 6, height = 6;
    // Grille de zones
    private Zone[][] grille;
    // Nombre de zones héliport
    private int nbEliport = 2;

    public Ile() {
        Random rand = new Random();
        grille = new Zone[width][height];

        // === 1. Initialisation : toutes les zones sont ordinaires ===
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grille[i][j] = new ZoneOrdinaire();
                grille[i][j].setPosition(i, j); // ➕ On définit leur position
            }
        }

        // === 2. Ajout des zones élémentaires (artefacts) ===

        // ⚠️ On évite d’écraser une case déjà utilisée
        placerZoneElement(new Artefact(Artefact.Element.EAU), rand);
        placerZoneElement(new Artefact(Artefact.Element.AIR), rand);
        placerZoneElement(new Artefact(Artefact.Element.TERRE), rand);
        placerZoneElement(new Artefact(Artefact.Element.FEU), rand);

        //initialisation des clef aléatoirement sur l'ile
        placerClef(new Clef(Artefact.Element.EAU),rand);
        placerClef(new Clef(Artefact.Element.AIR),rand);
        placerClef(new Clef(Artefact.Element.TERRE),rand);
        placerClef(new Clef(Artefact.Element.FEU),rand);


        // === 3. Ajout de 2 zones Héliport ===
        for (int i = 0; i < nbEliport; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            // On cherche une zone ordinaire uniquement dans la grille
            while (!(grille[x][y] instanceof ZoneOrdinaire)) {
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            }

            // puis on place les Zone Eliport dans la grille
            ZoneEliport zEli = new ZoneEliport();
            zEli.setPosition(x, y);
            grille[x][y] = zEli;
        }
    }

    // === GETTERS ===

    public Zone[][] getGrille() {
        return grille;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Zone getZone(int i, int j) {
        return grille[i][j];
    }


    // ======== SETTER ===============

    /***setEtatZone
     * @param i : int
     * @param j : int
     * @param e : Zone.Etat
     *change l'etat de zone defini */

    public void setEtatZone (int i, int j, Zone.Etat e){
        Zone z = getZone(i,j);
        z.changeState(e);
        this.grille[i][j] = z;
    }

    /**methode deletArtZone
     * @param i : int
     * @param j : int
     * *supprime l'artefact de la zone à l'emplacement i,j*/
    public void deletArtZone(int i, int j){
        // on extrait la zone ne question
        Zone z = getZone(i,j);

        // on l'identifie et on vérifie la présence d'Artefacte
        if (z instanceof ZoneElement && ((ZoneElement) z).thereIsArtefact()){
            ((ZoneElement) z).deletArt();
        }

        // on réinsère la zone dans la grille
        this.grille[i][j] = z;
    }


    /**
     * Méthode utilitaire pour placer une ZoneElement aléatoirement,
     * sans écraser une autre zone spéciale
     * @param artefact : Artefact
     * @param rand : Random
     */
    private void placerZoneElement(Artefact artefact, Random rand) {
        int x = rand.nextInt(width);
        int y = rand.nextInt(height);

        // choix de la zone
        while (!(grille[x][y] instanceof ZoneOrdinaire)|| !estZoneValide(x, y)) {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
        }

        // placement de la zone dans la grille
        ZoneElement zoneElem = new ZoneElement(artefact.getType());
        zoneElem.setPosition(x, y);
        zoneElem.setArt(artefact);
        grille[x][y] = zoneElem;
    }

    /**Methode placerClef
     * @param clem : Clef
     * @param rand : random
     * affecte des coordonnées à une clé pour pouvoir le placer sur l'île **/
    private void placerClef(Clef clem , Random rand){

        //coordonnée aléatoire
        int x = rand.nextInt(width);
        int y = rand.nextInt(height);

        // ne marche que si on tombe sur une ZoneOrdinaire
        while (!(grille[x][y] instanceof ZoneOrdinaire)|| !estZoneValide(x, y)) {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
        }
        //récupération de la zonne choisi
        Zone z = this.grille[x][y];
        if (! (((ZoneOrdinaire)z).thereIsClef() )) {
            ((ZoneOrdinaire) z).addCle(clem);
        }
        this.grille[x][y] = z;
        System.out.println("La clef  de type  " + clem.getCleElem() + " x = " + x + " y = " + y  );

    }
    public boolean estZoneValide(int i, int j) {
        // Retourne true si la case (i,j) est visible sur la carte (forme de losange)
        return !(
                (i == 0 && (j == 0 || j == 1 || j == 4 || j == 5)) ||
                        (i == 1 && (j == 0 || j == 5)) ||
                        (i == 4 && (j == 0 || j == 5)) ||
                        (i == 5 && (j == 0 || j == 1 || j == 4 || j == 5))
        );
    }


}

