package CVScreening.DataModel;

public enum Sex {
    MALE, FEMALE, NOT_SPECIFIED;

    public int toID() {
        switch (this){
            case MALE: return 1;
            case FEMALE: return 2;
            default: return 3;
        }

    }
}
