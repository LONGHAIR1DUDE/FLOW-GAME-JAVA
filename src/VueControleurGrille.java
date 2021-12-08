import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Observer;
import java.util.Observable;
public class VueControleurGrille extends JFrame implements Observer {
    private static final int PIXEL_PER_SQUARE = 60;
    // tableau de cases : i, j -> case
    private VueCase[][] tabCV;
    // hashmap : case -> i, j
    private HashMap<VueCase, Point> hashmap; // voir (*)
    // currentComponent : par défaut, mouseReleased est exécutée pour le composant (JLabel) sur lequel elle a été enclenchée (mousePressed) même si celui-ci a changé
    // Afin d'accéder au composant sur lequel le bouton de souris est relaché, on le conserve avec la variable currentComponent à
    // chaque entrée dans un composant - voir (**)
    public void update(Observable o, Object arg) {
                repaint();

    }
    public VueControleurGrille(Jeu _j,int size) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE);
        tabCV = new VueCase[size][size];
        hashmap = new HashMap<VueCase, Point>();
        _j.init(tabCV,size);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(size, size));
         for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {

                contentPane.add(tabCV[i][j]);

            }
        }
        setContentPane(contentPane);

        System.out.println("["+tabCV[0][0].x+" ,"+tabCV[0][0].y+"]");
    }


    public static void main(String[] args) {

        Jeu j1 = new Jeu();
        VueControleurGrille vue = new VueControleurGrille(j1,6);
        vue.setVisible(true);
        j1.addObserver(vue);


       (new Thread(j1)).start();

    }

}
