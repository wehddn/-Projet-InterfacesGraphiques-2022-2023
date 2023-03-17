package src;

import java.util.ArrayList;

//TO DO: enums

enum Type {
    SQR,
    HEX
}

enum Composant {
    SOURCE,
    LAMPE,
    WIFI,
    EMPTY
}

public class Tuile {
    private Type enum_type;
    private int type; // 4/6
    private Composant composant; // S/L/W/.
    private ArrayList<Integer> connexions; // static?
    private boolean power;
    private boolean visited;

    public Tuile(Composant composant, ArrayList<Integer> connexions) {
        this.composant = composant;
        this.connexions = connexions;
    }

    public Composant getComposant() {
        return composant;
    }

    public void setComposant(Composant composant) {
        this.composant = composant;
    }

    public ArrayList<Integer> getConnexions() {
        return connexions;
    }

    public void setConnexions(ArrayList<Integer> connexions) {
        this.connexions = connexions;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        String result = String.valueOf(composant) + " ";
        if (connexions.size() != 0) {
            result += "- ";
            for (Integer connexion : connexions) {
                result += connexion.toString() + " ";
            }
        }
        if (power)
            result += "ON";
        else
            result += "OF";
        return result;
    }

    public void turnNtimes(int n, int type) {
        for (int i = 0; i < connexions.size(); i++) {
            connexions.set(i, (connexions.get(i) + n) % type);
        }
    }

    public void turn(int type) {
        for (int i = 0; i < connexions.size(); i++) {
            connexions.set(i, (connexions.get(i) + 1) % type);
        }
    }
}
