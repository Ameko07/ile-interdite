import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ZoneMouse implements MouseListener {
    private Zone zone;

    public ZoneMouse (Zone z){
        this.zone = z;
    }


    @Override
    public void mouseClicked(MouseEvent e) {



        if (zone instanceof ZoneElement) {
            System.out.print("Zone Element : ");
            Artefact.Element el = ((ZoneElement) zone).getElement();
            switch (el) {
                case AIR -> System.out.println("de Type AIR");
                case EAU -> System.out.println("de Type EAU");
                case TERRE -> System.out.println("de Type TERRE");
                case FEU -> System.out.println("de Type FEU");

            }

        } else if (zone instanceof ZoneOrdinaire) {
            System.out.println("Zone ordinaire");
            // Exemple d'action : changer de couleur en cliquant

        } else if (zone instanceof ZoneEliport) {
            System.out.println("Zone Eliport");

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
