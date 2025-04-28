import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreSaisieNoms extends JFrame {
    private JTextField[] champsNoms = new JTextField[4];

    public FenetreSaisieNoms() {
        setTitle("Entrer les noms des joueurs");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Création des champs
        for (int i = 0; i < 4; i++) {
            add(new JLabel("Nom du joueur " + (i + 1) + " :"));
            champsNoms[i] = new JTextField();
            add(champsNoms[i]);
        }

        // Espace vide
        add(new JLabel(""));

        // Bouton "Lancer le jeu"
        JButton boutonLancer = new JButton("🚀 Lancer la partie !");
        boutonLancer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerJeu();
            }
        });
        add(boutonLancer);

        setVisible(true);
    }

    private void lancerJeu() {
        String[] nomsJoueurs = new String[4];
        for (int i = 0; i < 4; i++) {
            nomsJoueurs[i] = champsNoms[i].getText().isEmpty() ? "Joueur " + (i + 1) : champsNoms[i].getText();
        }

        dispose(); // Fermer la fenêtre de saisie

        new Fenetre(); // ➔ Ici tu peux aussi passer nomsJoueurs si tu veux afficher leurs noms !
    }
}
