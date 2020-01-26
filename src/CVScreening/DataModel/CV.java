package CVScreening.DataModel;

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

    public double computeScore(JobDescription jobDescription) {
        score = new Score();
        score.compute(jobDescription);
        return score.getValue();
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

    private class Score {

        double value;

        public double getValue() {
            return value;
        }

        public void compute(JobDescription jobDescription) {
            computeRequirementScore(jobDescription.getDomain(),
                    jobDescription.getPosition(),
                    jobDescription.getJobLevel(),
                    jobDescription.getMinimumStudyYears());

            if (jobDescription.hasOptionals()) {
                computeOptionalScore(jobDescription.getHasLeadershipExperience(),
                        jobDescription.getJobsChanged(),
                        jobDescription.getSpecificUniversity());
            }
        }

        private void computeRequirementScore(Domain domain, String position, JobLevel jobLevel, int minimumStudyTime) {

            // Domain
            double educationImportance = 0.1, experienceImportance = 0.4;
            if (jobLevel.equals(JobLevel.JUNIOR)) {
                educationImportance = 0.4;
                experienceImportance = 0.1;
            }
            for (Education education : getEducation()) {
                if (education.getDomain().equals(domain)) {
                    value += educationImportance;
                    break;
                }
            }
            for (Experience experience : getExperience()) {
                if (experience.getDomain().equals(domain)) {
                    value += experienceImportance;
                    break;
                }
            }

            // Job level
            Period experienceInPosition = Period.ZERO;
            for (Experience experience : getExperience()) {
                if (experience.getPosition().equals(position)) {
                    experienceInPosition.plus(experience.getTimeInterval().getPeriod());
                }
            }
            if (experienceInPosition.getYears() >= jobLevel.getMinimumYears()) {
                value += 0.3;
            }

            // Minimum study time
            int studiedYearsInDomain = 0;
            for (Education education : getEducation()) {
                if (education.getDomain().equals(domain)) {
                    studiedYearsInDomain += education.getTimeInterval().getPeriod().getYears();
                }
            }
            if (studiedYearsInDomain >= minimumStudyTime) {
                value += 0.2;
            }


        }

        private void computeOptionalScore(boolean hasLeadershipExperience, Integer jobsChanged, String specificUniversity) {
            int count = new int[]
                    {hasLeadershipExperience ? 1 : 0,
                            jobsChanged != null ? 1 : 0,
                            specificUniversity != null ? 1 : 0}.length;

            value *= 0.9;

            // Leadership
            if (hasLeadershipExperience) {
                for (Experience experience : getExperience()) {
                    if (isLeadership(experience.getPosition())) {
                        value += 0.1 / count;
                        if (experience.getTimeInterval().getPeriod().getYears() < 1) {
                            value -= 0.1 / (count * 2);
                        }
                    }
                }
            }

            // Jobs changed
            if(jobsChanged !=null && getExperience().size() <= jobsChanged){
                value += 0.1 / count;
            }

            // University check
            if(specificUniversity != null){
                for(Education education: getEducation()){
                    if(education.getUniversityName().equals(specificUniversity)){
                        value += 0.1 / count;
                        break;
                    }
                }
            }
        }

        private boolean isLeadership(String position) {
            List<String> dictionary = Arrays.asList("manager", "management", "administrator", "leader", "lead", "director",
                    "coordinator", "supervisor", "captain");

            String[] words = position.split(" ");
            for(String word: words){
                if(word.length() > 2 ){
                    if(dictionary.contains(word.toLowerCase())){
                        return true;
                    }
                }
            }

            return false;
        }
    }
}




