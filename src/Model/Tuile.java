package src.Model;

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
        String result = composant + " ";
        if (connexions.size() != 0) {
            for (Connexion connexion : connexions) {
                result += connexion.getValue() + " ";
            }
        }
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

    public void nextComposant() {
        composant = composant.next();
    }

    public boolean toggleConnexion(Connexion connexion) {
        boolean removed = connexions.remove(connexion);
        if(!removed)
            connexions.add(connexion);
        return removed;
    }

    public void toggleConnexion(Connexion connexion, boolean on) {
        if (on) {
            if (!connexions.contains(connexion))
                connexions.add(connexion);
        } else
            connexions.remove(connexion);
    }

    public void clean() {
        composant = Composant.EMPTY;
        connexions = new ArrayList<>();
        power = false;
    }
}
