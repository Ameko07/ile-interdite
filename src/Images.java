import javax.swing.*;
import java.awt.*;

public class Images extends JPanel {
    private Image image;

    public Images(String imagePath) {
        image = new ImageIcon(imagePath).getImage();
    }

    public Image getImage() {
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
