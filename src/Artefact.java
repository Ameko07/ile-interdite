import com.sun.tools.jconsole.JConsoleContext;

import java.util.Objects;

class Artefact {
    // Attribut
    Element element;



    public enum Element{EAU,AIR,TERRE,FEU}

    //constructor
    public Artefact(Element e){
        element = e;

    }
    /**getter getType
     * @return element : Element **/
    public Element getType(){
        return element;
    }

    @Override
    public String toString() {
        return "Artefact de type element"+ element; // j'ai ajouté element ici
    }


    // pour le teste

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Artefact artefact = (Artefact) o;
        return element == artefact.element;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(element);
    }
}
