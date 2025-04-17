import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**Classe de teste pour la classe Artefact**/
public class TestArtefact {

    @Test
    public void testGetType(){
        Artefact art = new Artefact(Artefact.Element.EAU);
        Artefact art2 = new Artefact(Artefact.Element.FEU);
        assertEquals(Artefact.Element.EAU, art.getType());
        assertNotEquals(art,art2);
    }






}
