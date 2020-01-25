package CVScreening.DataModel;

public class Education {
    private TimeInterval timeInterval;
    private String universityName;
    private Domain domain;

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

    @Override
    public String toString() {
        return  "\n\n\t" + timeInterval +
                "\n\t" + universityName +
                "\n\t" + domain;
    }
}
