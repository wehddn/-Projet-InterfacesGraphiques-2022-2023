package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plateau {
    private ArrayList<ArrayList<Tuile>> tuiles;
    private ArrayList<ArrayList<Integer>> lampes;
    private ArrayList<ArrayList<Integer>> bornes;

    public Plateau() {
        lampes = new ArrayList<>();
        bornes = new ArrayList<>();
        generateTuiles();
        // randomizeTuiles();
        settings();
    }

    public void randomizeTuiles() {
        Random r = new Random(System.currentTimeMillis());
        int n;
        for (ArrayList<Tuile> t : tuiles) {
            for (Tuile tuile : t) {
                n = r.nextInt(tuile.getType() - 1) + 1;
                tuile.turnNtimes(n);
            }
        }
    }

    public void generateTuiles() {
        tuiles = new ArrayList<ArrayList<Tuile>>();
        // lecture du txt
        tuiles = Convertion.parseFile(3);
    }

    // Le but du jeu est d'allumer toutes les lampes. On va vérifier les lampes
    // après chaque tour de tuile. Pour ce faire, on va enregistrer les coordonnées
    // des lampes
    public void settings() {
        for (int i = 0; i < tuiles.size(); i++) {
            for (int j = 0; j < tuiles.get(0).size(); j++) {
                char composant = tuiles.get(i).get(j).getComposant();
                // on allume les sources et les tuiles connectés
                switch(composant){
                    case 'S' : 
                        turnOn(i, j);
                        break;
                    case 'L' :
                        lampes.add(new ArrayList<>(List.of(i, j)));
                        break;
                    case 'W' :
                        bornes.add(new ArrayList<>(List.of(i, j)));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // fonction pour tourner la tuile sélectionnée
    public void turn(int i, int j) {
        Tuile currentTuile = tuiles.get(i).get(j);
        currentTuile.turn();

        // Si la tuile est la source, allumer toutes les tuiles
        if (currentTuile.getComposant() == 'S') {
            currentTuile.setPower(false);
            turnOn(i, j);
        } else {
            // S'il n'y a pas de tuiles allumées autour, désactivez la tuile sélectionnée
            if (!turnedOnNeighborExist(i, j)) {
                currentTuile.setPower(false);
            } else {
                // Pour chaque tuile adjacente, on vérifie s'il y a une source dans le circuit
                // S'il y a au moins une source, allumer tout, sinon éteigner
                ArrayList<ArrayList<Integer>> neighbors = getConnectedNeighbors(i, j);
                boolean source = false;
                for (ArrayList<Integer> neighbor : neighbors) {
                    if (sourceExist(neighbor.get(0), neighbor.get(1)))
                        source = true;
                    resetVisited();
                }
                if (source)
                    turnOn(i, j);
                else
                    turnOff(i, j);
            }
        }
        // On vérifie si toutes les lampes sont allumées
        if (checkWin())
            System.out.println("YOU WIN");
    }

    // Obtenir la liste des bornes sans borne spécifié.
    private ArrayList<ArrayList<Integer>> getNeighborsBornes(int i, int j) {
        ArrayList<ArrayList<Integer>> newBornes = new ArrayList<>(bornes);
        newBornes.remove(new ArrayList<>(List.of(i, j)));
        return newBornes;
    }

    // On vérifie si toutes les lampes sont allumées
    private boolean checkWin() {
        boolean result = true;
        for (ArrayList<Integer> tuileСoordinates : lampes) {
            if (!tuiles.get(tuileСoordinates.get(0)).get(tuileСoordinates.get(1)).isPower())
                result = false;
        }
        return result;
    }

    private void resetVisited() {
        for (ArrayList<Tuile> tuileRow : tuiles) {
            for (Tuile tuile : tuileRow) {
                tuile.setVisited(false);
            }
        }
    }

    // On vérifie récursivement toutes les tuiles, et si on trouve une source, renvoie true
    public boolean sourceExist(int i, int j) {
        tuiles.get(i).get(j).setVisited(true);
        if (tuiles.get(i).get(j).isPower()) {
            ArrayList<ArrayList<Integer>> tuilesToCheck = getConnectedNeighbors(i, j);
            for (ArrayList<Integer> tuileСoordinates : tuilesToCheck) {
                if (tuiles.get(tuileСoordinates.get(0)).get(tuileСoordinates.get(1)).getComposant() == 'S') {
                    return true;
                } else if (!tuiles.get(tuileСoordinates.get(0)).get(tuileСoordinates.get(1)).isVisited()) {
                    if (sourceExist(tuileСoordinates.get(0), tuileСoordinates.get(1)))
                        return true;
                }
            }
        }
        return false;
    }

    // On vérifie toutes les tuiles connectées et si au moins une est activée,
    // renvoie true
    public boolean turnedOnNeighborExist(int i, int j) {
        ArrayList<ArrayList<Integer>> tuilesTurnedOn = getConnectedNeighbors(i, j);
        for (ArrayList<Integer> tuileСoordinates : tuilesTurnedOn) {
            if (tuiles.get(tuileСoordinates.get(0)).get(tuileСoordinates.get(1)).isPower())
                return true;
        }
        for (ArrayList<Integer> borne : bornes) {
            if (tuiles.get(borne.get(0)).get(borne.get(1)).isPower())
                return true;
        }
        return false;
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

    public void turnOff(int i, int j) {
        // Si une tuile est déjà allumé, il n'est pas nécessaire de la visiter
        if (tuiles.get(i).get(j).isPower()) {
            tuiles.get(i).get(j).setPower(false);
            ArrayList<ArrayList<Integer>> tuilesToTurnOff = getConnectedNeighbors(i, j);
            for (ArrayList<Integer> tuileСoordinates : tuilesToTurnOff) {
                turnOff(tuileСoordinates.get(0), tuileСoordinates.get(1));
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
        // On ajoute des bornes commes des voisins
        if (tuiles.get(i).get(j).getComposant() == 'W')
            neighbors.addAll(getNeighborsBornes(i, j));
        return neighbors;
    }

    // Pour les coordonnées et le connexion données, on vérifie si la tuile
    // adjacente existe et s'il y a une connexion correspondante.
    // Par exemple, pour une tuile qui a une connexion 0, il faut vérifier si la
    // tuile existe sur le dessus et qu'elle a une connexion 2
    private ArrayList<Integer> getNeighbor(int i, int j, Integer connexion) {
        if (tuiles.get(0).get(0).getType() == 4)
            return getNeighbor4(i, j, connexion);
        else
            return getNeighbor6(i, j, connexion);
    }

    private ArrayList<Integer> getNeighbor6(int i, int j, Integer connexion) {
        ArrayList<Integer> neighbor = null;
        Integer neighborConnexion = (connexion + 3) % 6;
        if (connexion == 0 && i != 0) {
            if (tuiles.get(i - 1).get(j).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i - 1, j));
        }

        if (connexion == 1 && j != tuiles.get(0).size() - 1) {
            if (tuiles.get(i).get(j + 1).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i, j + 1));
        }

        if (connexion == 2 && i != tuiles.size() - 1 && j != tuiles.get(0).size() - 1) {
            if (tuiles.get(i + 1).get(j + 1).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i + 1, j + 1));
        }

        if (connexion == 3 && i != tuiles.size() - 1) {
            if (tuiles.get(i + 1).get(j).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i + 1, j));
        }

        if (connexion == 4 && i != tuiles.size() - 1 && j != 0) {
            if (tuiles.get(i + 1).get(j - 1).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i + 1, j - 1));
        }

        if (connexion == 5 && j != 0) {
            if (tuiles.get(i).get(j - 1).getConnexions().contains(neighborConnexion))
                neighbor = new ArrayList<>(List.of(i, j - 1));
        }

        return neighbor;
    }

    private ArrayList<Integer> getNeighbor4(int i, int j, Integer connexion) {
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
