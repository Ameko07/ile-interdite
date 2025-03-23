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

        // === 3. Ajout de 2 zones Héliport ===
        for (int i = 0; i < nbEliport; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            // On cherche une zone ordinaire uniquement
            while (!(grille[x][y] instanceof ZoneOrdinaire)) {
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            }

            ZoneEliport zEli = new ZoneEliport();
            zEli.setPosition(x, y);
            grille[x][y] = zEli;
        }
    }

    /**
     * Méthode utilitaire pour placer une ZoneElement aléatoirement,
     * sans écraser une autre zone spéciale
     */
    private void placerZoneElement(Artefact artefact, Random rand) {
        int x = rand.nextInt(width);
        int y = rand.nextInt(height);

        // Ne pas remplacer une zone déjà spéciale
        while (!(grille[x][y] instanceof ZoneOrdinaire)) {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
        }

        ZoneElement zoneElem = new ZoneElement(artefact.getType());
        zoneElem.setPosition(x, y);
        zoneElem.setArt(artefact); // Si tu veux associer l'artefact visuellement
        grille[x][y] = zoneElem;
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
}

