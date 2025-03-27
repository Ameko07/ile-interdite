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
        // initialisation du controleur de joueur
        ControleurJoueur cJ = new ControleurJoueur(this.ile, this.joueur,zoneMap,joueurs,joueurLabel);

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
        bouton.addActionListener(e -> cJ.finDeTour());


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
        haut.addActionListener(e -> cJ.deplacerJoueur( -1, 0));     // ⬆️ haut
        bas.addActionListener(e -> cJ.deplacerJoueur(1, 0));       // ⬇️ bas
        gauche.addActionListener(e -> cJ.deplacerJoueur(0, -1));   // ⬅️ gauche
        droite.addActionListener(e -> cJ.deplacerJoueur(0, 1));    // ➡️ droite

        //nouveau panel pour les boutons d'action
        JPanel panelAction = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Aligner à gauche



        // Ajout des boutons d'assechement , récupérer artefact et chercher clé
        JButton assecher = new JButton("Assécher");
        assecher.setPreferredSize(new Dimension(150, 50));
        assecher.addActionListener(e -> cJ.assecherZone());
        panelAction.add(assecher);

        JButton recupArtB = new JButton("Récupérer artefact");
        recupArtB.setPreferredSize(new Dimension(150, 50));
        recupArtB.addActionListener(e->cJ.recupArtJoueur());
        panelAction.add(recupArtB);

        JButton chercherclef = new JButton("Chercher une clef");
        chercherclef.setPreferredSize(new Dimension(150, 50));
        chercherclef.addActionListener( e-> cJ.chercherClef());
        panelAction.add(chercherclef);


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





















}
