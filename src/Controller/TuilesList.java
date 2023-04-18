package src.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.Model.Composant;
import src.Model.Connexion;
import src.Model.Tuile;
import src.Model.Type;

public class TuilesList implements Iterable<Tuile> {
    private ArrayList<ArrayList<Tuile>> tuiles;
    private ArrayList<ArrayList<Integer>> lampes;
    private ArrayList<ArrayList<Integer>> bornes;
    private ArrayList<ArrayList<Integer>> sources;

    private Type type; // 4/6

    public TuilesList() {
        tuiles = new ArrayList<>();
    }

    public void add(ArrayList<Tuile> parseString) {
        tuiles.add(parseString);
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setVariables() {
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
        return tuiles.size();
    }

    public int columnsNumber() {
        return tuiles.get(0).size();
    }

    public Composant getComposant(int i, int j) {
        return tuiles.get(i).get(j).getComposant();
    }

    public void turn(int i, int j) {
        tuiles.get(i).get(j).turn(type.getValue());
    }

    public boolean isPower(int i, int j) {
        return tuiles.get(i).get(j).isPower();
    }

    public void setPower(int i, int j) {
        tuiles.get(i).get(j).setPower(true);
    }

    public ArrayList<Connexion> getConnexions(int i, int j) {
        return tuiles.get(i).get(j).getConnexions();
    }

    public Type getType() {
        return type;
    }

    public int getTypeValue() {
        return type.getValue();
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

    public Tuile get(int i, int j) {
        return tuiles.get(i).get(j);
    }

    public void toggleComposant(int i, int j) {
        tuiles.get(j).get(i).nextComposant();
    }

    public void toggleConnexion(int i, int j, Connexion connexion) {
        tuiles.get(j).get(i).toggleConnexion(connexion);
    }

    public void addSide(int side) {
        ArrayList<Tuile> row;
        switch (side) {
            case 0:
                row = new ArrayList<>();
                for (int i = 0; i < tuiles.get(0).size(); i++) {
                    row.add(new Tuile(Composant.EMPTY, new ArrayList<Connexion>()));
                }
                tuiles.add(0, row);
                break;
            case 1:
                for (ArrayList<Tuile> line : tuiles) {
                    line.add(0, new Tuile(Composant.EMPTY, new ArrayList<Connexion>()));
                }
                break;
            case 2:
                row = new ArrayList<>();
                for (int i = 0; i < tuiles.get(0).size(); i++) {
                    row.add(new Tuile(Composant.EMPTY, new ArrayList<Connexion>()));
                }
                tuiles.add(row);
                break;
            case 3:
                for (ArrayList<Tuile> line : tuiles) {
                    line.add(new Tuile(Composant.EMPTY, new ArrayList<Connexion>()));
                }
                break;
            default:
                break;
        }
    }

    public void deleteSide(int side) {
        ArrayList<Tuile> row;
        switch (side) {
            case 0:
                row = new ArrayList<>();
                for (int i = 0; i < tuiles.get(0).size(); i++) {
                    row.add(new Tuile(Composant.EMPTY, new ArrayList<Connexion>()));
                }
                tuiles.remove(0);
                break;
            case 1:
                for (ArrayList<Tuile> line : tuiles) {
                    line.remove(0);
                }
                break;
            case 2:
                tuiles.remove(tuiles.size() - 1);
                break;
            case 3:
                for (ArrayList<Tuile> line : tuiles) {
                    line.remove(line.size() - 1);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        String res = "";
        res += rowsNumber() + " " + columnsNumber() + " " + getType() + "\n";
        for (ArrayList<Tuile> tuilesRow : tuiles) {
            for (Tuile tuile : tuilesRow) {
                res += tuile;
            }
            res += "\n";
        }
        return res;
    }

    public void switchType() {
        switch (type) {
            case HEX:
                type = Type.SQR;
                break;
            case SQR:
                type = Type.HEX;
            default:
                break;
        }
    }
}
