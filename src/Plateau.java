package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plateau {
    private TuilesList tuiles;

    public Plateau() {
        generateTuiles();
        randomizeTuiles();
        settings();
    }

    public void randomizeTuiles() {
        Random r = new Random(System.currentTimeMillis());
        int n;

        for (Tuile tuile : tuiles) {
            n = r.nextInt(tuiles.getType() - 1) + 1;
            tuile.turnNtimes(n, tuiles.getType());
        }
    }

    public void generateTuiles() {
        // lecture du txt
        tuiles = Convertion.parseFile(2);
        tuiles.setVariables();
        // lecture du txt
    }

    // Le but du jeu est d'allumer toutes les lampes. On va vérifier les lampes
    // après chaque tour de tuile. Pour ce faire, on va enregistrer les coordonnées
    // des lampes
    // En plus, on va enregistrer les coordonnées des bornes pour un accès rapide
    public void settings() {
        for (int i = 0; i < tuiles.rowsNumber(); i++) {
            for (int j = 0; j < tuiles.columnsNumber(); j++) {
                Composant composant = tuiles.getComposant(i, j);
                switch (composant) {
                    // on allume les sources et les tuiles connectés
                    case SOURCE:
                        turnOn(i, j);
                        tuiles.addSource(i, j);
                        break;
                    case LAMPE:
                        tuiles.addLampe(i, j);
                        break;
                    case WIFI:
                        tuiles.addBorne(i, j);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // fonction pour tourner la tuile sélectionnée
    public void turn(int i, int j) {
        tuiles.turn(i, j);

        // On éteint tous les tuiles
        for (Tuile tuile : tuiles) {
            tuile.setPower(false);
        }

        // On allume toutes les tuiles récursivement à partir des sources
        for (ArrayList<Integer> source : tuiles.getSources()) {
            turnOn(source.get(0), source.get(1));
        }

        // On vérifie si toutes les lampes sont allumées
        if (checkWin())
            System.out.println("YOU WIN");
    }

    // Obtenir la liste des bornes sans borne spécifié.
    private ArrayList<ArrayList<Integer>> getNeighborsBornes(int i, int j) {
        ArrayList<ArrayList<Integer>> newBornes = new ArrayList<>(tuiles.getBornes());
        newBornes.remove(new ArrayList<>(List.of(i, j)));
        return newBornes;
    }

    // On vérifie si toutes les lampes sont allumées
    private boolean checkWin() {
        boolean result = true;
        for (ArrayList<Integer> tuileСoordinates : tuiles.getLampes()) {
            if (!tuiles.isPower(tuileСoordinates.get(0), tuileСoordinates.get(1)))
                result = false;
        }
        return result;
    }

    // On allume une tuile actuelle, obtient la liste des tuiles connectées et les
    // allume récursivement
    public void turnOn(int i, int j) {
        // Si une tuile est déjà allumé, il n'est pas nécessaire de la visiter
        if (!tuiles.isPower(i, j)) {
            tuiles.setPower(i, j);
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
        ArrayList<Integer> connexions = tuiles.getConnexions(i, j);
        ArrayList<Integer> neighbor;
        for (Integer connexion : connexions) {
            neighbor = getNeighbor(i, j, connexion);
            if (neighbor.size() != 0)
                neighbors.add(neighbor);
        }
        // On ajoute des bornes commes des voisins
        if (tuiles.getComposant(i, j) == Composant.WIFI)
            neighbors.addAll(getNeighborsBornes(i, j));
        return neighbors;
    }

    // Pour les coordonnées et le connexion données, on vérifie si la tuile
    // adjacente existe et s'il y a une connexion correspondante.
    // Par exemple, pour une tuile qui a une connexion 0, il faut vérifier si la
    // tuile existe sur le dessus et qu'elle a une connexion 2
    private ArrayList<Integer> getNeighbor(int i, int j, Integer connexion) {
        if (tuiles.getType() == 4)
            return getNeighbor4(i, j, connexion);
        else
            return getNeighbor6(i, j, connexion);
    }

    private ArrayList<Integer> getNeighbor6(int i, int j, Integer connexion) {
        ArrayList<Integer> neighbor = new ArrayList<>();
        Integer neighborConnexion = (connexion + 3) % 6;
        int rowSize = tuiles.rowsNumber();
        int columnSize = tuiles.columnsNumber();
        switch (connexion) {
            case 0:
                if (i != 0)
                    neighbor = getNeighborIfValid(i - 1, j, neighbor, neighborConnexion);
                break;
            case 1:
                if (j != rowSize - 1)
                    neighbor = getNeighborIfValid(i, j + 1, neighbor, neighborConnexion);
                break;
            case 2:
                if (i != columnSize - 1 && j != rowSize - 1)
                    neighbor = getNeighborIfValid(i + 1, j + 1, neighbor, neighborConnexion);
                break;
            case 3:
                if (i != columnSize - 1)
                    neighbor = getNeighborIfValid(i + 1, j, neighbor, neighborConnexion);
                break;
            case 4:
                if (i != columnSize - 1 && j != 0)
                    neighbor = getNeighborIfValid(i + 1, j - 1, neighbor, neighborConnexion);
                break;
            case 5:
                if (j != 0)
                    neighbor = getNeighborIfValid(i, j - 1, neighbor, neighborConnexion);
                break;
            default:
                break;
        }
        return neighbor;
    }

    private ArrayList<Integer> getNeighbor4(int i, int j, Integer connexion) {
        ArrayList<Integer> neighbor = new ArrayList<>();
        Integer neighborConnexion = (connexion + 2) % 4;
        if (connexion == 0 && i != 0) {
            neighbor = getNeighborIfValid(i - 1, j, neighbor, neighborConnexion);
        }

        if (connexion == 1 && j != tuiles.columnsNumber() - 1) {
            neighbor = getNeighborIfValid(i, j + 1, neighbor, neighborConnexion);
        }

        if (connexion == 2 && i != tuiles.rowsNumber() - 1) {
            neighbor = getNeighborIfValid(i + 1, j, neighbor, neighborConnexion);
        }

        if (connexion == 3 && j != 0) {
            neighbor = getNeighborIfValid(i, j - 1, neighbor, neighborConnexion);
        }

        return neighbor;
    }

    private ArrayList<Integer> getNeighborIfValid(int i, int j, ArrayList<Integer> neighbor,
            Integer neighborConnexion) {
        if (tuiles.getConnexions(i, j).contains(neighborConnexion))
            neighbor = new ArrayList<>(List.of(i, j));
        return neighbor;
    }

    @Override
    public String toString() {
        String result = "Plateau type : " + tuiles.getType() + "\n";
        for (ArrayList<Tuile> arrayList : tuiles.getTuiles()) {
            for (Tuile tuile : arrayList) {
                result += tuile + " | ";
            }
            result += "\n";
        }
        return result;
    }

    public int getWith() {
        return tuiles.columnsNumber();
    }

    public int getHeight() {
        return tuiles.rowsNumber();
    }

    public ArrayList<ArrayList<Tuile>> getTuiles() {
        return tuiles.getTuiles();
    }
}
