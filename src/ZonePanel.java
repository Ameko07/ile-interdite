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

    }

    /**setter setJoueur
     * @param j : Joueur
     * Modifie l'attribut du joueur **/
    public void setJoueur(Joueur j) {
        this.joueur = j;
    }




    /**methode updateColor
     * Change la couleur des zone en fonction de leur type et de leur Etat**/
    private void updateColor() {
        // on récupère l'etat de la zone
        Zone.Etat etat = zone.getEtat();


        //couleur par type
        if (zone instanceof ZoneElement) {
            switch (((ZoneElement) zone).getElement()) {
                case AIR -> setBackground(Color.GRAY);
                case EAU -> setBackground(Color.BLUE);
                case TERRE -> setBackground (new Color(150, 101, 38));
                case FEU -> setBackground(Color.RED);
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


}
