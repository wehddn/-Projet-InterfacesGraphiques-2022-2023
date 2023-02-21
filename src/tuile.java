package src;
import java.util.ArrayList;

public class tuile {
    private int type; //4/6
    private char composant; //S/L/W/.
    private ArrayList<Integer> connexions; //static?
    private boolean power;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public char getComposant() {
        return composant;
    }
    
    public void setComposant(char composant) {
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
}
