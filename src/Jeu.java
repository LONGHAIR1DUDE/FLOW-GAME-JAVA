import javax.swing.*;
import java.util.Observable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Jeu extends Observable implements Runnable {

    private VueCase caseDepart; //case de depart du chemin en cours de construction
    public boolean mouse_pressed = false; //booleen : l'etat du souris
    public boolean path_not_valid = false;//booleen : validité du chemin
    private VueCase currentComponent; //case courante : derniere case accedée par la souris
        //Tableau des chemins validés
    public Path tabPaths[];

    //initialisation du tableau des chemins en 4 pour les 4 symboles a lier
    private void initTabPath(){
        for(int i = 0 ; i < 4; i++){
            tabPaths[i] = new Path();
        }
    }
    //lecture des niveaux a partir d'un fichier txt
    //tabCV: tableau 2D de case qu'on va initialiser a partir des valeur des grilles contenu dans le fichier Level*.txt
    //level : chaine de caractere du niveaux a initialiser (Ex : String level = "Level1")
    public void readLevel(VueCase[][] tabCV,String level){
        FileReader inputFile;
        int currentId;
        CaseType currentType ;
        try {
            inputFile = new FileReader("out/levels/"+level+".txt") ;
            Scanner sc = new Scanner(new BufferedReader(inputFile));
            while (sc.hasNextLine()) {
                for (int i = 0; i < 6; i++) {

                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j = 0; j < 6; j++) {
                        currentId = Integer.parseInt(line[j]);
                        switch(currentId)
                        {   //affectation d'un type de Case/symbole pour chaque entier lu du grille
                            case 1 : currentType = CaseType.S1;break;
                            case 2 : currentType = CaseType.S2;break;
                            case 3 : currentType = CaseType.S3;break;
                            case 4 : currentType = CaseType.S4;break;
                            case 0 : currentType = CaseType.empty;break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + currentId);
                        }
                            //initialisation de tabCV[x][y] avec le type correspondant
                        tabCV[i][j] = new VueCase(i, j,currentType) ;

                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //Creation des chemins (ajout des cordonnees de case de depart au debut du trajet : (liste de case) contenu dans un chemin
    //TabPath : tableau 1D des chemins de longueur 4 (pour 4 symboles)
    //HeadCase : case du depart du chemin en cours de construction
    public void createPath(Path[] tabPath,VueCase HeadCase)
    { //on rajoute une case de depart au debut du trajet si le trajet (liste de case formant le chemin) est vide
        //et on stocke le type(symbole) de chaque chemin dans le variable type du chemin
        if (tabPath[0].trajet.isEmpty()) {tabPath[0].ajouter(0, HeadCase); tabPath[0].type = HeadCase.m.getType();}
        else if(tabPath[1].trajet.isEmpty()) {tabPath[1].ajouter(0, HeadCase); tabPath[1].type = HeadCase.m.getType();}
        else if(tabPath[2].trajet.isEmpty()) {tabPath[2].ajouter(0, HeadCase); tabPath[2].type = HeadCase.m.getType();}
        else if(tabPath[3].trajet.isEmpty()) {tabPath[3].ajouter(0, HeadCase); tabPath[3].type = HeadCase.m.getType();}
    }
    //Ajout d'une case traversé par le joueur a la fin du liste des cases qui forment le chemin / trajet. et retourne l'indice du case ajoutée.
    //TabPath : tableau 1D des chemins de longueur 4 (pour 4 symboles)
    //HeadCase : case du depart du chemin en cours de construction
    //CurrentComponent : case qu'on veut rajouter au chemin
    public int addPath(Path[] tabPath,VueCase HeadCase,VueCase CurrentComponent)
    { int indice= -1;
        //on rajoute CurrentComponent la case courante a la fin du trajet s'il part de la case du depart du chemin en cours de construction
        if (tabPath[0].contientCase(HeadCase)) {
            tabPath[0].ajouter(tabPath[0].getTrajetSize(), CurrentComponent); indice = 0;}
        else if (tabPath[1].contientCase(HeadCase)) {tabPath[1].ajouter(tabPath[1].getTrajetSize(), CurrentComponent); indice = 1;}
        else if (tabPath[2].contientCase(HeadCase)) {tabPath[2].ajouter(tabPath[2].getTrajetSize(), CurrentComponent); indice = 2;}
        else if (tabPath[3].contientCase(HeadCase)) {tabPath[3].ajouter(tabPath[3].getTrajetSize(), CurrentComponent); indice = 3;}
        return indice;
    }
    //Verifie si le chemin est valide en verifiant le type de la case du depart et celle d'arrivé
    //Si le chemin est valide on change les types des cases dans le chemin construit a celle du case de depart
    //TabPath : tableau 1D des chemins de longueur 4 (pour 4 symboles)
    //HeadCase : case du depart du chemin en cours de construction
    //LastComponent : La case d'arrivé
    public void checkpath(Path[] tabPath,VueCase HeadCase,VueCase LastComponent,VueCase[][] tabCV)
    {
        if (tabPath[0].contientCase(HeadCase) && tabPath[0].contientCase(LastComponent)) {
            if(HeadCase.m.getType() == LastComponent.m.getType())
            {
                tabPath[0].removeDuplicate();
                for(int g = 1;g < tabPath[0].getTrajetSize()-1;g++) {
                     tabCV[tabPath[0].trajet.get(g).x ][tabPath[0].trajet.get(g).y ].m.setType(tabPath[0].type);
            }//on note que le chemin est fait .
                tabPath[0].done = true;
        }}
            else if (tabPath[1].contientCase(HeadCase) && tabPath[1].contientCase(LastComponent)) {
                if (HeadCase.m.getType() == LastComponent.m.getType()) {
                    tabPath[1].removeDuplicate();
                    for (int g = 1; g < tabPath[1].getTrajetSize() - 1; g++) {
                        tabCV[tabPath[1].trajet.get(g).x][tabPath[1].trajet.get(g).y].m.setType(tabPath[1].type);
                    }tabPath[1].done = true;
                } }else if (tabPath[2].contientCase(HeadCase) && tabPath[2].contientCase(LastComponent)) {
                    if (HeadCase.m.getType() == LastComponent.m.getType()) {
                        tabPath[2].removeDuplicate();
                        for (int g = 1; g < tabPath[2].getTrajetSize() - 1; g++) {
                            tabCV[tabPath[2].trajet.get(g).x][tabPath[2].trajet.get(g).y].m.setType(tabPath[2].type);
                        }tabPath[2].done = true;
                    } }else if (tabPath[3].contientCase(HeadCase) && tabPath[3].contientCase(LastComponent)) {
                        if (HeadCase.m.getType() == LastComponent.m.getType()) {
                            tabPath[3].removeDuplicate();
                            for (int g = 1; g < tabPath[3].getTrajetSize() - 1; g++) {
                                tabCV[tabPath[3].trajet.get(g).x][tabPath[3].trajet.get(g).y].m.setType(tabPath[3].type);
                            }tabPath[3].done = true;
                        }
                    }
                }
 //Detruit un trajet dans tabPath defini par la case de depart
public void clearPath(Path[] tabPath,VueCase HeadCase)
{
    if (tabPath[0].contientCase(HeadCase)) tabPath[0].viderChemin();
    else if (tabPath[1].contientCase(HeadCase)) tabPath[1].viderChemin();
    else if (tabPath[2].contientCase(HeadCase)) tabPath[2].viderChemin();
    else if (tabPath[3].contientCase(HeadCase)) tabPath[3].viderChemin();
}
//Verifie si la partie est gagné en verifiant que tout les trajets ont été construit
public boolean game_won(Path[] tabPath)
{
    if(!tabPath[0].trajet.isEmpty() && !tabPath[1].trajet.isEmpty() && !tabPath[2].trajet.isEmpty() && !tabPath[3].trajet.isEmpty())
        return true;

    return false;

}

//Initialisation du tableau de case /grille
    public void init(VueCase[][] tabCV, int size) {
        readLevel(tabCV,"Level3");
        tabPaths = new Path[4];
        initTabPath();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tabCV[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                       //Si l'utilisateur appuie sur une case , on enregistre ses coordonnées comme la case de depart
                       if(((VueCase) e.getSource()).m.getType() != CaseType.empty) {
                           caseDepart = ((VueCase) e.getSource());
                           mouse_pressed = true;
                           //creation d'un chemin si la case de depart n'est pas vide
                           createPath(tabPaths, caseDepart);
                       }



                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        if(mouse_pressed) {
                        currentComponent = (VueCase) e.getSource();
                        if((currentComponent.m.getType() == CaseType.empty) )
                        {
                            //on rajoute les coordonnées de la case dans le chemin si la case est vide
                           addPath(tabPaths,caseDepart,currentComponent);

                        }else if((currentComponent.m.getType() != CaseType.empty) && (currentComponent.m.getType() != caseDepart.m.getType()) )
                        {  //si une case n'est pas vide  on note que le chemin n'est pas valide
                            path_not_valid = true;
                        }
                        }
                    }


                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent

                        //On rajoute la dernier case au chemin
                           int k= addPath(tabPaths,caseDepart,currentComponent);
                        if(currentComponent.m.getType() != caseDepart.m.getType())
                        {
                            //On detruit le chemin qui commence dans caseDepart si la case de depart != la case d'arrivée
                                clearPath(tabPaths,caseDepart);

                        }else
                        {
                            //sinon si le chemin n'est pas valide ou deja fait on le detruit
                            if(path_not_valid || tabPaths[k].done ) {
                                tabPaths[k].viderChemin(); path_not_valid = false;tabPaths[k].done = false;}
                            else {
                                //Sinon on applique le chemin sur le tableau de case CV
                                checkpath(tabPaths,caseDepart,currentComponent,tabCV);}

                        }
                        //On verifie si la partie est terminé a chaque fois que l'utilisateur relacher la souris
                        setChanged();
                        notifyObservers();
                        if (game_won(tabPaths))
                        {
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            int choix =JOptionPane.showConfirmDialog(null,"Level Clear, Continue ? ","Message",JOptionPane.YES_NO_OPTION);
                            if(choix==JOptionPane.YES_OPTION)
                            {
                                System.out.println("------------------Parie terminé_---------------------");

                            }
                        }
                        mouse_pressed = false;
                    }
                });

            }
        }

    }
    public void clear(VueCase[][] tabCV, int size)
    {
        for(int i=0;i<4;i++)
        {
            for(int j=1;j<tabPaths[i].getTrajetSize()-1;j++)
            {
                tabCV[tabPaths[i].trajet.get(j).x][tabPaths[i].trajet.get(j).y].m.setType(CaseType.empty);
            }
            tabPaths[i].viderChemin();

        }

        setChanged();
        notifyObservers();

    }

    public void run() {
        while(true) {



            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();//TODO
            }

        }
    }

}
