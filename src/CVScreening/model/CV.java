package CVScreening.model;

import CVScreening.model.helpers.Domain;
import CVScreening.model.helpers.JobLevel;

import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CV {

    private PersonalInfo info;
    private List<Education> education;
    private List<Experience> experience;

    private Score score;

    public double getScore() {
        return score.getValue();
    }

    public void computeScore(JobDescription jobDescription) {
        this.score = new Score().compute(this, jobDescription);
    }

    public CV(PersonalInfo info, List<Education> education, List<Experience> experience) {
        this.info = info;
        this.education = education;
        this.experience = experience;
    }

    public PersonalInfo getInfo() {
        return info;
    }

    public List<Education> getEducation() {
        return education;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setInfo(PersonalInfo info) {
        this.info = info;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "\n======================================================================\n" +
                "Curriculum Vitae for " + info.getFirstName() + " " + info.getLastName() +
                "\n====Personal Information====\n" + info +
                "\n====Education====\n" + education +
                "\n====Experience====\n" + experience +
                "\n======================================================================\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CV)) return false;
        CV cv = (CV) o;
        return info.equals(cv.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }

}




