package src;

public enum Type {
    SQR(4),
    HEX(6);

    private final int type;

    Type(int type) {
        this.type = type;
    }

    public int getValue() {
        return type;
    }

    public int getHeight() {
        if (this == SQR)
            return 120;
        else
            return 104;
    }

    public int getTexturesCount(){
        if (this == SQR)
            return 9;
        else
            return 12;
    }
}