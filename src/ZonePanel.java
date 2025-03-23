import javax.swing.*;
import java.awt.*;

/**c'est une classe qui va représenter une donnée sur la fenêtre **/

public class ZonePanel extends JPanel {
    private Zone zone;
    private Joueur joueur;
    public void setJoueur(Joueur j) {
        this.joueur = j;
    }


    public ZonePanel(Zone z) {
        this.zone = z;
        updateColor();

        // Création de l'écouteur avant de l'ajouter

        ZoneMouse mouseListener = new ZoneMouse(zone) ;
        // Ajout du MouseListener à  JPanel
        addMouseListener(mouseListener);



    }

    /**methode updateColor
     * Change la couleur des zone en fonction de leur type et de leur Etat**/
    private void updateColor() {
        Zone.Etat etat = zone.getEtat();
        Color baseColor = Color.LIGHT_GRAY;

        if (zone instanceof ZoneElement) {
            switch (((ZoneElement) zone).getElement()) {
                case AIR -> baseColor = Color.GRAY;
                case EAU -> baseColor = Color.BLUE;
                case TERRE -> baseColor = new Color(150, 101, 38);
                case FEU -> baseColor = Color.RED;
            }
        } else if (zone instanceof ZoneOrdinaire) {
            baseColor = Color.YELLOW;
        } else if (zone instanceof ZoneEliport) {
            baseColor = Color.BLACK;
        }

        // Couleur d'arrière-plan selon l'état
        switch (etat) {
            case inonde -> setBackground(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 100));
            case submerge -> setBackground(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 50));
            default -> setBackground(baseColor);
        }

        // Si le joueur est sur cette zone → mettre une bordure ou un indicateur
        if (joueur != null && joueur.getX() == zone.getX() && joueur.getY() == zone.getY()) {
            setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        }
    }

    public void refresh() {
        updateColor();
        repaint();
    }

}
