import java.util.ArrayList;
import java.util.Random;

public class Joueur {
    // attribut
    //position du joueur sur la grille
    int x,y;

    ArrayList<Clef> clefs; // peux avoir plusieurs clés
    ArrayList<Artefact> art; // peut avoir plusieurs artefacte

    public Joueur(int maxX, int maxY) {
        Random rand = new Random();
        this.x = rand.nextInt(maxX); // maxX = 6 → donc index 0 à 5
        this.y = rand.nextInt(maxY);
        clefs = new ArrayList<>();
        art = new ArrayList<>();
    }


    /**methode getter addClef
     * @param cle : Clef
     * Ajoute une clé récupéré par le joueur si celui ci est valide **/
    public void addClefJ(Clef cle){
        this.clefs.add(cle);
    }

    /**methode getter addArt
     * @param art : Artefact
     * Ajoute une Artefacte récupéré par le joueur **/
    public void addArt(Artefact art){
        this.art.add(art);
    }

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

    @Override
    public String toString() {
        return "Joueur " + "position " +
                "(" + getX() + "," + getY() + ") " +
                " nombre clefs = " + nbClefs() +
                 " nombre artefact = " + nbArts() ;
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }



}
