package src;

//TO DO: verifier les valeur des attributs
public enum Type {
    SQR(4),
    HEX(6);

    private final int type;
    private final int height;
    private final int texturesCount;
    private final int rotateAngle;

    Type(int type) {
        this.type = type;
        if (type == 4) {
            this.height = 120;
            this.texturesCount = 9;
            this.rotateAngle = 90;
        } else {
            this.height = 104;
            this.texturesCount = 12;
            this.rotateAngle = 60;
        }
    }

    public int getValue() {
        return type;
    }

    public int getHeight() {
        return height;
    }

    public int getTexturesCount() {
        return texturesCount;
    }

    public int getRotateAngle() {
        return rotateAngle;
    }

    @Override
    public String toString() {
        switch (this) {
            case HEX:
                return "H";
            case SQR:
                return "S";
        }
        return null;
    }

}