import java.util.Objects;

public class Clef {
    Artefact.Element cleElem;

    public Clef(Artefact.Element e){
        cleElem = e;
    }

    public Artefact.Element getCleElem() {
        return cleElem;
    }





    // création de IntellIJ pour les testes
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Clef clef = (Clef) o;
        return cleElem == clef.cleElem;
    }

    @Override
    public String toString() {
        return "Clef de type " + cleElem ;
    }

    /*@Override
    public int hashCode() {
        return Objects.hashCode(cleElem);
    }*/
}
