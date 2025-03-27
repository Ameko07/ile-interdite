import java.util.ArrayList;
import java.util.Random;

public class Joueur {
    // attribut
    //position du joueur sur la grille
    int x,y;

    ArrayList<Clef> clefs; // peux avoir plusieurs clés
    ArrayList<Artefact> art; // peut avoir plusieurs artefacte
    private int id;

    //constructor
    public Joueur(int maxX, int maxY) {
        Random rand = new Random();
        this.x = rand.nextInt(maxX); // maxX = 6 → donc index 0 à 5
        this.y = rand.nextInt(maxY);
        clefs = new ArrayList<>();
        art = new ArrayList<>();
    }



    //SETTER
    /**setter setId
     * affecte ne id au joueur **/
    public void setId(int id) {
        this.id = id;
    }

    /**setter addClef
     * @param c : Clef
     * ajoute une clé récupéré dans la liste du joueur **/
    public void addClef(Clef c){
        this.clefs.add(c);

    }
    /**setter setPosition
     * @param x : int
     * @param y : int
     * affect les coordonnée du joueur **/
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    //GETTER
    /**getter getClefs
     * @return clefs : ArrayList
     * récupère le tableau des clefs que possède le joueur **/
    public ArrayList<Clef> getClefs() {
        return clefs;
    }

    /**getter nbClefs
     * @return nbcl
     * donne le nombre de clefs possédé**/
    public int nbClefs(){
        return this.clefs.size();
    }

    /**getter getArt
     * @return art : ArrayList
     * récupère le tableau d'artefact possédé par le joueur **/
    public ArrayList<Artefact> getArt() {
        return art;
    }



    /**getter nbArts
     * @return nb artefact
     * donne le nombre d'artefacts possédé**/
    public int nbArts(){
        return art.size();
    }

    /**getter getX
     * @return x **/
    public int getX() {
        return x;
    }

    /**getter getY
     * @return y**/
    public int getY() {
        return y;
    }

    /**getter getId
     * retourne l'id du joueur  **/
    public int getId() {
        return this.id;
    }

    /**methode getter addArt
     * @param art : Artefact
     * Ajoute une Artefacte récupéré par le joueur **/
    public void addArt(Artefact art){
        this.art.add(art);
    }

    /**methode possedeClef
     * @param clef : Clef
     * @return true si clef existe dans la liste sinon false**/
    public boolean possedeClef(Clef clef){
        for (Clef c : this.clefs){
            if (c == clef) return true ;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Joueur " + "position " +
                "(" + getX() + "," + getY() + ") " +
                " nombre clefs = " + nbClefs() +
                " nombre artefact = " + nbArts() ;
    }




}
