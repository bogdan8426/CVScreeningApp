package CVScreening.CVGenerator;

import CVScreening.CVGenerator.root.InputReader;
import CVScreening.DataModel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class CVGenerator {

    private ObservableList<CV> cvs= FXCollections.observableArrayList();
    private Random random = new Random();
    private InputReader reader = new InputReader();

    private CVGenerator(){}

    public static ObservableList<CV> generateRandomFiles(){
        CVGenerator generator = new CVGenerator();
        generator.loadCVsFromTextFiles();
        generator.writeXMLs();
        return generator.cvs;
    }

    public static ObservableList<CV> loadCVs() throws FileNotFoundException {
        return new CVReader().loadCVs();
    }

    private void writeXMLs(){
        if(cvs.isEmpty()){
            //TODO: throw some error
            System.out.println("ERROR, LOAD THE CVS FIRST!");
            return;
        }
        File cvFolder = new File("C:\\Users\\Bogdan\\Desktop\\Facultate\\Java advanced\\CV Screening Application\\src\\CVScreening\\CVs");
            if(cvFolder.list().length == 0){
                System.out.println("CVs folder is empty, writing xml cvs...");
            }else{
                System.out.println("CVs folder not empty, overwriting files...");
                for(File file: cvFolder.listFiles())
                    if (!file.isDirectory())
                        file.delete();
            }
        new CVWriter(cvs).saveCVs();
    }

    private ObservableList<CV> loadCVsFromTextFiles() {
        Map<String, Sex> names = reader.getNames();
        for(String name: names.keySet()) {
            cvs.add(getCV(name, names.get(name)));
        }
        return cvs;
    }

    private CV getCV(String name, Sex sex) {
        PersonalInfo info = getInfo(name, sex);
        List<Education> educations = getRandomEducationList(info.getBirthday());
        List<Experience> experiences = getRandomExperienceList(educations.get(educations.size()-1).getDomain(),
                                        educations.get(educations.size()-1).getTimeInterval().getEndDate());

        return new CV(info, educations,experiences);
    }

    private PersonalInfo getInfo(String name, Sex sex){
        String[] fullName = name.split(",");
        return new PersonalInfo(fullName[0], fullName[1], getRandomPhoneNumber(), getRandomBirthday(), sex);
    }

    private List<Education> getRandomEducationList(LocalDate birthday){
        List<Education> educations = new LinkedList<>();
        int noSections = random.nextInt(1) + 2;
        LocalDate collegeStart = LocalDate.of(birthday.getYear()+19,
                                                    Month.OCTOBER,
                                                random.nextInt(6)+1);
        List<String> universities = reader.getUniversities();
        Domain[] domains = Domain.values();
        for(int i = 1; i<=noSections; i++){
            Domain domain = domains[random.nextInt(domains.length)];

            educations.add(new Education(getEducationPeriod(collegeStart.plusYears(i-1)),
                                            universities.get(random.nextInt(universities.size()-1)),
                                            domain));
        }
        return educations;
    }

    private List<Experience> getRandomExperienceList(Domain studyDomain, LocalDate studyEndDate) {
        List<Experience> experiences = new LinkedList<>();
        int noSections = random.nextInt(3) + 2;
        Map<Domain, Map<String, String>> jobs = reader.getJobs();
        List<String> companies = reader.getCompanies();
        for(int i= 1; i<=noSections; i++){
            TimeInterval timeInterval = getExperiencePeriod(studyEndDate);
            if(i == 1) { timeInterval = TimeInterval.between(studyEndDate, studyEndDate.plusMonths(random.nextInt(6)+1));}
            Domain domain = new ArrayList<>(jobs.keySet()).get(random.nextInt(jobs.size()-1));
            if(i > 1 && !domain.equals(studyDomain)){
                    domain = studyDomain;
            }
            Map<String, String> positionDescription = jobs.get(domain);
            String position = new ArrayList<String>(positionDescription.keySet()).get(random.nextInt(positionDescription.size()-1));
            String description = positionDescription.get(position);
            experiences.add(new Experience(timeInterval, position, description, companies.get(random.nextInt(companies.size()-1)), domain));

        }

        return experiences;
    }

    private LocalDate getRandomBirthday() {
        return LocalDate.of(1950 + random.nextInt(52),
                Month.of(random.nextInt(11)+1),
                random.nextInt(28)+1);
    }

    private String getRandomPhoneNumber() {
        return "(07" +
                (random.nextInt(89) + 10) +
                ") " +
                (random.nextInt(899) + 100) +
                " " +
                (random.nextInt(899) + 100);

    }

    private TimeInterval getEducationPeriod(LocalDate start){
        int years = random.nextInt(2)+3;
        return TimeInterval.between(start, start.plusYears(years).minusMonths(3).plusDays(14));
    }

    private TimeInterval getExperiencePeriod(LocalDate start){
        int years = start.getYear()+random.nextInt(20) +1;
        int months = random.nextInt(6) + 6;
        int days = random.nextInt(27) + 1;
        return TimeInterval.between(start.plusMonths(3), LocalDate.of(years,months,days));
    }
}
