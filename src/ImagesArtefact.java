import javax.swing.*;
import java.awt.*;
public class ImagesArtefact extends JPanel {
    private ImageIcon imgEau;
    private ImageIcon imgFeu;
    private ImageIcon imgTerre;
    private ImageIcon imgAir;
    private Zone zone;

    public ImagesArtefact(){
        imgEau = new ImageIcon("src/Artefacts/artefactEau.png");
        imgFeu = new ImageIcon("src/Artefacts/artefactFeu.png");
        imgTerre = new ImageIcon("src/Artefacts/artefactTerre.png");
        imgAir = new ImageIcon("src/Artefacts/artefactAir.png");

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner l’état de la zone (ex: normale, inondée)
        if (zone.getEtat() == Zone.Etat.inonde) {
            g.setColor(new Color(0, 0, 255, 50)); // Bleu transparent
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (zone.getEtat() == Zone.Etat.submerge) {
            g.setColor(new Color(0, 0, 255, 120)); // Bleu foncé
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Dessiner l’artefact si présent
        if (zone instanceof ZoneElement) {
            Artefact a = ((ZoneElement) zone).getArt();
            ImageIcon img = null;

            switch (a.getType()) {
                case EAU:
                    img = imgEau;
                    break;
                case FEU:
                    img = imgFeu;
                    break;
                case TERRE:
                    img = imgTerre;
                    break;
                case AIR:
                    img = imgAir;
                    break;
            }

            if (img != null) {
                g.drawImage(img.getImage(), getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2, this);
            }
        }
    }
    public void setZone(Zone zone) {
        this.zone = zone;
        repaint();
    }



}
