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
    private VueCase caseDepart;
    public boolean mouse_pressed = false;

    private VueCase currentComponent;

    public Path tabPaths[];
    private void initTabPath(){
        for(int i = 0 ; i < 4; i++){
            tabPaths[i] = new Path();
        }
    }
    private void readLevel(VueCase[][] tabCV,String level){
        FileReader inputFile;
        int currentId;
        CaseType currentType ;
        try {
            inputFile = new FileReader("/home/long-hair-dude/LIF13/flow-game-lif13/src/"+level+".txt") ;
            Scanner sc = new Scanner(new BufferedReader(inputFile));
            while (sc.hasNextLine()) {
                for (int i = 0; i < 6; i++) {
                    System.out.println("i="+i);
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j = 0; j < 6; j++) {
                        currentId = Integer.parseInt(line[j]);
                        switch(currentId)
                        {
                            case 1 : currentType = CaseType.S1;break;
                            case 2 : currentType = CaseType.S2;break;
                            case 3 : currentType = CaseType.S3;break;
                            case 4 : currentType = CaseType.S4;break;
                            case 0 : currentType = CaseType.empty;break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + currentId);
                        }
                        //System.out.println(" ",i," ",j);
                        tabCV[i][j] = new VueCase(i, j,currentType) ;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void createPath(Path[] tabPath,VueCase HeadCase)
    {
        if (tabPath[0].trajet.isEmpty()) {tabPath[0].ajouter(0, HeadCase); tabPath[0].type = HeadCase.m.getType();System.out.println("case added to head tabPath[0] "+tabPath[0].trajet.get(0));}
        else if(tabPath[1].trajet.isEmpty()) {tabPath[1].ajouter(0, HeadCase); tabPath[1].type = HeadCase.m.getType();}
        else if(tabPath[2].trajet.isEmpty()) {tabPath[2].ajouter(0, HeadCase); tabPath[2].type = HeadCase.m.getType();}
        else if(tabPath[3].trajet.isEmpty()) {tabPath[3].ajouter(0, HeadCase); tabPath[3].type = HeadCase.m.getType();}
    }
    public void addPath(Path[] tabPath,VueCase HeadCase,VueCase CurrentComponent)
    {
        System.out.println("entered addPath ----------");
        int size_path0 = tabPath[0].getTrajetSize();
        System.out.println(tabPath[0].contientCase(HeadCase)+">>>>>>>>>>>>>");
        if (tabPath[0].contientCase(HeadCase)) {tabPath[0].ajouter(size_path0, CurrentComponent); System.out.println("case added to tabPath[0] "+CurrentComponent);}
        else if (tabPath[1].contientCase(HeadCase)) tabPath[1].ajouter(tabPath[1].getTrajetSize(), CurrentComponent);
        else if (tabPath[2].contientCase(HeadCase)) tabPath[2].ajouter(tabPath[2].getTrajetSize(), CurrentComponent);
        else if (tabPath[3].contientCase(HeadCase)) tabPath[3].ajouter(tabPath[3].getTrajetSize(), CurrentComponent);
    }
    public void checkpath(Path[] tabPath,VueCase HeadCase,VueCase LastComponent,VueCase[][] tabCV)
    {
        if (tabPath[0].contientCase(HeadCase) && tabPath[0].contientCase(LastComponent)) {
            if(HeadCase.m.getType() == LastComponent.m.getType())
            {
                tabPath[0].removeDuplicate();
                for(int g = 1;g < tabPath[0].getTrajetSize()-1;g++) {
                     tabCV[tabPath[0].trajet.get(g).x ][tabPath[0].trajet.get(g).y ].m.setType(tabPath[0].type);
            }
        }}
            else if (tabPath[1].contientCase(HeadCase) && tabPath[1].contientCase(LastComponent)) {
                if (HeadCase.m.getType() == LastComponent.m.getType()) {
                    tabPath[1].removeDuplicate();
                    for (int g = 1; g < tabPath[1].getTrajetSize() - 1; g++) {
                        tabCV[tabPath[1].trajet.get(g).x][tabPath[1].trajet.get(g).y].m.setType(tabPath[1].type);
                    }
                } }else if (tabPath[2].contientCase(HeadCase) && tabPath[2].contientCase(LastComponent)) {
                    if (HeadCase.m.getType() == LastComponent.m.getType()) {
                        tabPath[2].removeDuplicate();
                        for (int g = 1; g < tabPath[2].getTrajetSize() - 1; g++) {
                            tabCV[tabPath[2].trajet.get(g).x][tabPath[2].trajet.get(g).y].m.setType(tabPath[2].type);
                        }
                    } }else if (tabPath[3].contientCase(HeadCase) && tabPath[3].contientCase(LastComponent)) {
                        if (HeadCase.m.getType() == LastComponent.m.getType()) {
                            tabPath[3].removeDuplicate();
                            for (int g = 1; g < tabPath[3].getTrajetSize() - 1; g++) {
                                tabCV[tabPath[3].trajet.get(g).x][tabPath[3].trajet.get(g).y].m.setType(tabPath[3].type);
                            }
                        }
                    }
                }
public void clearPath(Path[] tabPath,VueCase HeadCase)
{
    if (tabPath[0].contientCase(HeadCase)) tabPath[0].viderChemin();
    else if (tabPath[1].contientCase(HeadCase)) tabPath[1].viderChemin();
    else if (tabPath[2].contientCase(HeadCase)) tabPath[2].viderChemin();
    else if (tabPath[3].contientCase(HeadCase)) tabPath[3].viderChemin();
}
    public void init(VueCase[][] tabCV, int size) {
        readLevel(tabCV,"Level1");
        tabPaths = new Path[4];
        initTabPath();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tabCV[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        //Point p = hashmap.get(e.getSource()); // (*) permet de récupérer les coordonnées d'une caseVue
                       if(((VueCase) e.getSource()).m.getType() != CaseType.empty) caseDepart = ((VueCase) e.getSource());
                        mouse_pressed = true;



                        createPath(tabPaths,caseDepart);

                        System.out.println("pressed type :"+((VueCase) e.getSource()).m.getType() );


                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        System.out.println("MOUSE IN --------------------------");
                        currentComponent = (VueCase) e.getSource();
                        if((currentComponent.m.getType() == CaseType.empty) )
                        {

                            addPath(tabPaths,caseDepart,currentComponent);
                        }
                        System.out.println("mouseEntered : " + e.getSource());
                    }


                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        System.out.println("mouseReleased : " + currentComponent);
                            addPath(tabPaths,caseDepart,currentComponent);
                        if(currentComponent.m.getType() != caseDepart.m.getType())
                        {


                                clearPath(tabPaths,caseDepart);

                        }else
                        {

                            checkpath(tabPaths,caseDepart,currentComponent,tabCV);

                        }
                        mouse_pressed = false;
                        setChanged();
                        notifyObservers();
                    }
                });

            }
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
            }

        }
    }

}
