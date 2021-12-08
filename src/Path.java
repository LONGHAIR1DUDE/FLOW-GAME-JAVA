
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
public class Path {
    public ArrayList<VueCase> trajet;//Liste des cases qui forment le chemin
    public CaseType type;//le type du chemin (S1,S2,S3,S4,0)
    public boolean done = false;//Booleen : etat du chemin (fait ou pas encore)
    public Path(){
        trajet = new ArrayList<VueCase>() {};
    }//Constructeur d'un chemin
    //enleve les cases en doubles
    public void removeDuplicate(){
        java.util.List<VueCase> list = trajet.stream().distinct().collect(Collectors.toList());
        trajet = (ArrayList<VueCase>) list;
    }
        //retour le nombre de case dans le trajet
    public int getTrajetSize(){
        return trajet.size();
    }
    //Debug Step : imprimer un chemin sous forme d'une liste
    public void print_T()
    { System.out.println(" [ ");
        for(int d =0;d<getTrajetSize();d++) {
            System.out.println(trajet.get(d).x + "," + trajet.get(d).y + " ; ");
        }
        System.out.println("]");
    }
    //retourne la derniere case dans un chemin
    public VueCase getCheminFin(){
        return trajet.get(trajet.size()-1);
    }
        //ajouter une case dans le chemin a l'indice : indice
    public void ajouter(int indice, VueCase _case){

            trajet.add(indice, _case);

    }
    //retourne true ou false si le case existe dans le chemin
    public boolean contientCase(VueCase case1){
        for(int i = 0; i < trajet.size(); i++){
            if(trajet.get(i).equals(case1)) return true;
        }
        return false;
    }
    public VueCase pred(VueCase case1){
        int indice=0;
        removeDuplicate();
        for(int i = 0; i < trajet.size(); i++){
            if(trajet.get(i).equals(case1)) indice =i;
        }
        return trajet.get(indice-1);
    }

    //vider un chemin
    public void viderChemin(){
        trajet.clear();
    }
}
