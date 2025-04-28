import javax.swing.*;
import java.awt.*;

/**c'est une classe qui va représenter une zone donnée sur la fenêtre **/

public class ZonePanel extends JPanel {
    //atribut
    private Zone zone;
    private Joueur joueur;

    // attribut du texte d'etat de zone
    private JLabel etatTxt =  new JLabel("", SwingConstants.CENTER);
    private Color[] couleursJoueurs = {Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.CYAN};

    private ImagesArtefact imgArt;
    private java.util.List<Joueur> joueurs;  // Liste de tous les joueurs
    private ImageIcon[] imagesPions = new ImageIcon[4]; // 4 pions



    //Constructor
    public ZonePanel(Zone z) {
        this.zone = z;
        updateColor();
        setLayout(new BorderLayout());  // Permet d'ajouter des composants

        // Création de l'écouteur avant de l'ajouter

        ZoneMouse mouseListener = new ZoneMouse(zone) ;
        // Ajout du MouseListener à  JPanel
        addMouseListener(mouseListener);

        //initialisation du label
        String e = zone.toString(Zone.Etat.normal);

        this.etatTxt = new JLabel(e, SwingConstants.CENTER);
        setLayout(new BorderLayout()); // Permet d'ajouter le label au centre
        add(etatTxt, BorderLayout.NORTH);

        // initiaisation d'images d'artefact
        imgArt = new ImagesArtefact();
        imgArt.setOpaque(false);
        imgArt.setBackground(new Color(0, 0, 0, 0)); // Transparent
        add(imgArt, BorderLayout.CENTER);
        for (int i = 0; i < 4; i++) {
            imagesPions[i] = new ImageIcon("src/images/Pion" + (i+1) + ".png");
        }


    }

    /**setter setJoueur
     * @param j : Joueur
     * Modifie l'attribut du joueur **/
    public void setJoueur(Joueur j) {
        this.joueur = j;
    }


    public void setJoueurs(java.util.List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }


    /**methode updateColor
     * Change la couleur des zone en fonction de leur type et de leur Etat**/
    private void updateColor() {
        // on récupère l'etat de la zone
        Zone.Etat etat = zone.getEtat();


        //couleur par type
        if (zone instanceof ZoneElement) {
            switch (((ZoneElement) zone).getElement()) {
                case AIR -> setBackground(new Color(51, 122, 82));
                case EAU -> setBackground(new Color(64,94,145));
                case TERRE -> setBackground (new Color(150, 90, 44));
                case FEU -> setBackground(new Color(255,85,0));
            }
        } else if (zone instanceof ZoneOrdinaire) {
            setBackground(Color.YELLOW);
        } else if (zone instanceof ZoneEliport) {
            setBackground(Color.BLACK);
        }

        // label modifier par rapport à l'état de la zone
        switch (etat) {
            case inonde -> etatTxt.setText(zone.toString(etat));
            case submerge -> etatTxt.setText(zone.toString(etat));
            default -> etatTxt.setText(zone.toString(etat));
        }
        // mise en couleur du texte selon l'état
        if (etat == Zone.Etat.submerge) {
            etatTxt.setForeground(Color.RED);
        } else if (etat == Zone.Etat.inonde) {
            etatTxt.setForeground(Color.BLUE);
        } else {
            etatTxt.setForeground(Color.BLACK);
        }

        // Si le joueur est sur cette zone → mettre une bordure ou un indicateur
        if (joueur != null && joueur.getX() == zone.getX() && joueur.getY() == zone.getY()) {
            setBorder(BorderFactory.createLineBorder(couleursJoueurs[joueur.getId()], 3));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        }

    }
    /**methode refresh
     * met à jour les couleur et les attributs des zones**/
    public void refresh() {
        imgArt.setZone(zone);
        updateColor();

        repaint();
    }
    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (zone != null) { //
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(255, 255, 255, 90)); // Blanc transparent
            g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
            g2.dispose();
        }




        if (joueurs != null && zone != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            // Active l'anti-aliasing pour un rendu plus lisse
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int nbJoueursSurZone = 0;

            // Compte combien de joueurs sont sur cette zone
            for (Joueur j : joueurs) {
                if (j.getX() == zone.getX() && j.getY() == zone.getY()) {
                    nbJoueursSurZone++;
                }
            }

            int index = 0;
            for (Joueur j : joueurs) {
                if (j.getX() == zone.getX() && j.getY() == zone.getY()) {
                    Image img = imagesPions[j.getId()].getImage();

                    int taille = Math.min(getWidth(), getHeight()) / 2; // Taille adaptée à la case
                    int spacing = 5; // Espace entre les pions

                    int totalWidth = nbJoueursSurZone * (taille + spacing) - spacing;
                    int startX = (getWidth() - totalWidth) / 2;

                    int x = startX + index * (taille + spacing);
                    int y = (getHeight() - taille) / 2;

                    // Ombre douce sous le pion
                    g2.setColor(new Color(0, 0, 0, 80));
                    g2.fillOval(x + 3, y + 3, taille, taille);

                    // Dessine l'image du pion
                    g2.drawImage(img, x, y, taille, taille, this);

                    index++;
                }
            }

            g2.dispose();
        }
    }


}
