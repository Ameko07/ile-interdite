import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Joueur {
    // attribut
    //position du joueur sur la grille
    int x,y;

    ArrayList<Clef> clefs; // peux avoir plusieurs clés
    ArrayList<Artefact> art; // peut avoir plusieurs artefacte
    private int id;
    HashMap<String, Integer> ActionSpecial ;


    //constructor
    public Joueur(int maxX, int maxY) {
        Random rand = new Random();
        this.x = rand.nextInt(maxX); // maxX = 6 → donc index 0 à 5
        this.y = rand.nextInt(maxY);
        clefs = new ArrayList<>();
        art = new ArrayList<>();
        ActionSpecial = new HashMap<>();
        ActionSpecial.put("Sac De Sable", 0);
        ActionSpecial.put("Helicopter", 0);
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

    /**Setter addAction
     * @param s  : String
     * incrémente le nombre d'action disponibe**/
    public void addAction(String s){
        int acc = this.ActionSpecial.get(s);

        this.ActionSpecial.put(s,acc+1);
    }
    /**setter SubAction
     * @param s : String
     * décrémente le nombre d'action s'il n'a pas encore atteint 0**/
    public void subAction(String s){
        int acc = this.ActionSpecial.get(s);
        if (acc == 0){
            System.out.println("Il n'y plus d'Action disponible");
        }else {
            acc--;
            this.ActionSpecial.put(s,acc);
        }
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

    /**getter getActionValues
     *  @return le nombre d'action restante **/
    public int getActionValues(String s){
        if (! this.ActionSpecial.containsKey(s))  throw new IllegalArgumentException("Action inconnu" );

        return ActionSpecial.get(s);

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
            if (c.getCleElem().equals(clef.getCleElem())) return true;

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

    public void removeClef(Clef clef){
        for (int i = 0; i < clefs.size(); i++) {
            if (clefs.get(i).getCleElem().equals(clef.getCleElem())) {
                clefs.remove(i);
                break; // on enlève une seule clef
            }
        }
    }








}
