import javax.swing.*;
import java.awt.*;

/**c'est une classe qui va représenter une donnée sur la fenêtre **/

public class ZonePanel extends JPanel {
    private Zone zone;


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

        Color BaseColor = null;

        //choix de la couleur d'une zone element
        if (zone instanceof ZoneElement) {
            Artefact.Element element = ((ZoneElement) zone).getElement();

            // chaque element a sa propre couleur
            switch (element) {
                case AIR :BaseColor = Color.GRAY; break;
                case EAU : BaseColor =Color.BLUE;break;
                case TERRE :BaseColor =Color.decode("#966526");break;
                case FEU :BaseColor =Color.RED;break;
                default : BaseColor  = Color.WHITE;break;

            }
        } else if (zone instanceof ZoneOrdinaire) {
            BaseColor =Color.YELLOW;


        } else if (zone instanceof ZoneEliport) {
            BaseColor =Color.BLACK;

        }

        //Couleur selon l'etat
        switch (etat){
            case inonde : setBackground(new Color(BaseColor.getRed(), BaseColor.getGreen(), BaseColor.getBlue(), 100));
            break;
            case submerge: setBackground(new Color(BaseColor.getRed(), BaseColor.getGreen(), BaseColor.getBlue(), 150));
                break;
            case normal:setBackground(BaseColor);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + etat);
        }


    }

}
