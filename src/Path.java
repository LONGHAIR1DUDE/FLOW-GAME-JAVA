
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
public class Path {
    public ArrayList<VueCase> trajet;
    public CaseType type;
    public Path(){
        trajet = new ArrayList<VueCase>() {};
    }

    public void removeDuplicate(){
        java.util.List<VueCase> list = trajet.stream().distinct().collect(Collectors.toList());
        trajet = (ArrayList<VueCase>) list;
    }

    public int getTrajetSize(){
        return trajet.size();
    }
    public void print_T()
    { System.out.println(" [ ");
        for(int d =0;d<getTrajetSize();d++) {
            System.out.println(trajet.get(d).x + "," + trajet.get(d).y + " ; ");
        }
        System.out.println("]");
    }
    public VueCase getCheminFin(){
        return trajet.get(trajet.size()-1);
    }

    public void ajouter(int indice, VueCase _case){

            trajet.add(indice, _case);

    }

    public boolean contientCase(VueCase case1){
        for(int i = 0; i < trajet.size(); i++){
            if(trajet.get(i).equals(case1)) return true;
        }
        return false;
    }



    public void viderChemin(){
        trajet.clear();
    }
}
