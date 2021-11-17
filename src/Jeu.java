import java.util.Observable;

public class Jeu extends Observable implements Runnable {

    ModelCase m;


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
