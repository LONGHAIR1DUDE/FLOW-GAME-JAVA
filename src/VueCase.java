import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

// TODO : redéfinir la fonction hashValue() et equals(Object) si vous souhaitez utiliser la hashmap de VueControleurGrille avec VueCase en clef

public class VueCase extends JPanel {
    private int x, y;

    private static Random rnd = new Random();
    public ModelCase m = new ModelCase();
    public VueCase(int _x, int _y,CaseType _type) {
        x = _x;
        y = _y;
        m.setType(_type);
    }






    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());

       // g.drawRoundRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2, 5, 5);

       // Rectangle2D deltaText =  g.getFont().getStringBounds("0", g.getFontMetrics().getFontRenderContext()); // "0" utilisé pour gabarit


        switch(m.getType()) {
            case S1 :
                g.setColor(Color.RED);
                g.fillOval(getWidth()/5 +2, getHeight()/5 +2,getWidth()/2, getHeight()/2);
                break;
            case S2 :
                g.setColor(Color.GREEN);
                g.fillOval(getWidth()/5 +2, getHeight()/5 +2,getWidth()/2, getHeight()/2);
                break;
            case S3 :
                g.setColor(Color.CYAN);
                g.fillOval(getWidth()/5 +2, getHeight()/5 +2,getWidth()/2, getHeight()/2);
                break;
            case S4 :
                g.setColor(Color.YELLOW);
                g.fillOval(getWidth()/5 +2, getHeight()/5 +2,getWidth()/2, getHeight()/2);
                break;


        }
    }
    public String toString() {
        return x + ", " + y;

    }

}