package src;

import java.util.ArrayList;
import java.util.Random;

public class Plateau {
    private ArrayList<ArrayList<Tuile>> tuiles;

    public Plateau() {
        generateTuiles();
        randomizeTuiles();
    }

    public void randomizeTuiles() {
        Random r = new Random();
        int n;
        for (ArrayList<Tuile> t : tuiles) {
            for (Tuile tuile : t) {
                n = r.nextInt(tuile.getType() - 1) + 1;
                tuile.turnTuile(n);
            }
        }
    }

    public void generateTuiles() {
        tuiles = new ArrayList<ArrayList<Tuile>>();
        // lecture du txt
        tuiles = Convertion.parseFile(1);
    }

    @Override
    public String toString() {
        String result = "Plateau type : " + tuiles.get(0).get(0).getType() + "\n";
        for (ArrayList<Tuile> arrayList : tuiles) {
            for (Tuile tuile : arrayList) {
                result += tuile + "; ";
            }
            result += "\n";
        }
        return result;
    }
}
