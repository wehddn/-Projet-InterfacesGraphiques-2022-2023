package src;

import java.util.ArrayList;

public class Plateau {

    private ArrayList<ArrayList<Tuile>> tuiles;

    public Plateau() {
        tuiles = new ArrayList<>();
        loadTuiles();
    }

    private void loadTuiles() {
        tuiles = Convertion.parseFile(1);
    }

    @Override
    public String toString() {
        String result = "";
        for (ArrayList<Tuile> arrayList : tuiles) {
            for (Tuile tuile : arrayList) {
                result += tuile + "; ";
            }
            result += "\n";
        }
        return result;
    }
        
}
