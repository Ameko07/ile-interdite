import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestZone {

    // on va tester les zones un par un
    @Test
    public void testZoneOrdinaire (){
        Zone z = new ZoneOrdinaire("");
        assertInstanceOf(ZoneOrdinaire.class, z, "c'est bien une ZoneOrdinaire ");
        assertFalse(z instanceof ZoneElement, "Ce n'est pas une zoneElement");
        assertFalse(z instanceof ZoneEliport, "C'est n'est pas une zoneEliport");

        // à faire dans un test à part
        assertSame(Zone.Etat.normal, z.getEtat(), "L'etat normal par defaut");
        assertNull(((ZoneOrdinaire) z).getCle(), "Pas de cle par defaut");

        // seul les zones Ordinaire possède une clé donc test clé
        ((ZoneOrdinaire) z).addCle(new Clef(Artefact.Element.EAU));
        // la clé a bien été instancié ?
        assertTrue(((ZoneOrdinaire) z).thereIsClef());
        assertEquals(new Clef(Artefact.Element.EAU), ((ZoneOrdinaire) z).getCle());

        // suppression de la clé
        ((ZoneOrdinaire) z).deleteClef();
        assertNotEquals(new Clef(Artefact.Element.EAU), ((ZoneOrdinaire) z).getCle());
        assertNull(((ZoneOrdinaire) z).getCle());

        // position n'est pas utilisé dans le constructeur mais ailleurs (à mettre dans un test sur toute les zones)
        z.setPosition(0,0);
        assertEquals(0,z.getX());
        assertEquals(0,z.getY());



    }

    @Test
    public void testZoneEliport(){
        ZoneEliport z = new ZoneEliport("");

        assertInstanceOf(ZoneEliport.class, z, "C'est une ZoneEliport");
        assertEquals(0, z.getNbJoueur(), "il n'y a pas de joueur de base" );

    }

    @Test
    public void testZoneElement(){

        ZoneElement z = new ZoneElement("",Artefact.Element.EAU); // à finir

        assertInstanceOf(ZoneElement.class, z);

        // Teste des methodes
        assertEquals(Artefact.Element.EAU, z.getElement());

        assertFalse(z.thereIsArtefact());
        z.setArt(new Artefact(Artefact.Element.EAU));
        assertTrue(z.thereIsArtefact());
        assertEquals(new Artefact(Artefact.Element.EAU), z.getArt());
        z.deletArt();
        assertFalse(z.thereIsArtefact());
        assertEquals(null , z.getArt());


    }


}
