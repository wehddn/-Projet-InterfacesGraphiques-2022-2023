package src;

import java.util.ArrayList;

public class Plateau {

    private ArrayList<ArrayList<Tuile>> tuiles;

    public Plateau() {
        tuiles = new ArrayList<>();
        loadTuiles();
    }

    private void loadTuiles() {
        //TODO modifier la sélection de niveau
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
