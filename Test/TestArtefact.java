import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**Classe de teste pour la classe Artefact**/
public class TestArtefact {

    @Test
    public void testGetType(){
        Artefact art = new Artefact(Artefact.Element.EAU);
        assertEquals(Artefact.Element.EAU, art.getType());
    }






}
