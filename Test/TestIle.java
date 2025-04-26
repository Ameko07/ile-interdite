import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**Classe teste pour la classe Ile**/
public class TestIle {
    Ile ile ;

    @BeforeEach
    public void setup(){
        ile = new Ile();


    }

    /**testes pour la taille de l'ile**/
    @Test
    public void TestTaille(){

        assertEquals(6,ile.getWidth());
        assertEquals(ile.getHeight(), ile.getWidth());
        assertEquals(6, ile.getGrille().length);

    }

    @Test
    public void testInitialisation(){
        Zone[][] grille = ile.getGrille();


        int nbZoneEli = 0;
        int nbZoneElement = 0;
        int zoneClefs = 0;

        for (int i = 0 ; i< grille.length ; i++){
            for (int j = 0 ; j< grille[i].length ; j++){
                Zone z = ile.getZone(i,j);
                if (z instanceof ZoneEliport){
                    nbZoneEli++;
                }else if (z instanceof ZoneElement){
                    nbZoneElement++;
                }else if (z instanceof ZoneOrdinaire){
                    if (((ZoneOrdinaire) z).thereIsClef())
                        zoneClefs++;
                }

            }
        }

        assertEquals(2, nbZoneEli, "il ne devrait n'y avoir que 2 zoneEli");
        assertEquals(4,nbZoneElement, "il ne devrait n'y avoir que 4 zoneElement");

        //teste pour les cle
        assertEquals(4, zoneClefs, "Il devrait n'y avoir que 4 clefs ");



    }

    @Test
    public void testSetterEtat(){
        Zone[][] grille = ile.getGrille();
        Zone z = grille[0][0];
        // vérifie l'état de la Zone par défaut
        assertEquals(Zone.Etat.normal,z.getEtat(), "l'Etat par défaut doit être normal");

        ile.setEtatZone(0,0, Zone.Etat.inonde );
        assertEquals(Zone.Etat.inonde, z.getEtat(), "l'etat doit être modifier par inondé" );

        ile.setEtatZone(0,0, Zone.Etat.submerge );
        assertEquals(Zone.Etat.submerge, z.getEtat(), "l'etat doit être modifier par submergé" );

    }

    @Test
    public void testDeletArt(){
        Zone[][] grille = ile.getGrille();

        for (int i = 0 ; i< grille.length; i++){
            for (int j = 0; j<grille[i].length; j++){
                Zone z = grille[i][j];
                if (z instanceof ZoneElement){
                    ile.deletArtZone(i,j);
                    assertEquals(null, ((ZoneElement) z).getArt(), "il ne devrait plus y avoir d'artefact dans cette zone");
                    break;
                }
            }
        }

    }


}
