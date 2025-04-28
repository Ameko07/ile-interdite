import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JFrame {
    private float opacity = 0f;
    private float sizeMultiplier = 1f;
    private Timer animationTimer;
    private int helicoX = -100; // Position initiale de l'hélico (hors écran)
    private Image helicoImage;

    public SplashScreen() {
        setTitle("Bienvenue !");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); // Fond transparent

        // Charger l'image de l'hélico
        helicoImage = new ImageIcon("src/images/helico.png").getImage();

        setVisible(true); // ✨ OBLIGATOIRE pour l'afficher !!!!

        animationTimer = new Timer(30, new AnimationHandler());
        animationTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        // Ne pas appeler super.paint(g) sur JFrame sinon clignotements !!
        Graphics2D g2 = (Graphics2D) g;

        // Fond dégradé
        GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180), getWidth(), getHeight(), new Color(169, 78, 29));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Hélico
        if (helicoImage != null) {
            g2.drawImage(helicoImage, helicoX, 50, 100, 60, this); // Taille hélico
        }

        // Texte animé
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(opacity, 1f)));
        g2.setFont(new Font("Serif", Font.BOLD, (int) (48 * sizeMultiplier)));
        g2.setColor(new Color(255, 255, 240));
        String message = "🏝️ Bienvenue à l'Île Interdite 🏝️";
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(message);
        g2.drawString(message, (getWidth() - textWidth) / 2, getHeight() / 2);
    }

    private class AnimationHandler implements ActionListener {
        private int elapsed = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            elapsed += 30;

            opacity += 0.02f;
            sizeMultiplier += 0.001f;

            // Avancer l'hélico vers la droite
            helicoX += 5; // Vitesse du déplacement

            repaint();

            if (elapsed >= 3000) { // Après 5 secondes
                animationTimer.stop();
                dispose(); // Fermer le splash
                new FenetreSaisieNoms();
                // Lancer ton vrai jeu
            }
        }
    }
}
