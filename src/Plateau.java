package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plateau {
    private TuilesList tuiles;

    public Plateau(int n) {
        generateTuiles(n);
        randomizeTuiles();
        settings();
    }

    public void randomizeTuiles() {
        Random r = new Random(System.currentTimeMillis());
        int n;

        for (Tuile tuile : tuiles) {
            n = r.nextInt(tuiles.getTypeValue() - 1) + 1;
            tuile.turnNtimes(n, tuiles.getTypeValue());
        }
    }

    // TO DO: passer le num de level en param
    public void generateTuiles(int n) {
        // lecture du txt
        tuiles = Convertion.parseFile(n);
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
        ArrayList<Connexion> connexions = tuiles.getConnexions(i, j);
        ArrayList<Integer> neighbor;
        for (Connexion connexion : connexions) {
            neighbor = getNeighbor(i, j, connexion.getValue());
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
        ArrayList<Integer> neighbor = new ArrayList<>();
        Connexion neighborConnexion = Connexion
                .intToEnum((connexion + tuiles.getTypeValue() / 2) % tuiles.getTypeValue());

        // Dans HEX tuile il y a 6 côtés, donc il faut faire correspondre SQR at HEX
        // tuiles pour que le swith fonctionne correctement
        // 0, 1, 2, 3 de SQR = 0, 1, 3, 5 de HEX
        if (tuiles.getType() == Type.SQR && connexion > 1)
            connexion = 2 * connexion - 1;

        int neighborI, neighborJ;

        if (j % 2 == 1 || tuiles.getType() == Type.SQR)
            switch (connexion) {
                case 0:
                    neighborI = i - 1;
                    neighborJ = j;
                    break;
                case 1:
                    neighborI = i;
                    neighborJ = j + 1;
                    break;
                case 2:
                    neighborI = i + 1;
                    neighborJ = j + 1;
                    break;
                case 3:
                    neighborI = i + 1;
                    neighborJ = j;
                    break;
                case 4:
                    neighborI = i + 1;
                    neighborJ = j - 1;
                    break;
                case 5:
                    neighborI = i;
                    neighborJ = j - 1;
                    break;
                default:
                    return neighbor;
            }
        else
            switch (connexion) {
                case 0:
                    neighborI = i - 1;
                    neighborJ = j;
                    break;
                case 1:
                    neighborI = i - 1;
                    neighborJ = j + 1;
                    break;
                case 2:
                    neighborI = i;
                    neighborJ = j + 1;
                    break;
                case 3:
                    neighborI = i + 1;
                    neighborJ = j;
                    break;
                case 4:
                    neighborI = i;
                    neighborJ = j - 1;
                    break;
                case 5:
                    neighborI = i - 1;
                    neighborJ = j - 1;
                    break;
                default:
                    return neighbor;
            }

        if (isValid(neighborI, neighborJ)) {
            neighbor = getNeighborIfValid(neighborI, neighborJ, neighbor, neighborConnexion);
        }

        return neighbor;
    }

    private boolean isValid(int i, int j) {
        return i >= 0 && i < tuiles.rowsNumber() && j >= 0 && j < tuiles.columnsNumber();
    }

    private ArrayList<Integer> getNeighborIfValid(int i, int j, ArrayList<Integer> neighbor,
            Connexion neighborConnexion) {
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

    public TuilesList getTuiles() {
        return tuiles;
    }
}
