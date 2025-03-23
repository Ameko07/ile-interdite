/** classe abstrait de zone
 * permet de diférencier les 3 types de zone avec leur propre comportement
 * **/
abstract class Zone {
    // attribut
    Etat etat;
    Clef cle ;

    //constructor
    public Zone(){
        etat = Etat.normal;
        cle = null;
    }
    //methodes
    public Etat getEtat(){
       return etat;
    }
    /**methode changeState
     * permet de modifier l'etat de la zone **/
    public void changeState(Etat e){
            etat = e;
    }

    /**setter addCle
     * @param c : Clef
     * permet d'ajouter une clf dans la zone surtout pour l'initialisation **/
    public void addCle(Clef c){
        this.cle = c;

    }

    /**setter deleteClef
     * permet de supprimer une clé , utile pour la récupération dans la zone **/
    void deleteClef(){
        this.cle = null;
    }

    public enum Etat{normal, inonde , submerge}
}


// les zones sont mis em Etat normale par defaut

/**Classe ZoneOrdinqire
 * une zone normale avec un etat normale **/
class ZoneOrdinaire extends Zone{

    // constructor
    public ZoneOrdinaire(){
        super();
    }

    @Override
    public String toString() {
        return "Zone Ordinaire{" +
                " Etat = "+ this.etat +" }" ;
    }
}

/**classe ZoneEliport
 * zone d'issue des joueur
 * etat normale par defaut **/

class ZoneEliport extends Zone{
    //attribut
    int nbJoueur;// permet de savoir si l'evacuation des joueur est possible

    // cpnstructor
    public ZoneEliport (){
        super();
        nbJoueur = 0;
    }

    /**getter getNbJoueur
     * @return nbJoeur : int **/
    public int getNbJoueur() {
        return nbJoueur;
    }

    @Override
    public String toString() {
        return "ZoneEliport{" +
                "Etat= " + this.etat +
                "nbJoueur=" + nbJoueur +
                '}';
    }
}

/**Classe ZoneElement
 * une zone d'element pour chaque type d'artefact
 * Etat normale par defaut **/

class ZoneElement extends Zone{
    // attribut récupéré type element dans Artefact
    Artefact.Element element;
    Artefact art ;
    boolean artefact;


    // constructor
    public ZoneElement (Artefact.Element e){
       super();
       element = e;
       art = null;
       artefact = false; // ne contient rien par defaut
    }

    /**setter setArt
     * @param a : Artefact
     * ajoute un nouvel artefacte**/
    public void setArt(Artefact a) {
        // on ajoute l'artefact
        this.art = a;
        this.artefact = true;
    }

    /**getter de l'elemnent de la zone**/
    public Artefact.Element getElement() {
        return element;
    }

    /**methode thereIsQrtefact
     * @return true or false presence d'artefact **/
    public boolean thereIsArtefact() {
        return artefact;
    }

    @Override
    public String toString() {
        return "ZoneElement{" +
                "element=" + element +
                ", art=" + art.element +
                ", artefact=" + artefact +
                '}';
    }
}



