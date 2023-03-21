package src;

//TO DO : interfaces?

public enum Connexion {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int connexion;

    Connexion(int connexion) {
        this.connexion = connexion;
    }

    public int getValue() {
        return connexion;
    }

    public static Connexion intToEnum(int i) {
        switch (i) {
            case 0:
                return Connexion.ZERO;
            case 1:
                return Connexion.ONE;
            case 2:
                return Connexion.TWO;
            case 3:
                return Connexion.THREE;
            case 4:
                return Connexion.FOUR;
            case 5:
                return Connexion.FIVE;
            default:
                return null;
        }
    }
}
