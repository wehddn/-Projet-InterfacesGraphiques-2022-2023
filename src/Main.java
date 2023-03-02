package src;

public class Main {
    public static void main(String[] args){
        Plateau p = new Plateau();
        System.out.println(p);
        p.turn(1, 0);
        System.out.println(p);
        p.turn(0, 1);
        System.out.println(p);
        p.turn(0, 2);
        p.turn(0, 2);
        p.turn(0, 2);
        System.out.println(p);
    }
}
