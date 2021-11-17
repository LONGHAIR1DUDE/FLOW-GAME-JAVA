import java.util.Random;

public class  ModelCase{
    private CaseType type;
    private static Random rnd = new Random();

    public CaseType getType() {
        return type;
    }

    public void rndType() {


        type = CaseType.values()[rnd.nextInt(CaseType.values().length)];

        }
}