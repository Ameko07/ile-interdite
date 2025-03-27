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

    // construtor
    public ControleurJoueur(Ile i, Joueur j,Map<Zone, ZonePanel> z , List<Joueur> Lj,JLabel labelJoueur){
        this.ile = i;
        this.joueur = j;
        this.zoneMap = z;
        this.joueurs = Lj;
        joueurLabel = labelJoueur;
    }

    /**
     * Méthode appelée à chaque "fin de tour"
     * Inonde 3 zones non-submergées au hasard
     */
    public void inonderTroisZones() {
        // Liste des zones éligibles à l’inondation
        List<Zone> candidates = new ArrayList<>();

        // Parcours de la grille pour récupérer les zones non submergées
        for (int i = 0; i < ile.getWidth(); i++) {
            for (int j = 0; j < ile.getHeight(); j++) {
                Zone z = ile.getZone(i, j);
                if (z.getEtat() != Zone.Etat.submerge) {
                    candidates.add(z);
                }
            }
        }

        // Mélange aléatoire des zones
        Collections.shuffle(candidates);

        // On va inonder au max 3 zones (ou moins si moins de 3 dispo)
        int n = Math.min(3, candidates.size());

        // Traitement des n zones sélectionnées
        for (int i = 0; i < n; i++) {
            Zone z = candidates.get(i);

            // Si la zone est normale → elle devient inondée
            if (z.getEtat() == Zone.Etat.normal) {
                z.changeState(Zone.Etat.inonde);
            }
            // Si elle est déjà inondée → elle devient submergée
            else if (z.getEtat() == Zone.Etat.inonde) {
                z.changeState(Zone.Etat.submerge);
            }

            // Rafraîchir son affichage graphique
            ZonePanel panel = zoneMap.get(z);
            if (panel != null) {
                panel.refresh(); // Redessine la couleur selon le nouvel état
            }
        }
        actionsRestantes = 3;
        System.out.println("🌀 Nouveau tour, actions réinitialisées !");

    }

    /**Function consommerAction
     * un boolean qui dimmine les actions restante à chaque appel et retourne s'il est possible d'effectuer une action ou non **/
    public boolean consommerAction() {
        if (actionsRestantes > 0) {
            actionsRestantes--;
            System.out.println("✅ Action effectuée ! Il reste : " + actionsRestantes);
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
    public void recupArtJoueur (){
        if (!consommerAction()) return;
        int xJ = this.joueur.getX();
        int yJ = this.joueur.getY();
        Zone zJ = this.ile.getZone(xJ,yJ);
        if (zJ instanceof ZoneElement){
            Artefact a = ((ZoneElement) zJ).getArt();
            if (a == null ){
                throw new NullPointerException ("il n'y a pas d'artefact ici");

            }else if (joueur.possedeClef(new Clef(((ZoneElement) zJ).getElement()))){
                this.joueur.addArt(a);
                this.ile.deletArtZone(xJ,yJ);
                System.out.println("Le joueur a récupéré un artéfact de type " + joueur.getArt());
            }
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
        int alea = rand.nextInt(3);
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
            case 2:
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
    public void finDeTour() {
        inonderTroisZones();

        joueurActif = (joueurActif + 1) % joueurs.size();
        joueur = joueurs.get(joueurActif);

        // Mettre à jour tous les panneaux
        for (ZonePanel zP : zoneMap.values()) {
            zP.setJoueur(joueur);
            zP.refresh();
        }

        actionsRestantes = 3;
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
}
