import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestJoueur {

    @Test
    public void TestInit(){
        Joueur j = new Joueur(6,6);
        assertNotNull(j);
        assertEquals(0,j.nbClefs());
        assertEquals(0,j.nbArts());
        assertEquals(0,j.getActionValues("Sac De Sable"));
        assertEquals(0,j.getActionValues("Helicopter"));

    }

    @Test
    /**teste pour es getter et setter **/
    public void TestGSetter(){
        Joueur j = new Joueur(6,6);

        // Création des clé entant qu'objet utilisable
        Clef c1 = new Clef(Artefact.Element.EAU);
        Clef c2= new Clef(Artefact.Element.TERRE);
        Clef c3 = new Clef(Artefact.Element.AIR);
        Clef c4 = new Clef(Artefact.Element.FEU);

        //créations des artéfactes

        Artefact a1 = new Artefact(Artefact.Element.EAU);
        Artefact a2 = new Artefact(Artefact.Element.FEU);
        Artefact a3 = new Artefact(Artefact.Element.TERRE);
        Artefact a4 = new Artefact(Artefact.Element.AIR);


        // test Position
        j.setPosition(0,0);
        assertEquals(j.getX(),j.getY());

        //test que les clefs soient bien initialise
        j.addClef(c1);
        j.addClef(c2);
        assertTrue(j.possedeClef(c1));
        assertTrue(j.possedeClef(c2));
        assertFalse(j.possedeClef(c3));
        assertFalse(j.possedeClef(c4));

        //verification de la liste de clefs
        ArrayList <Clef> cL = new ArrayList<> ();
        cL.add(c1);
        cL.add(c2);
        assertEquals(cL,j.getClefs());

        j.addClef(c4);
        assertEquals(3,j.nbClefs());

        //suppression d'une cle
        j.removeClef(c2);
        assertEquals(2,j.nbClefs());
        assertFalse(j.possedeClef(c2));

        // test de setId
        j.setId(4);
        assertEquals(4,j.getId());

        //ajout d'artefact
        j.addArt(a1);
        j.addArt(a3);

        ArrayList<Artefact> arts = new ArrayList<>();
        arts.add(a1);
        arts.add(a3);

        assertEquals(2,j.nbArts());
        assertFalse(j.nbArts()==0);
        assertEquals(arts,j.getArt());

        //incrémentation du nombre d'actions spéciales
        j.addAction("Sac De Sable");
        assertFalse(j.getActionValues("Sac De Sable")== 0);
        assertTrue(j.getActionValues("Sac De Sable")==1);




    }
}
