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
    public boolean drop_initiated= false;
    private VueCase currentComponent;
    private Path red = new Path();
    private Path green = new Path();
    private Path yellow = new Path();
    private Path cyan = new Path();
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
                for (int i = 0; i < 4; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j = 0; j < 4; j++) {
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
    public void init(VueCase[][] tabCV, int size) {
        readLevel(tabCV,"Level1");
        tabPaths = new Path[4];
        initTabPath();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tabCV[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        //Point p = hashmap.get(e.getSource()); // (*) permet de récupérer les coordonnées d'une caseVue
                       if(((VueCase) e.getSource()).m.getType() != CaseType.empty) caseDepart = ((VueCase) e.getSource());
                        mouse_pressed = true;

                        switch(caseDepart.m.getType()) {
                            case S1 :   red.ajouter(0, caseDepart);
                            case S2 :   green.ajouter(0, caseDepart);
                            case S3 :   cyan.ajouter(0, caseDepart);
                            case S4 :   yellow.ajouter(0, caseDepart);
                        }

                        tabPaths[0].ajouter(0,caseDepart);




                    }
                    int k =1;
                    int h=1;
                    int q =1;
                    int z =1;
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        System.out.println("MOUSE IN --------------------------");
                        currentComponent = (VueCase) e.getSource();
                        if((currentComponent.m.getType() == CaseType.empty) && (mouse_pressed))
                        {
                            switch(caseDepart.m.getType()) {
                                case S1 :   red.ajouter(k++, currentComponent);break;
                                case S2 :   green.ajouter(h++, currentComponent);break;
                                case S3 :   cyan.ajouter(q++, currentComponent);break;
                                case S4 :   yellow.ajouter(z++, currentComponent);break;
                            }
                        }
                        System.out.println("mouseEntered : " + e.getSource());
                    }


                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        System.out.println("mouseReleased : " + currentComponent);

                        if(currentComponent.m.getType() != caseDepart.m.getType())
                        {
                            switch(caseDepart.m.getType()) {
                                case S1 :   red.viderChemin();break;
                                case S2 :   green.viderChemin();break;
                                case S3 :   cyan.viderChemin();break;
                                case S4 :   yellow.viderChemin();break;
                            }



                        }else
                        {
                            switch(caseDepart.m.getType()) {
                                case S1 : for(int g = 1;g < red.getTrajetSize()-1;g++) {

                                    tabCV[red.trajet.get(1).x ][red.trajet.get(g).y ].m.setType(CaseType.S1);
                                    System.out.println(tabCV[red.trajet.get(g).x ][red.trajet.get(g).y ].m.getType());
                                }
                                    red.removeDuplicate(); red.viderChemin();break;
                                case S2 : for(int g = 1;g < green.getTrajetSize()-1;g++) {

                                    tabCV[green.trajet.get(g).x ][green.trajet.get(g).y ].m.setType(CaseType.S2);
                                    System.out.println(tabCV[green.trajet.get(g).x ][green.trajet.get(g).y ].m.getType());
                                }
                                    green.removeDuplicate(); green.viderChemin();break;
                                case S3 : for(int g = 1;g < cyan.getTrajetSize()-1;g++) {

                                    tabCV[cyan.trajet.get(g).x ][cyan.trajet.get(g).y ].m.setType(CaseType.S3);
                                    System.out.println(tabCV[cyan.trajet.get(g).x ][cyan.trajet.get(g).y ].m.getType());
                                }
                                    cyan.removeDuplicate(); cyan.viderChemin();break;
                                case S4 : for(int g = 1;g < yellow.getTrajetSize()-1;g++) {

                                    tabCV[yellow.trajet.get(g).x ][yellow.trajet.get(g).y ].m.setType(CaseType.S4);
                                    System.out.println(tabCV[yellow.trajet.get(g).x ][yellow.trajet.get(g).y ].m.getType());
                                }
                                    yellow.removeDuplicate(); yellow.viderChemin();break;
                            }
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
