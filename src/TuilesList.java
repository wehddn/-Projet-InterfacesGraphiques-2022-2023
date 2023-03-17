package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TuilesList implements Iterable<Tuile> {
    private ArrayList<ArrayList<Tuile>> tuiles;
    private ArrayList<ArrayList<Integer>> lampes;
    private ArrayList<ArrayList<Integer>> bornes;
    private ArrayList<ArrayList<Integer>> sources;
    private int rowsNumber;
    private int columnsNumber;

    public TuilesList(ArrayList<ArrayList<Tuile>> tuiles) {
        this.tuiles = tuiles;
        rowsNumber = tuiles.size();
        columnsNumber = tuiles.get(0).size();
        lampes = new ArrayList<>();
        bornes = new ArrayList<>();
        sources = new ArrayList<>();
    }

    @Override
    public Iterator<Tuile> iterator() {
        return new Iterator<Tuile>() {
            private int outerIndex = 0;
            private int innerIndex = 0;

            @Override
            public boolean hasNext() {
                while (outerIndex < tuiles.size()) {
                    if (innerIndex < tuiles.get(outerIndex).size()) {
                        return true;
                    } else {
                        outerIndex++;
                        innerIndex = 0;
                    }
                }
                return false;
            }

            @Override
            public Tuile next() {
                Tuile tuile = tuiles.get(outerIndex).get(innerIndex);
                innerIndex++;
                return tuile;
            }
        };
    }

    public int rowsNumber() {
        return rowsNumber;
    }

    public int columnsNumber() {
        return columnsNumber;
    }

    public Composant getComposant(int i, int j) {
        return tuiles.get(i).get(j).getComposant();
    }

    public void turn(int i, int j) {
        tuiles.get(i).get(j).turn();
    }

    public boolean isPower(int i, int j) {
        return tuiles.get(i).get(j).isPower();
    }

    public void setPower(int i, int j) {
        tuiles.get(i).get(j).setPower(true);
    }

    public ArrayList<Integer> getConnexions(int i, int j) {
        return tuiles.get(i).get(j).getConnexions();
    }

    public int getType() {
        return tuiles.get(0).get(0).getType();
    }

    public ArrayList<ArrayList<Tuile>> getTuiles() {
        return tuiles;
    }

    public void addSource(int i, int j) {
        sources.add(new ArrayList<>(List.of(i, j)));
    }

    public void addLampe(int i, int j) {
        lampes.add(new ArrayList<>(List.of(i, j)));
    }

    public void addBorne(int i, int j) {
        bornes.add(new ArrayList<>(List.of(i, j)));
    }

    public ArrayList<ArrayList<Integer>> getSources() {
        return sources;
    }

    public ArrayList<ArrayList<Integer>> getBornes() {
        return bornes;
    }

    public ArrayList<ArrayList<Integer>> getLampes() {
        return lampes;
    }
}
