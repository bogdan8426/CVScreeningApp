package CVScreening.model.helpers;

public enum JobLevel {
    JUNIOR, MIDDLE, SENIOR;

    public int getMinimumYears() {
        switch (this) {
            case MIDDLE:
                return 1;
            case SENIOR:
                return 5;
            default:
                return 0;
        }
    }
}
