package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plateau {
    private ArrayList<ArrayList<Tuile>> tuiles;

    public Plateau() {
        generateTuiles();
        randomizeTuiles();
        settings();
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

    // on allume les sources et les tuiles connectés
    public void settings() {
        for (int i = 0; i < tuiles.size(); i++) {
            for (int j = 0; j < tuiles.get(0).size(); j++) {
                if (tuiles.get(i).get(j).getComposant() == 'S') {
                    turnOn(i, j);
                }
            }
        }
    }

    // On allume une tuile actuelle, obtient la liste des tuiles connectées et les
    // allume récursivement
    public void turnOn(int i, int j) {
        // Si une tuile est déjà allumé, il n'est pas nécessaire de la visiter
        if (!tuiles.get(i).get(j).isPower()) {
            tuiles.get(i).get(j).setPower(true);
            ArrayList<ArrayList<Integer>> tuilesToTurnOn = getConnectedNeighbors(i, j);
            for (ArrayList<Integer> tuileСoordinates : tuilesToTurnOn) {
                turnOn(tuileСoordinates.get(0), tuileСoordinates.get(1));
            }
        }
    }

    // On obtient la liste des connexions de la tuile et vérifie s'il y a des tuiles
    // connectée
    public ArrayList<ArrayList<Integer>> getConnectedNeighbors(int i, int j) {
        ArrayList<ArrayList<Integer>> neighbors = new ArrayList<>();
        ArrayList<Integer> connexions = tuiles.get(i).get(j).getConnexions();
        ArrayList<Integer> neighbor;
        for (Integer connexion : connexions) {
            neighbor = getNeighbor(i, j, connexion);
            if (neighbor != null)
                neighbors.add(neighbor);
        }
        return neighbors;
    }

    // Pour les coordonnées et le connexion données, on vérifie si la tuile
    // adjacente existe et s'il y a une connexion correspondante.
    // Par exemple, pour une tuile qui a une connexion 0, il faut vérifier si la
    // tuile existe sur le dessus et qu'elle a une connexion 2
    private ArrayList<Integer> getNeighbor(int i, int j, Integer connexion) {
        ArrayList<Integer> neighbor = null;
        Integer neighborConnexion = (connexion + 2) % 4;
        if (connexion == 0 && i != 0) {
            if (tuiles.get(i - 1).get(j).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i - 1, j));
        }

        if (connexion == 1 && j != tuiles.get(0).size() - 1) {
            if (tuiles.get(i).get(j + 1).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i, j + 1));
        }

        if (connexion == 2 && i != tuiles.size() - 1) {
            if (tuiles.get(i + 1).get(j).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i + 1, j));
        }

        if (connexion == 3 && j != 0) {
            if (tuiles.get(i).get(j - 1).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i, j - 1));
        }

        return neighbor;
    }

    @Override
    public String toString() {
        String result = "Plateau type : " + tuiles.get(0).get(0).getType() + "\n";
        for (ArrayList<Tuile> arrayList : tuiles) {
            for (Tuile tuile : arrayList) {
                result += tuile + " | ";
            }
            result += "\n";
        }
        return result;
    }
}
