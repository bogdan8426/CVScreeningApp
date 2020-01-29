package CVScreening.model;

import CVScreening.model.helpers.Domain;
import CVScreening.model.helpers.JobLevel;

import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

class Score {

        double value;

        public double getValue() {
            return value;
        }

        public Score compute(CV cv, JobDescription jobDescription) {
            computeRequirementScore(cv,
                    jobDescription.getDomain(),
                    jobDescription.getPosition(),
                    jobDescription.getJobLevel(),
                    jobDescription.getMinimumStudyYears());

            if (jobDescription.hasOptionals()) {
                computeOptionalScore(cv,
                        jobDescription.getHasLeadershipExperience(),
                        jobDescription.getJobsChanged(),
                        jobDescription.getSpecificUniversity());
            }
            return this;
        }

        /*
            This method calculates the Score.value for the CV input matching the specified requirements
            -> min(value) = 0;
            -> max(value) = 1;

            If the CV contains both education and experience in the specified domain,
                we add 0.5 to the value.
            If the CV has more years of experience than the minimum specified by the jobLevel in the specified position,
                we add 0.3 to the value.
            If the CV has more years of study than the specified minimumStudyYears,
                we add 0.2 to the value.
         */
        private void computeRequirementScore(CV cv, Domain domain, String position, JobLevel jobLevel, int minimumStudyYears) {
            List<Education> educations = cv.getEducation();
            List<Experience> experiences = cv.getExperience();


            double educationImportance = 0.1, experienceImportance = 0.4;
            if (jobLevel.equals(JobLevel.JUNIOR)) {
                educationImportance = 0.4;
                experienceImportance = 0.1;
            }

            // Check if the person studied the specified domain
            if(educations.stream().anyMatch(education -> education.getDomain().equals(domain))){
                value += educationImportance;
            }

            // Check if the person worked in the specified domain
            if(experiences.stream().anyMatch(experience -> experience.getDomain().equals(domain))){
                value += experienceImportance;
            }

            // Job level in specified position
            Optional<Integer> yearsInPosition = experiences.stream()
                    .filter(experience -> experience.getPosition().equals(position))
                    .map(experience -> experience.getTimeInterval().getPeriod().getYears())
                    .reduce(Integer::sum);
            if (yearsInPosition.isPresent() && yearsInPosition.get() >= jobLevel.getMinimumYears()) {
                value += 0.3;
            }

            // Minimum study time in the specified domain
            Optional<Integer> domainStudyPeriod = educations.stream()
                    .filter(education -> education.getDomain().equals(domain))
                    .map(education -> education.getTimeInterval().getPeriod().getYears())
                    .reduce(Integer::sum);
            if(domainStudyPeriod.isPresent() && domainStudyPeriod.get() >= minimumStudyYears){
                value += 0.2;
            }
        }


        /*
            Considering the job description has optional fields set, this method takes them into consideration as a 10% from
            the value computed by the computeRequirementsScore() method.
            If no optional fields fit the cv -> max(score) = 0.9;
            If all optional fields fit the cv -> max(score) = 1;

            Each optional has the same weight. Weight is calculated considering noOfOptionals:
             -> weight = 1/noOfOptionals;
            Considering optionals count for 10% of the Score.value, the corrected weight becomes:
             -> weight = 0.1/noOfOptionals;

         */
        private void computeOptionalScore(CV cv, boolean hasLeadershipExperience, Integer jobsChanged, String specificUniversity) {
            List<Education> educations = cv.getEducation();
            List<Experience> experiences = cv.getExperience();

            int noOfOptionals = IntStream.of(hasLeadershipExperience ? 1 : 0,
                                        jobsChanged != null ? 1 : 0,
                                        specificUniversity != null ? 1 : 0).sum();

            value *= 0.9;

            // Leadership
            if(hasLeadershipExperience){
                Optional<Period> experienceInLeadership = experiences.stream()
                        .filter(experience -> isLeadership(experience.getPosition()))
                        .map(experience -> experience.getTimeInterval().getPeriod())
                        .reduce(Period::plus);

                if(experienceInLeadership.isPresent()){
                    value += 0.1 / noOfOptionals;
                    if (experienceInLeadership.get().getYears() < 1) {
                        value -= 0.1 / (noOfOptionals * 2);
                    }
                }
            }

            // Jobs changed
            if(jobsChanged !=null && cv.getExperience().size() <= jobsChanged){
                value += 0.1 / noOfOptionals;
            }

            // University check
            if(specificUniversity != null){
                if(educations.stream().anyMatch(education -> education.getUniversityName().equals(specificUniversity))){
                    value += 0.1 / noOfOptionals;
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
