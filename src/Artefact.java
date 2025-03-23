import com.sun.tools.jconsole.JConsoleContext;

class Artefact {
    // Attribut
    Element element;



    public enum Element{EAU,AIR,TERRE,FEU}

    //constructor
    public Artefact(Element e){
        element = e;

    }
    public Element getType(){
        return element;
    }

    @Override
    public String toString() {
        return "Artefact de type element"+ element; // j'ai ajouté element ici
    }
}
