package src;
import java.util.ArrayList;

public class Tuile {
    private int type; //4/6
    private String composant; //S/L/W/.
    private ArrayList<Integer> connexions; //static?
    private boolean power;
    private boolean visited;

    public Tuile(String comp, ArrayList<Integer> connex){
        composant=comp;
        connexions=connex;
    }

    
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getComposant() {
        return composant;
    }
    public void setComposant(String composant) {
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

    public void turnTuile(int n){
        for(int i=0;i<n;i++){
            for (Integer ico : connexions) {
                connexions.set(ico, (ico+1%type));
            }
        }
        
    }


    
    



}
