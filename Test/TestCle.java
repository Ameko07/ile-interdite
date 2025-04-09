import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**teste pour la classe clef**/
public class TestCle {

    @Test
    public void testGetCleElem(){

        Clef cle = new Clef(Artefact.Element.EAU);
        assertEquals(Artefact.Element.EAU, cle.getCleElem());

    }

}
