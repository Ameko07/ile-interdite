import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    public Fenetre(){

        //titre
        setTitle("Ile interdite ");

        //taille de la fenetre


        //Fermeture
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //centrer à l'écran
        //setLocationRelativeTo(null);


        //bouton
        // Création d'un bouton
        JButton bouton = new JButton("fin de tour");
        bouton.setPreferredSize(new Dimension(100,50));
        add(bouton);

        JPanel panelButton = new JPanel();
        panelButton.add(bouton);
        add(panelButton, BorderLayout.EAST);


        //initiqlisqtion de l'ile
        Ile ile = new Ile();
        JPanel gridPanel = new JPanel();
         // Taille spécifique pour le JPanel
        gridPanel.setPreferredSize(new Dimension(100, 100));
        //JP.setBackground(Color.BLACK);
        gridPanel.setLayout(new GridLayout(ile.getWidth(), ile.getHeight()));
        for (int i = 0; i < ile.getWidth(); i++) {
            for (int j = 0; j < ile.getHeight(); j++) {
                Zone zone = ile.getZone(i, j); // Récupération de la zone
                ZonePanel zP = new ZonePanel(zone); // Création du bouton
                gridPanel.add(zP); // Ajout du bouton au panel
            }
        }

        add(gridPanel,BorderLayout.CENTER);
        pack();
        setSize(1200,800);




        setVisible(true);
    }
}


