public class Clef {
    Artefact.Element cleElem;

    public Clef(Artefact.Element e){
        cleElem = e;
    }

    public Artefact.Element getCleElem() {
        return cleElem;
    }

    @Override
    public String toString() {
        return "Clef de type " + cleElem ;
    }
}
