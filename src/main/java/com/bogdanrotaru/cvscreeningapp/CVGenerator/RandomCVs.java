package com.bogdanrotaru.cvscreeningapp.CVGenerator;

import com.bogdanrotaru.cvscreeningapp.CVGenerator.root.InputReader;
import com.bogdanrotaru.cvscreeningapp.exceptions.CVFilesReadException;
import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.Education;
import com.bogdanrotaru.cvscreeningapp.model.Experience;
import com.bogdanrotaru.cvscreeningapp.model.PersonalInfo;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Sex;
import com.bogdanrotaru.cvscreeningapp.model.helpers.TimeInterval;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.logging.Logger;

class RandomCVs {

    private List<CV> cvs = new LinkedList<>();
    private Random random = new Random();
    private InputReader reader = new InputReader();

    public List<CV> generate(int count) throws CVFilesReadException {
        try {
            loadCVsFromTextFiles(count);
        } catch (FileNotFoundException e) {
            throw new CVFilesReadException("Problem generating list of random cvs, couldn't find txt file! ", e);
        }
        if (!cvs.isEmpty()) {
            Logger.getLogger(CVGenerator.class.getName()).info("CVs generated successfully!");
        }
        return cvs;
    }

    private void loadCVsFromTextFiles(int count) throws FileNotFoundException {
        Map<String, Sex> names = reader.getNames(count);
        for (String name : names.keySet()) {
            cvs.add(getCV(name, names.get(name)));
        }
    }

    private CV getCV(String name, Sex sex) throws FileNotFoundException {
        PersonalInfo info = getInfo(name, sex);
        List<Education> educations = getRandomEducationList(info.getBirthday());
        List<Experience> experiences = getRandomExperienceList(educations.get(educations.size() - 1).getDomain(),
                educations.get(educations.size() - 1).getTimeInterval().getEndDate());

        return new CV(info, educations, experiences);
    }

    private PersonalInfo getInfo(String name, Sex sex) {
        String[] fullName = name.split(",");
        return new PersonalInfo(fullName[0], fullName[1], getRandomPhoneNumber(), getRandomBirthday(), sex);
    }

    private List<Education> getRandomEducationList(LocalDate birthday) throws FileNotFoundException {
        List<Education> educations = new LinkedList<>();
        int noSections = random.nextInt(1) + 2;
        LocalDate collegeStart = LocalDate.of(birthday.getYear() + 19,
                Month.OCTOBER,
                random.nextInt(6) + 1);
        List<String> universities = reader.getUniversities();
        Domain[] domains = Domain.values();
        for (int i = 1; i <= noSections; i++) {
            Domain domain = domains[random.nextInt(domains.length)];

            educations.add(new Education(getEducationPeriod(collegeStart.plusYears(i - 1)),
                    universities.get(random.nextInt(universities.size() - 1)),
                    domain));
        }
        return educations;
    }

    private List<Experience> getRandomExperienceList(Domain studyDomain, LocalDate studyEndDate) throws FileNotFoundException {
        List<Experience> experiences = new LinkedList<>();
        int noSections = random.nextInt(3) + 2;
        Map<Domain, Map<String, String>> jobs = reader.getJobs();
        List<String> companies = reader.getCompanies();
        for (int i = 1; i <= noSections; i++) {
            TimeInterval timeInterval = getExperiencePeriod(studyEndDate);
            if (i == 1) {
                timeInterval = TimeInterval.between(studyEndDate, studyEndDate.plusMonths(random.nextInt(6) + 1));
            }
            Domain domain = new ArrayList<>(jobs.keySet()).get(random.nextInt(jobs.size() - 1));
            if (i > 1 && !domain.equals(studyDomain)) {
                domain = studyDomain;
            }
            Map<String, String> positionDescription = jobs.get(domain);
            String position = new ArrayList<>(positionDescription.keySet()).get(random.nextInt(positionDescription.size() - 1));
            String description = positionDescription.get(position);
            experiences.add(new Experience(timeInterval, position, description, companies.get(random.nextInt(companies.size() - 1)), domain));

        }

        return experiences;
    }

    private LocalDate getRandomBirthday() {
        return LocalDate.of(1950 + random.nextInt(52),
                Month.of(random.nextInt(11) + 1),
                random.nextInt(28) + 1);
    }

    private String getRandomPhoneNumber() {
        return "(07" +
                (random.nextInt(89) + 10) +
                ") " +
                (random.nextInt(899) + 100) +
                " " +
                (random.nextInt(899) + 100);

    }

    private TimeInterval getEducationPeriod(LocalDate start) {
        int years = random.nextInt(2) + 3;
        return TimeInterval.between(start, start.plusYears(years).minusMonths(3).plusDays(14));
    }

    private TimeInterval getExperiencePeriod(LocalDate start) {
        int years = start.getYear() + random.nextInt(20) + 1;
        int months = random.nextInt(6) + 6;
        int days = random.nextInt(27) + 1;
        return TimeInterval.between(start.plusMonths(3), LocalDate.of(years, months, days));
    }
}
