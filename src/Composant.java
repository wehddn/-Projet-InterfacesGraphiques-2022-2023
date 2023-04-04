package src;

public enum Composant {
    SOURCE,
    LAMPE,
    WIFI,
    EMPTY;

    public Composant next() {
        switch (this) {
            case SOURCE:
                return Composant.LAMPE;
            case LAMPE:
                return Composant.WIFI;
            case WIFI:
                return Composant.EMPTY;
            case EMPTY:
                return Composant.SOURCE;
        }
        return null;
    }
}