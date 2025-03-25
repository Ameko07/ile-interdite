// Imports Swing (interface graphique)
import javax.swing.*;
import java.awt.*;


// Imports graphiques spécifiques (au lieu d'import java.awt.*;)

// Imports des collections (List, Map, etc.)
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

// Classe principale de la fenêtre du jeu, hérite de JFrame
public class Fenetre extends JFrame {

    // Map entre chaque Zone (données) et son affichage (ZonePanel)
    private Map<Zone, ZonePanel> zoneMap = new HashMap<>();

    // L’île, avec ses zones
    private Ile ile;
    private Joueur joueur;
    private int actionsRestantes = 3;
    private List<Joueur> joueurs = new ArrayList<>();
    private int joueurActif = 0;
    private JLabel joueurLabel; // affichage du joueur courant
    private Color[] couleursJoueurs = {Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.CYAN};



    // Constructeur de la fenêtre
    public Fenetre() {
        // Titre de la fenêtre
        setTitle("Ile interdite");

        // Quand on ferme la fenêtre → quitter le programme
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout principal : BorderLayout
        setLayout(new BorderLayout());

        // === Création du bouton "Fin de tour" ===
        JButton bouton = new JButton("Fin de tour");
        bouton.setPreferredSize(new Dimension(150, 50));

        // Ajout d’un listener sur le bouton
        bouton.addActionListener(e -> finDeTour());


        // Panel pour le bouton (à droite)
        JPanel panelButton = new JPanel();
        panelButton.add(bouton);

        // Ajout du panel de bouton à droite (EAST)
        add(panelButton, BorderLayout.EAST);
        // === Panel des déplacements ===
        JPanel panelDeplacement = new JPanel();
        panelDeplacement.setLayout(new GridLayout(2, 3, 5, 5)); // espacement stylé

// Création des boutons directionnels
        JButton haut = new JButton("↑");
        JButton bas = new JButton("↓");
        JButton gauche = new JButton("←");
        JButton droite = new JButton("→");

      // Ajout des boutons au panel (en forme de croix)
        panelDeplacement.add(new JLabel()); // vide
        panelDeplacement.add(haut);
        panelDeplacement.add(new JLabel()); // vide
        panelDeplacement.add(gauche);
        panelDeplacement.add(bas);
        panelDeplacement.add(droite);

        // Ajout au panel existant à droite
        panelButton.add(panelDeplacement);
        haut.addActionListener(e -> deplacerJoueur( -1, 0));     // ⬆️ haut
        bas.addActionListener(e -> deplacerJoueur(1, 0));       // ⬇️ bas
        gauche.addActionListener(e -> deplacerJoueur(0, -1));   // ⬅️ gauche
        droite.addActionListener(e -> deplacerJoueur(0, 1));    // ➡️ droite

        //nouveau panel pour les boutons d'action
        JPanel panelAction = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Aligner à gauche



        // Ajout des boutons d'assechement , récupérer artefact et chercher clé
        JButton assecher = new JButton("Assécher");
        assecher.setPreferredSize(new Dimension(150, 50));
        assecher.addActionListener(e -> assecherZone());
        panelAction.add(assecher);

        JButton recupArtB = new JButton("Récupérer artefact");
        recupArtB.setPreferredSize(new Dimension(150, 50));
        recupArtB.addActionListener(e->recupArtJoueur());
        panelAction.add(recupArtB);

        add(panelAction,BorderLayout.SOUTH);




        //  BONUS VISUEL

        Font fontBouton = new Font("Arial", Font.BOLD, 20); // Police moderne et lisible
        Color couleurFond = new Color(220, 220, 220);       // Gris clair
        Color couleurTexte = Color.DARK_GRAY;

        JButton[] boutons = {haut, bas, gauche, droite};
        for (JButton b : boutons) {
            b.setFont(fontBouton);
            b.setBackground(couleurFond);
            b.setForeground(couleurTexte);
            b.setFocusPainted(false); // Enlève le cadre moche quand sélectionné
            b.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }


        // === Initialisation de l’île ===
        ile = new Ile();
        // Création d'un joueur avec des coordonnées valides
        // Création du joueur dans une zone non submergée
        int nbJoueurs = 4; // ou 2, 3 selon ce que tu veux
        for (int i = 0; i < nbJoueurs; i++) {
            Joueur j = new Joueur(ile.getWidth(), ile.getHeight());
            while (ile.getZone(j.getX(), j.getY()).getEtat() == Zone.Etat.submerge) {
                j = new Joueur(ile.getWidth(), ile.getHeight());
            }
            j.setId(i);
            joueurs.add(j);
        }
        joueur = joueurs.get(0); // joueur 1 au début


        joueurLabel = new JLabel("🎮 Tour du joueur 1", SwingConstants.CENTER);
        joueurLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(joueurLabel, BorderLayout.NORTH);


        // Panel principal pour afficher la grille
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(ile.getWidth(), ile.getHeight()));

        // On parcourt chaque zone pour créer son affichage
        for (int i = 0; i < ile.getWidth(); i++) {
            for (int j = 0; j < ile.getHeight(); j++) {
                Zone zone = ile.getZone(i, j);
                ZonePanel zP = new ZonePanel(zone);

                // ⬇️ ici on donne le joueur à chaque ZonePanel
                zP.setJoueur(joueur);

                gridPanel.add(zP);
                zoneMap.put(zone, zP);
            }
        }
        for (ZonePanel panel : zoneMap.values()) {
            panel.refresh(); // ✅ ça force chaque case à vérifier si le joueur est là
        }

        // Ajout du panel de grille au centre de la fenêtre
        add(gridPanel, BorderLayout.CENTER);

        // Ajuste automatiquement la taille de la fenêtre selon les composants
        pack();

        // Taille finale de la fenêtre
        setSize(1200, 800);

        // Affichage visible
        setVisible(true);
    }
    // Rafraîchir tous les panels une fois le joueur placé


    /**
     * Méthode appelée à chaque "fin de tour"
     * Inonde 3 zones non-submergées au hasard
     */
    private void inonderTroisZones() {
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

            }else {
                this.joueur.addArt(a);
                this.ile.deletArtZone(xJ,yJ);
            }
        }
    }


    // ⬅️ Appelée avec dx/dy = déplacement horizontal/vertical
    private void deplacerJoueur(int dx, int dy) {
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

    private boolean consommerAction() {
        if (actionsRestantes > 0) {
            actionsRestantes--;
            System.out.println("✅ Action effectuée ! Il reste : " + actionsRestantes);
            return true;
        } else {
            System.out.println("⛔ Plus d'actions disponibles ce tour !");
            return false;
        }
    }

    private void finDeTour() {
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







}
