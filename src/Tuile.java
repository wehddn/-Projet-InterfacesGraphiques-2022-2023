package src;

import java.util.ArrayList;

public class Tuile {
    private Composant composant;
    private ArrayList<Connexion> connexions;
    private boolean power;
    private boolean visited;

    public Tuile(Composant composant, ArrayList<Connexion> connexions) {
        this.composant = composant;
        this.connexions = connexions;
    }

    public Composant getComposant() {
        return composant;
    }

    public void setComposant(Composant composant) {
        this.composant = composant;
    }

    public ArrayList<Connexion> getConnexions() {
        return connexions;
    }

    public void setConnexions(ArrayList<Connexion> connexions) {
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
            for (Connexion connexion : connexions) {
                result += connexion.getValue() + " ";
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
            connexions.set(i, Connexion.intToEnum((connexions.get(i).getValue() + n) % type));
        }
    }

    public void turn(int type) {
        for (int i = 0; i < connexions.size(); i++) {
            connexions.set(i, Connexion.intToEnum((connexions.get(i).getValue() + 1) % type));
        }
    }

    public ArrayList<Integer> getIntConnexions() {
        ArrayList<Integer> intConnexions = new ArrayList<>();
        for (Connexion connexion : connexions) {
            intConnexions.add(connexion.getValue());
        }
        return intConnexions;
    }
}
