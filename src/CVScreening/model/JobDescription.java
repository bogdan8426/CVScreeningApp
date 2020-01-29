package CVScreening.model;

import CVScreening.model.helpers.Domain;
import CVScreening.model.helpers.JobLevel;

public class JobDescription {

    // Requirements
    private Domain domain;
    private String position;
    private JobLevel jobLevel;
    private int minimumStudyYears;

    // Nice to have
    private boolean hasLeadershipExperience = false;
    private int jobsChanged = -1;
    private String specificUniversity;

    public boolean hasOptionals(){
        return hasLeadershipExperience || jobsChanged > 0 || specificUniversity != null;
    }

    public JobDescription(Domain domain, String position, JobLevel jobLevel, int minimumStudyTime) {
        this.domain = domain;
        this.position = position;
        this.jobLevel = jobLevel;
        this.minimumStudyYears = minimumStudyTime;
    }

    public void setHasLeadershipExperience(boolean hasLeadershipExperience) {
        this.hasLeadershipExperience = hasLeadershipExperience;
    }

    public void setJobsChanged(int jobsChanged) {
        this.jobsChanged = jobsChanged;
    }

    public void setSpecificUniversity(String specificUniversity) {
        this.specificUniversity = specificUniversity;
    }

    public Domain getDomain() {
        return domain;
    }

    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public int getMinimumStudyYears() {
        return minimumStudyYears;
    }

    public boolean getHasLeadershipExperience() {
        return hasLeadershipExperience;
    }

    public int getJobsChanged() {
        return jobsChanged;
    }

    public String getSpecificUniversity() {
        return specificUniversity;
    }

    public String getPosition() {
        return position;
    }
}
