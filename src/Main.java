package src;

public class Main {
    public static void main(String[] args){
        Plateau p = new Plateau();
        System.out.println(p);
        p.turn(3, 2);
        System.out.println(p);
    }
}
