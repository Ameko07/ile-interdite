import javax.swing.*;
import java.awt.*;

// Imports des collections (List, Map, etc.)
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class Fenetre extends JFrame {

    private Map<Zone, ZonePanel> zoneMap = new HashMap<>();
    private Ile ile;
    private Joueur joueur;
    private List<Joueur> joueurs = new ArrayList<>();
    private JLabel joueurLabel;
    private JLabel actionsLabel;
    private ControleurJoueur cJ;
    private JTextArea infosJoueurs;
    private Musique musiqueBG;
    private Images imageBG;
    private ImagesArtefact imagesArt;
    private JPanel panelActions;  // rends ce champ accessible globalement



    /**Constructeur de la classe fenetre**/

    public Fenetre() {
        setTitle("🌊 Île Interdite");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(235, 245, 255));


        //initialisation des images
        imageBG =  new Images("src/JungleImageBG.JPG");
        imageBG.setLayout(new BorderLayout()); // Permet d'ajouter d'autres composants
        setContentPane(imageBG);

        // ==== Initialisation des attributs utiles ====
        ile = new Ile();
        for (int i = 0; i < 4; i++) {
            Joueur j = new Joueur(ile.getWidth(), ile.getHeight());
            while (ile.getZone(j.getX(), j.getY()).getEtat() == Zone.Etat.submerge) {
                j = new Joueur(ile.getWidth(), ile.getHeight());
            }
            j.setId(i);
            joueurs.add(j);
        }
        joueur = joueurs.get(0);

        // ==== Initialisation UI ====
        joueurLabel = new JLabel("🎮 Tour du joueur 1", SwingConstants.CENTER);
        infosJoueurs = new JTextArea(8, 30);
        infosJoueurs.setEditable(false);
        infosJoueurs.setFont(new Font("Monospaced", Font.PLAIN, 14));
        infosJoueurs.setBorder(BorderFactory.createTitledBorder("📋 Inventaire des joueurs"));
        add(new JScrollPane(infosJoueurs), BorderLayout.WEST);

        joueurLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        joueurLabel.setForeground(new Color(33, 45, 66));
        joueurLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(joueurLabel, BorderLayout.NORTH);

        cJ = new ControleurJoueur(ile, joueur, zoneMap, joueurs, joueurLabel, this);

        // ==== Panel Grille ====
        JPanel gridPanel = new JPanel(new GridLayout(ile.getWidth(), ile.getHeight(), 3, 3));
        gridPanel.setBackground(new Color(210, 230, 250));
        for (int i = 0; i < ile.getWidth(); i++) {
            for (int j = 0; j < ile.getHeight(); j++) {
                Zone z = ile.getZone(i, j);
                ZonePanel zp = new ZonePanel(z);
                zp.setJoueur(joueur);
                gridPanel.add(zp);
                zoneMap.put(z, zp);
            }
        }
        zoneMap.values().forEach(ZonePanel::refresh);
        add(gridPanel, BorderLayout.CENTER);

        // ==== Panel Est (Actions & Déplacements) ====
        JPanel panelRight = new JPanel();
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
        panelRight.setOpaque(false);
        panelRight.setBackground(new Color(0, 0, 0, 0)); // Transparent
        //panelRight.setBackground(new Color(245, 250, 255));
        panelRight.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ==============BOUTON FIn de Tour ============
        JButton finTour = makeButton("✅ Fin de tour");
        finTour.addActionListener(e -> {
            cJ.finDeTour();
            actionsLabel.setText("⚙️ Actions restantes : " + cJ.getActionsRestantes());
            updateBoutonsSpeciaux();
        });
        panelRight.add(finTour);
        panelRight.add(Box.createVerticalStrut(15));

        // Déplacements
        panelRight.add(new JLabel("Déplacements", SwingConstants.CENTER));
        panelRight.add(Box.createVerticalStrut(10));
        panelRight.add(buildCrossPanel(
                makeActionButton("↑", () -> deplacer(-1, 0)),
                makeActionButton("↓", () -> deplacer(1, 0)),
                makeActionButton("←", () -> deplacer(0, -1)),
                makeActionButton("→", () -> deplacer(0, 1))
        ));
        panelRight.add(Box.createVerticalStrut(25));

        // Assèchement

        JLabel assechLab = new JLabel("Assécher Adjacent", SwingConstants.CENTER);

        assechLab.setForeground(Color.decode("#a7eef7"));  // Texte en bleu
        //assechLab.setOpaque(false);            // Active le fond
        //assechLab.setBackground(Color.LIGHT_GRAY); // Fond gris clair

        panelRight.add(assechLab);
        panelRight.add(Box.createVerticalStrut(10));
        panelRight.add(buildCrossPanel(
                makeActionButton("↑", () -> cJ.assecherAdjacente(-1, 0)),
                makeActionButton("↓", () -> cJ.assecherAdjacente(1, 0)),
                makeActionButton("←", () -> cJ.assecherAdjacente(0, -1)),
                makeActionButton("→", () -> cJ.assecherAdjacente(0, 1))
        ));

        add(panelRight, BorderLayout.EAST);

        // ==== Panel Sud ====
        JPanel panelBas = new JPanel();
        panelBas.setLayout(new BoxLayout(panelBas, BoxLayout.Y_AXIS));

        //panelBas.setBackground(new Color(235, 245, 255));
        panelBas.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        panelBas.setOpaque(false);
        panelBas.setBackground(new Color(0, 0, 0, 0)); // Transparent

        actionsLabel = new JLabel("⚙️ Actions restantes : 3", SwingConstants.CENTER);
        actionsLabel.setForeground(Color.WHITE);
        actionsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        actionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBas.add(actionsLabel);
        panelBas.add(Box.createVerticalStrut(10));

        panelActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));


        panelActions.setOpaque(false);
        panelActions.setBackground(new Color(0, 0, 0, 0)); // Transparent
        updateBoutonsSpeciaux();

        panelBas.add(panelActions);
        add(panelBas, BorderLayout.SOUTH);

        // initialisation de la musique de fond
        musiqueBG = new Musique();
        // chemin de la musique depuis nos pc respective (petit bug niveau chemin locale)
        //"C:\\Users\\mimia\\Documents\\ile-interdite\\src\\JungleMusic.WAV"
        //"C:\\Users\\lafat\\Université\\POGL\\ile-interdite\\src\\JungleMusic.WAV"//

        musiqueBG.jouerMusique("C:\\Users\\lafat\\Université\\POGL\\ile-interdite\\src\\JungleMusic.WAV");

        pack();
        setMinimumSize(new Dimension(1100, 750));
        setLocationRelativeTo(null); // centre écran
        setVisible(true);

    }

    /**methode deplacer
     * @param dx : int
     * @param dy : int
     * permet l'animation du joueur dans la fenêtre et update ses coordonnées **/
    public void deplacer(int dx, int dy) {
        cJ.deplacerJoueur(dx, dy);
        actionsLabel.setText("⚙️ Actions restantes : " + cJ.getActionsRestantes());
    }




    /**methode buildCrossPanel
     * @param down : JButton
     * @param up : JButton
     * @param left : JButton
     *  @param right : JButton
     *  Permet d'afficher les boutons directionnels pour le dépacement du joeur
     *               et pour assechement adjacent**/
    private JPanel buildCrossPanel(JButton up, JButton down, JButton left, JButton right) {
        JPanel p = new JPanel(new GridLayout(2, 3, 5, 5));
        p.setOpaque(false);
        p.add(new JLabel());
        p.add(up);
        p.add(new JLabel());
        p.add(left);
        p.add(down);
        p.add(right);
        return p;
    }


    /**Methode update Info
     * @param joueurActif : int
     * @param actionsRestantes : int
     * Met à jour les informations affché du joueur actif**/
    public void updateInfos(int joueurActif, int actionsRestantes) {
        StringBuilder sb = new StringBuilder();
        for (Joueur j : joueurs) {
            sb.append("🎮 Joueur ").append(j.getId() + 1);
            if (j.getId() == joueurActif) sb.append(" (ACTIF)");
            sb.append("\n  🔑 Clés: ");
            for (Clef c : j.getClefs()) {
                sb.append(c.getCleElem()).append(" ");
            }
            sb.append("\n  ✨ Artefacts: ");
            for (Artefact a : j.getArt()) {
                sb.append(a.getType()).append(" ");
            }
            sb.append("\n\n");
        }

        sb.append("⚡ Actions restantes : ").append(actionsRestantes).append("\n");

        infosJoueurs.setText(sb.toString());
    }

    /**Methode de création de bouton makeButton()
     * @param text : String
     * permet de créer les bouton sur les mêmes dimensions**/
    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(180, 40));
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(new Color(220, 230, 240));
        b.setFocusPainted(false);
        b.setForeground(new Color(30, 30, 30));
        return b;
    }

    /**Methode de création d'action de bouton makeActionButton()
     * @param label : String
     * @param action : Runnable
     * crée un bouton comme la méthode makeButton
     * Mais ici, on associe directement une action **/
    private JButton makeActionButton(String label, Runnable action) {
        JButton b = new JButton(label);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setBackground(new Color(230, 255, 230));
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        b.addActionListener(e -> action.run());
        return b;
    }

    /**Fonction makeFenetreSacSable() qui affiche
     * une fenêtre avec des boutons et des inputs pour Assecher des Zones loins du joueur actif
     **/
    private void makeFenetreSacSable() {
        JDialog miniFenetre = new JDialog(this, "Sac de Sable", true);
        miniFenetre.setLayout(new GridLayout(4, 2, 10, 10));
        miniFenetre.setSize(300, 200);
        miniFenetre.setLocationRelativeTo(this);

        JLabel labelX = new JLabel("Coordonnée X (0-5) :");
        JTextField inputX = new JTextField();

        JLabel labelY = new JLabel("Coordonnée Y (0-5) :");
        JTextField inputY = new JTextField();

        JLabel messageErreur = new JLabel("");
        messageErreur.setForeground(Color.RED);

        JButton valider = makeButton("Validé");
        valider.addActionListener(e -> {
            try {
                int x = Integer.parseInt(inputX.getText());
                int y = Integer.parseInt(inputY.getText());

                if (x >= 0 && x <= 5 && y >= 0 && y <= 5) {
                    // Action à exécuter avec les coordonnées
                    cJ.SacDeSable(x,y);

                    System.out.println("Coordonnées valides : x=" + x + ", y=" + y);
                    cJ.getJoueur().getActionValues("Sac De Sable");

                    miniFenetre.dispose(); // Fermer la mini-fenêtre
                } else {
                    messageErreur.setText("Coordonnées invalides !");
                }
            } catch (NumberFormatException ex) {
                messageErreur.setText("Veuillez entrer des nombres valides !");
            }
        });

        miniFenetre.add(labelX);
        miniFenetre.add(inputX);
        miniFenetre.add(labelY);
        miniFenetre.add(inputY);
        miniFenetre.add(new JLabel());  // espace vide
        miniFenetre.add(valider);
        miniFenetre.add(messageErreur);

        miniFenetre.setVisible(true);
    }

    /**Fonction makeFenetre() qui affiche une fenêtre avec des boutons et des inputs
     * entrer les coordonnées pour déplacers les joueurs sur la même à une nouvelle zone
     * et Met à jour leur données
     * **/
    public void makeFenetreHelico() {
     JDialog miniFenetre = new JDialog(this, "Helicoptère", true);
     miniFenetre.setLayout(new GridLayout(4, 2, 10, 10));
     miniFenetre.setSize(300, 200);
     miniFenetre.setLocationRelativeTo(this);

     JLabel labelX = new JLabel("Coordonnée X (0-5) :");
     JTextField inputX = new JTextField();

     JLabel labelY = new JLabel("Coordonnée Y (0-5) :");
     JTextField inputY = new JTextField();

     JLabel messageErreur = new JLabel("");
     messageErreur.setForeground(Color.RED);

     JButton valider = new JButton("Valider");
     valider.addActionListener(e -> {
     try {
     int x = Integer.parseInt(inputX.getText());
     int y = Integer.parseInt(inputY.getText());

     if (x >= 0 && x <= 5 && y >= 0 && y <= 5) {
     // Action à exécuter avec les coordonnées
     cJ.helicopter(x,y);
     System.out.println("Coordonnées valides : x=" + x + ", y=" + y);
     miniFenetre.dispose(); // Fermer la mini-fenêtre
     } else {
     messageErreur.setText("Coordonnées invalides !");
     }
     } catch (NumberFormatException ex) {
     messageErreur.setText("Veuillez entrer des nombres valides !");
     }
     });

     miniFenetre.add(labelX);
     miniFenetre.add(inputX);
     miniFenetre.add(labelY);
     miniFenetre.add(inputY);
     miniFenetre.add(new JLabel());  // espace vide
     miniFenetre.add(valider);
     miniFenetre.add(messageErreur);

     miniFenetre.setVisible(true);
     }

     /**Methode makeFenetreDonnerCle ()
      * qui permet d'afficher une fenetre permettant au joueur de proceder aux échanges de clé **/
    private void makeFenetreDonnerCle() {
        JDialog mini = new JDialog(this, "Donner une clé", true);
        mini.setLayout(new GridLayout(4, 2, 10, 10));
        mini.setSize(400, 250);
        mini.setLocationRelativeTo(this);

        JLabel labelCle = new JLabel("Choisir une clé :");
        JComboBox<Clef> comboCle = new JComboBox<>(cJ.getJoueur().getClefs().toArray(new Clef[0]));

        JLabel labelJoueur = new JLabel("À quel joueur ?");
        JComboBox<Joueur> comboJoueur = new JComboBox<>();
        for (Joueur j : joueurs) {
            if (j != cJ.getJoueur() && j.getX() == cJ.getJoueur().getX() && j.getY() == cJ.getJoueur().getY()) {
                comboJoueur.addItem(j);
            }
        }

        JButton valider = new JButton("Donner");
        valider.addActionListener(e -> {
            Clef selectedCle = (Clef) comboCle.getSelectedItem();
            Joueur receveur = (Joueur) comboJoueur.getSelectedItem();
            if (selectedCle != null && receveur != null) {
                cJ.donnerCle(receveur, selectedCle);
                updateBoutonsSpeciaux();
                mini.dispose();
            }
        });

        mini.add(labelCle);
        mini.add(comboCle);
        mini.add(labelJoueur);
        mini.add(comboJoueur);
        mini.add(new JLabel());
        mini.add(valider);

        mini.setVisible(true);
    }

    /**methode updateBoutonsSpeciaux()
     * permet de mettre à jour l'état des boutons spéciaux et du nombre d'action possible par joueur**/
    public void updateBoutonsSpeciaux() {
        panelActions.removeAll(); // On vide pour tout reconstruire proprement

        JButton ass = makeButton("💧 Assécher");
        ass.addActionListener(e -> {
            cJ.assecherZone();
            actionsLabel.setText("⚙️ Actions restantes : " + cJ.getActionsRestantes());
            updateBoutonsSpeciaux(); // au cas où le sac est utilisé
        });

        JButton recup = makeButton("🗿 Récupérer artefact");
        recup.addActionListener(e -> {
            cJ.recupArtJoueur();
            actionsLabel.setText("⚙️ Actions restantes : " + cJ.getActionsRestantes());
        });

        JButton cle = makeButton("🔑 Chercher une clef");
        cle.addActionListener(e -> {
            cJ.chercherClef();
            actionsLabel.setText("⚙️ Actions restantes : " + cJ.getActionsRestantes());
        });

        panelActions.add(ass);
        panelActions.add(recup);
        panelActions.add(cle);

        // Ajouter Sac de Sable si disponible
        JButton sacSableBtn = makeButton("Sac de sable");
        sacSableBtn.addActionListener(e-> {
            makeFenetreSacSable();
            updateBoutonsSpeciaux(); // mise à jour après usage éventuel
        });
        sacSableBtn.setEnabled(cJ.getJoueur().getActionValues("Sac De Sable") > 0);
        panelActions.add(sacSableBtn);



        JButton donnerCleBtn = makeButton(" Donner une clé");
        donnerCleBtn.addActionListener(e -> makeFenetreDonnerCle());
        panelActions.add(donnerCleBtn);
        panelActions.revalidate();
        panelActions.repaint();

    }



}


