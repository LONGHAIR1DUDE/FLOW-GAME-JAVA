import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VueMenu extends JPanel {

    private JButton n_partie = new JButton();

    private JButton q_partie = new JButton("Exit Game");


    public JButton getQ_partie() {
        return q_partie;
    }

    public JButton getN_partie() {
        return n_partie;
    }

    public VueMenu()
    {
        //this.setSize(100,100);
        n_partie.setFont(new Font("Dialog", Font.BOLD, 35));
        n_partie.setLabel("New Game");


        q_partie.setFont(new Font("Dialog", Font.BOLD, 35));
        q_partie.setLabel("Exit Game");

        this.setLayout(new GridLayout(2,1));

        this.n_partie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                n_partie.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                n_partie.setBackground(null);
            }
        });

        this.q_partie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                q_partie.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                q_partie.setBackground(null);
            }
        });





        this.add(n_partie);
        this.add(q_partie);

        this.setVisible(true);


    }
}
