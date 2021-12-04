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
    private VueCase prevCase;
    private JComponent currentComponent;
    public void init(VueCase[][] tabCV, int size) {
            FileReader inputFile;
            int currentId;
            CaseType currentType ;
            try {
                inputFile = new FileReader("/home/long-hair-dude/LIF13/flow-game-lif13/src/Level1.txt") ;
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

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tabCV[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        //Point p = hashmap.get(e.getSource()); // (*) permet de récupérer les coordonnées d'une caseVue
                        caseDepart = ((VueCase) e.getSource());

                        System.out.println("mousePressed : " + caseDepart);


                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        currentComponent = (JComponent) e.getSource();

                        System.out.println("mouseEntered : " + e.getSource());
                    }


                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        System.out.println("mouseReleased : " + currentComponent);
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
