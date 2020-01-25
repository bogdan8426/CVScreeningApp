package CVScreening.DataModel;

import java.time.Period;

public enum JobLevel {
    JUNIOR, MIDDLE, SENIOR;

    public int getMinimumYears(){
        switch (this){
            case MIDDLE: return 1;
            case SENIOR: return 5;
            default: return 0;
        }
    }
}
