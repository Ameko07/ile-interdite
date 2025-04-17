import javax.swing.*;
import java.util.*;

public class ControleurJoueur {
    // récupération des attribut utiles pour les actions
    private Ile ile;
    private Joueur joueur;
    private Map<Zone, ZonePanel> zoneMap = new HashMap<>();
    private int actionsRestantes = 3;
    private int joueurActif = 0;
    private List<Joueur> joueurs ;
    private JLabel joueurLabel;

    private Fenetre fenetre;

    // construtor
    public ControleurJoueur(Ile i, Joueur j, Map<Zone, ZonePanel> z, List<Joueur> Lj, JLabel labelJoueur, Fenetre f) {

        this.ile = i;
        this.joueur = j;
        this.zoneMap = z;
        this.joueurs = Lj;
        joueurLabel = labelJoueur;
        this.fenetre = f;
    }

    /**
     * Méthode appelée à chaque "fin de tour"
     * Inonde 3 zones non-submergées au hasard
     */
    private void inonderTroisZones() {
        List<Zone> candidates = new ArrayList<>();
        for (int i = 0; i < ile.getWidth(); i++) {
            for (int j = 0; j < ile.getHeight(); j++) {
                Zone z = ile.getZone(i, j);
                if (z.getEtat() != Zone.Etat.submerge) {
                    candidates.add(z);
                }
            }
        }

        Collections.shuffle(candidates);
        int n = Math.min(3, candidates.size());

        for (int i = 0; i < n; i++) {
            Zone z = candidates.get(i);

            // 🧍‍♂️ Si un joueur est sur cette zone ET elle va devenir submergée
            for (Joueur j : joueurs) {
                if (j.getX() == z.getX() && j.getY() == z.getY() && z.getEtat() == Zone.Etat.inonde) {
                    if (!aUneZoneAdjacenteAccessible(j)) {
                        JOptionPane.showMessageDialog(null, "💀 Le joueur " + (j.getId() + 1) + " s’est noyé ! Partie perdue...");
                        partiePerdue = true;
                        System.exit(0);
                        return;
                    }
                }
            }

            // Changement d'état
            if (z.getEtat() == Zone.Etat.normal) {
                z.changeState(Zone.Etat.inonde);
            } else if (z.getEtat() == Zone.Etat.inonde) {
                z.changeState(Zone.Etat.submerge);
            }

            ZonePanel panel = zoneMap.get(z);
            if (panel != null) panel.refresh();
        }

        System.out.println("🌀 Nouveau tour, actions réinitialisées !");
    }

    /**Function consommerAction
     * un boolean qui dimmine les actions restante à chaque appel et retourne s'il est possible d'effectuer une action ou non **/
    public boolean consommerAction() {
        if (actionsRestantes > 0) {
            actionsRestantes--;
            System.out.println("✅ Action effectuée ! Il reste : " + actionsRestantes);
            fenetre.updateInfos(joueur.getId(), actionsRestantes);
            return true;
        } else {
            System.out.println("⛔ Plus d'actions disponibles ce tour !");
            return false;
        }
    }

    /**Methode assecherZone
     * change l'etat de la zone à l'emplacement du joueur en normal si celui est inondé **/
    public void assecherZone(){
        if (!consommerAction()) return;
        int xJ = this.joueur.getX();
        int yJ = this.joueur.getY();
        Zone zJ = this.ile.getZone(xJ,yJ);
        if (zJ.getEtat() == Zone.Etat.inonde){
            this.ile.setEtatZone(xJ,yJ, Zone.Etat.normal);
        }else if (zJ.getEtat() == Zone.Etat.submerge){
            System.out.println("Impossible assèchement de la Zone");
        }else {
            // on ne fait rien
        }
        ZonePanel zP = zoneMap.get(zJ);
        zP.refresh();
    }

    /**methode recupArtJoueur
     * récupère l'artefact à l'emplacement
     * supprime l'artefact de la zone et l"ajoute à l'inventaire du joueur **/
    public void recupArtJoueur() {
        if (!consommerAction()) return;

        int xJ = joueur.getX();
        int yJ = joueur.getY();
        Zone z = ile.getZone(xJ, yJ);

        if (z instanceof ZoneElement zoneElem && zoneElem.thereIsArtefact()) {
            Artefact.Element type = zoneElem.getElement();

            // Compte les clés de ce type
            int count = 0;
            for (Clef c : joueur.getClefs()) {
                if (c.getCleElem() == type) count++;
            }

            if (count >= 4) {
                // Enlève 4 clefs de ce type
                int removed = 0;
                ArrayList<Clef> aRetirer = new ArrayList<>();
                for (Clef c : joueur.getClefs()) {
                    if (c.getCleElem() == type && removed < 4) {
                        aRetirer.add(c);
                        removed++;
                    }
                }
                //Potentiel bug car joueur.getClef renvoie une copie de l'atttribut donc si on modifie la copie et non l'attribut
                joueur.getClefs().removeAll(aRetirer);

                // Ajoute l'artefact et supprime de la zone
                joueur.addArt(zoneElem.getArt());
                zoneElem.deletArt();
                System.out.println("🎁 Artefact récupéré !");

                checkVictoire(); // Vérifie si c’est la fin de la partie
            } else {
                System.out.println("⛔ Pas assez de clefs !");
            }
        } else {
            System.out.println("❌ Pas d'artefact ici !");
        }
    }


    // ⬅️ Appelée avec dx/dy = déplacement horizontal/vertical
    public void deplacerJoueur(int dx, int dy) {
        if (!consommerAction()) return;
        int newX = joueur.getX() + dx;
        int newY = joueur.getY() + dy;

        // ✅ Vérification que la nouvelle position est dans la grille
        if (newX >= 0 && newX < ile.getWidth() && newY >= 0 && newY < ile.getHeight()) {
            Zone zoneCible = ile.getZone(newX, newY);

            // ✅ Vérifie que la zone n'est pas submergée
            if (zoneCible.getEtat() != Zone.Etat.submerge) {
                // 👣 Déplacer le joueur
                joueur.setPosition(newX, newY);

                // 🔄 Rafraîchir tous les panneaux pour mettre à jour le contour vert
                for (ZonePanel panel : zoneMap.values()) {
                    panel.refresh();
                }
            } else {
                System.out.println("⛔ Zone submergée, impossible d'y aller !");
            }
        } else {
            System.out.println("⛔ Hors de la grille !");
        }
    }

    /**Action chercherClef
     * Action à multiple possibilité
     * trouver une clé , ne rien faire ou inonder la zone sur laquelle le joueur se trouve**/


    public void chercherClef(){
        //recupéré les coordonnée du joueur pour connaitre sa zone
        int xJ = this.joueur.getX();
        int yJ = this.joueur.getY();
        Zone z = this.ile.getZone(xJ,yJ);
        Random rand = new Random();

        // du random
        int alea = rand.nextInt(4);
        switch (alea){
            case 0:
                // Vérifier que la zone est ordinaire
                if (z instanceof ZoneOrdinaire) {
                    ZoneOrdinaire zo = (ZoneOrdinaire) z;
                    if (zo.thereIsClef()){
                        // Récupérer la clef et l'ajouter au joueur
                        Clef c = zo.getCle();
                        this.joueur.addClef(c);
                        System.out.println("Le joueur a trouvé une clef de type " + c.getCleElem());
                        // Supprimer la clef de la zone
                        zo.deleteClef();
                    }
                } else {
                    System.out.println("La zone n'est pas ordinaire, aucune clef à récupérer.");
                }
                break;
            case 1:
                // inondation de la zone
                if (z.getEtat() == Zone.Etat.normal){
                    z.changeState(Zone.Etat.inonde);
                    System.out.println("La zone du joueur est inondée");
                }
                break;
            case 3:
                Random r = new Random();
                // on ajoute une action dans la liste du joueur

                    this.joueur.addAction("Sac De Sable");

                System.out.println("Le joueur a obtenue une action special : Sac de Sable " + joueur.getActionValues("Sac De Sable"));
                break;
            case 4:
                System.out.println("Ne fait rien");
                break;

        }
        // Mise à jour des zones
        this.ile.getGrille()[xJ][yJ] = z;
        for (ZonePanel zP : zoneMap.values()) {
            zP.setJoueur(joueur);
            zP.refresh();
        }

    }



    // FIN DE TOUR
    private boolean partiePerdue = false;

    public void finDeTour() {
        inonderTroisZones();

        // 💥 Vérifier si l’héliport a disparu
        for (int i = 0; i < ile.getWidth(); i++) {
            for (int j = 0; j < ile.getHeight(); j++) {
                Zone z = ile.getZone(i, j);
                if (z instanceof ZoneEliport && z.getEtat() == Zone.Etat.submerge) {
                    JOptionPane.showMessageDialog(null, "💀 L'héliport a été submergé ! Partie perdue...");
                    partiePerdue = true;
                    System.exit(0);
                    return;
                }
            }
        }

        // ⏭️ Tour suivant (si on n’a pas perdu)
        joueurActif = (joueurActif + 1) % joueurs.size();
        joueur = joueurs.get(joueurActif);
        actionsRestantes = 3;

        for (ZonePanel zP : zoneMap.values()) {
            zP.setJoueur(joueur);
            zP.refresh();
        }

        joueurLabel.setText("🎮 Tour du joueur " + (joueurActif + 1));
    }







    public Ile getIle() {
        return ile;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Map<Zone, ZonePanel> getZoneMap() {
        return zoneMap;
    }

    public int getActionsRestantes() {
        return actionsRestantes;
    }

    public void assecherAdjacente(int dx, int dy) {
        if (!consommerAction()) return;

        int x = joueur.getX() + dx;
        int y = joueur.getY() + dy;

        // ✅ Vérification des bornes
        if (x >= 0 && x < ile.getWidth() && y >= 0 && y < ile.getHeight()) {
            Zone cible = ile.getZone(x, y);
            if (cible.getEtat() == Zone.Etat.inonde) {
                cible.changeState(Zone.Etat.normal);
                System.out.println("💧 Zone (" + x + "," + y + ") asséchée !");
                zoneMap.get(cible).refresh();
            } else {
                System.out.println("❌ La zone n'est pas inondée.");
            }
        } else {
            System.out.println("⛔ Zone hors de la grille.");
        }
    }

    private void checkVictoire() {
        boolean tousArtefacts = true;
        for (Artefact.Element elem : Artefact.Element.values()) {
            boolean present = false;
            for (Joueur j : joueurs) {
                for (Artefact a : j.getArt()) {
                    if (a.getType() == elem) {
                        present = true;
                        break;
                    }
                }
            }
            if (!present) {
                tousArtefacts = false;
                break;
            }
        }

        if (!tousArtefacts) return;

        // Vérifie que tous les joueurs sont sur un héliport
        boolean tousSurHeliport = true;
        for (Joueur j : joueurs) {
            Zone z = ile.getZone(j.getX(), j.getY());
            if (!(z instanceof ZoneEliport)) {
                tousSurHeliport = false;
                break;
            }
        }

        if (tousSurHeliport) {
            JOptionPane.showMessageDialog(null, "🎉 Félicitations ! Vous avez gagné !");
            System.exit(0);
        }
    }
    private boolean aUneZoneAdjacenteAccessible(Joueur j) {
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] dir : directions) {
            int nx = j.getX() + dir[0];
            int ny = j.getY() + dir[1];

            if (nx >= 0 && nx < ile.getWidth() && ny >= 0 && ny < ile.getHeight()) {
                Zone adj = ile.getZone(nx, ny);
                if (adj.getEtat() != Zone.Etat.submerge) {
                    return true; // Y'a une issue
                }
            }
        }
        return false; // Piégé
    }


    /**Controlleur du joueur de l'action SacDeSable
     * Réutilisation de la fonction Assecher Adjascent mais sans le calcul de position
     * On va directmenent à l'endroit demandé
     * **/
    public void SacDeSable(int x, int y){
        //  Vérification des bornes
        if (x >= 0 && x < ile.getWidth() && y >= 0 && y < ile.getHeight()) {
            Zone cible = ile.getZone(x, y);
            if (cible.getEtat() == Zone.Etat.inonde) {
                cible.changeState(Zone.Etat.normal);
                System.out.println(" Zone (" + x + "," + y + ") asséchée !");
                zoneMap.get(cible).refresh();
            } else {
                System.out.println(" La zone n'est pas inondée.");
            }
        } else {
            System.out.println(" Zone hors de la grille.");
        }

    }
    public void donnerCle(Joueur receveur, Clef clef) {
        // Vérifie que les joueurs sont sur la même case
        if (joueur.getX() == receveur.getX() && joueur.getY() == receveur.getY()) {
            if (joueur.possedeClef(clef)) {
                if (!consommerAction()) return;

                joueur.removeClef(clef);
                receveur.addClef(clef);
                System.out.println(" Clé " + clef.getCleElem() + " donnée au joueur " + (receveur.getId() + 1));
            } else {
                System.out.println("Tu ne possèdes pas cette clé !");
            }
        } else {
            System.out.println(" Les joueurs ne sont pas sur la même case !");
        }
    }


}

