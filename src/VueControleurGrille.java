import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.util.HashMap;
import java.util.Observer;
import java.util.Observable;
public class VueControleurGrille extends JFrame implements Observer {
    private static final int PIXEL_PER_SQUARE = 120;
    // tableau de cases : i, j -> case
    private VueCase[][] tabCV;
    // hashmap : case -> i, j
    private HashMap<VueCase, Point> hashmap; // voir (*)
    // currentComponent : par défaut, mouseReleased est exécutée pour le composant (JLabel) sur lequel elle a été enclenchée (mousePressed) même si celui-ci a changé
    // Afin d'accéder au composant sur lequel le bouton de souris est relaché, on le conserve avec la variable currentComponent à
    // chaque entrée dans un composant - voir (**)

    private JButton home;
    private JButton exit;
    private JButton restart_level;

    private VueMenu vueMenu;



    public void update(Observable o, Object arg) {
        repaint();

    }
    public VueControleurGrille(Jeu _j,int size) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE);
        setLocationRelativeTo(null);
        initVueControleurGrille(_j,size);

        System.out.println("["+tabCV[0][0].x+" ,"+tabCV[0][0].y+"]");


    }


    public void initVueControleurGrille(Jeu _j,int size)
    {


        tabCV = new VueCase[size][size];
        hashmap = new HashMap<VueCase, Point>();
        _j.init(tabCV,size);



       



        JPanel stack = new JPanel(new CardLayout(30,30));
        //stack.setSize(new Dimension(300,300));


        JPanel grille = new JPanel(new GridLayout(size, size));

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {

                grille.add(tabCV[i][j]);

            }
        }


        home= new JButton();
        home.setIcon(new ImageIcon("out/icons/maison.png"));
        home.setToolTipText("Menu");
        home.addActionListener(
                (e ->
                {

                    int choix =JOptionPane.showConfirmDialog(this,"Return to Menu ?","Message",JOptionPane.YES_NO_OPTION);
                    if(choix==JOptionPane.YES_OPTION)
                    {
                        CardLayout cl = (CardLayout) stack.getLayout();
                        cl.show(stack, "home_menu");
                    }
                })
        );

        exit=new JButton();
        exit.setIcon(new ImageIcon("out//icons/exit.png"));
        exit.setToolTipText("Exit");
        exit.addActionListener(
                (e ->
                {
                    int choix =JOptionPane.showConfirmDialog(this,"Do you want to Exit","Message",JOptionPane.YES_NO_OPTION);
                    if(choix==JOptionPane.YES_OPTION)
                    {
                        dispose();
                        Thread.currentThread().interrupt();
                    }


                })
        );

        restart_level=new JButton();
        restart_level.setIcon(new ImageIcon("out//icons/redo.png"));
        restart_level.setToolTipText("Restart Level");
        restart_level.addActionListener(
                (e ->
                {
                    int choix =JOptionPane.showConfirmDialog(this,"Restart Level ?","Message",JOptionPane.YES_NO_OPTION);
                    if(choix==JOptionPane.YES_OPTION)
                    {
                        _j.clear(tabCV,size);
                    }
                })
        );


        vueMenu=new VueMenu();
        vueMenu.getN_partie().addActionListener(
                (e) ->
                {
                    CardLayout cl = (CardLayout) stack.getLayout();
                    cl.show(stack, "grille");
                }
        );

        vueMenu.getQ_partie().addActionListener((e)->
        {
            int choix =JOptionPane.showConfirmDialog(this,"Do you want to Exit","Message",JOptionPane.YES_NO_OPTION);
            if(choix==JOptionPane.YES_OPTION)
            {
                dispose();
            }

        });



        JPanel jeu = new JPanel(new BorderLayout());


        JPanel south = new JPanel(new FlowLayout());
        south.add(exit,FlowLayout.LEFT);
        south.add(restart_level,FlowLayout.LEFT);
        south.add(home,FlowLayout.LEFT);


        jeu.add(south,BorderLayout.SOUTH);
        jeu.add(grille,BorderLayout.CENTER);


        stack.add(vueMenu,"home_menu");
        stack.add(jeu,"grille");


       // JPanel contentPane = new JPanel(new BorderLayout());
        Image img = Toolkit.getDefaultToolkit().getImage("out/icons/Dot_Pattern_Free_Vector.jpg");
        this.setContentPane(new JPanel(new BorderLayout()) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
                add(stack,BorderLayout.CENTER);
            }
        });

    }







    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Jeu j1 = new Jeu();
        VueControleurGrille vue = new VueControleurGrille(j1,6);
        vue.setVisible(true);
        j1.addObserver(vue);
/*
        Thread t1=new Thread(j1);
        t1.start();*/



    }

}
