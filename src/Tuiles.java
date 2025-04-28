import java.awt.*;

public class Tuiles {
    Images imgTuile;

    public Tuiles (String s){
        imgTuile = new Images(s);

    }

    /**Methode hasExtension() qui prend en paramètre :
     * @param fileName : String le nom du fichier
     * @param extension : String la chaine de caractère à vérifier
     * @return boolean en présence de l'extension dans le nom du fichier
     * **/
    public boolean hasExtension(String fileName, String extension) {
        if (fileName == null || extension == null) {
            return false;
        }
        return fileName.toLowerCase().endsWith(extension.toLowerCase());
    }

    public Images getImgTuile() {
        return imgTuile;
    }

    public void inonderTuile(String path){
        if (hasExtension(path,"_Inonde.png")){
            imgTuile = new Images(path);
        }
        else {
           throw new IllegalArgumentException("ce n'est pas le bon fichier");
        }
    }
}
