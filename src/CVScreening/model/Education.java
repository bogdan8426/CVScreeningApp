package CVScreening.model;

import CVScreening.model.helpers.Domain;
import CVScreening.model.helpers.TimeInterval;

public class Education {
    private TimeInterval timeInterval;
    private String universityName;
    private Domain domain;

    public Education(){}

    public Education(TimeInterval timeInterval, String universityName, Domain domain) {
        this.timeInterval = timeInterval;
        this.universityName = universityName;
        this.domain = domain;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public String getUniversityName() {
        return universityName;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return  "\n\n\t" + timeInterval +
                "\n\t" + universityName +
                "\n\t" + domain;
    }
}
