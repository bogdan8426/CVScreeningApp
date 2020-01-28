package CVScreening.CVGenerator.root;

import CVScreening.DataModel.Domain;
import CVScreening.DataModel.Sex;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InputReader {

    private Map<String,Sex> names = new LinkedHashMap<>();
    private String path = "C:\\Users\\Bogdan\\Desktop\\Facultate\\Java advanced\\CV Screening Application\\src\\CVScreening\\CVGenerator\\root\\";
    private List<String> universities = new LinkedList<>();
    private Map<Domain, Map<String, String>> jobs = new LinkedHashMap<>();
    private List<String> companies = new LinkedList<>();

    public List<String> getCompanies() {
        try (Scanner companiesFile = new Scanner(new FileReader(path + "companies.txt"))) {
            while(companiesFile.hasNextLine()) {
                companies.add(companiesFile.nextLine());
            }
        } catch (IOException e) {
            //TODO: Throw fileReadException or something!
            e.printStackTrace();
        }
        // cleanup
        companies.removeAll(Collections.singleton(null));
        companies.removeAll(Collections.singleton(""));

        return companies;
    }

    public Map<Domain, Map<String, String>> getJobs() {
        List<String> list = new LinkedList<>();
        try (Scanner jobsFile = new Scanner(new FileReader(path + "jobs.txt"))) {
            while(jobsFile.hasNextLine()) {
                list.add(jobsFile.nextLine());
            }
        } catch (IOException e) {
            //TODO: Throw fileReadException or something!
            e.printStackTrace();
        }

        Map<String, String> positionDescription = new LinkedHashMap<>();
        for(int i = 0; i < list.size(); i++){
            String line = list.get(i);
            String[] values = line.split("\t");
            values[0] = values[0].replaceAll("[^\\p{Graph}\n\r\t ]", "");
            Domain domain = Domain.valueOf(values[0]);
            positionDescription.put(values[1],values[2]);
            if(i != 0){
                Domain oldDomain = Domain.valueOf(list.get(i-1).split("\t")[0].replaceAll("[^\\p{Graph}\n\r\t ]", ""));
                if(!domain.equals(oldDomain)){
                    positionDescription.remove(values[1]);
                    jobs.put(oldDomain, positionDescription);
                    positionDescription.put(values[1],values[2]);
                    positionDescription = new LinkedHashMap<>();
                }
            }
            if(i == list.size()-1){
                jobs.put(domain,positionDescription);
            }
        }
        return jobs;
    }

    public Map<String, Sex> getNames(){
        try (Scanner namesFile = new Scanner(new FileReader(path + "names.txt"))) {
            while(namesFile.hasNextLine()) {
                String line = namesFile.nextLine();
                String[] values = line.split(",");
                names.put(values[0] + "," + values [1], Sex.valueOf(values[2]));
            }
        } catch (IOException e) {
            //TODO: Throw fileReadException or something!
            e.printStackTrace();
        }
        return names;
    }

    public List<String> getUniversities(){
        try (Scanner univFile = new Scanner(new FileReader(path + "universities.txt"))) {
            while(univFile.hasNextLine()) {
                universities.add(univFile.nextLine());
            }
        } catch (IOException e) {
            //TODO: Throw fileReadException or something!
            e.printStackTrace();
        }
        return universities;
    }


    //    public static void main(String[] args) throws IOException {
////
//        Random random = new Random();
//        try(FileWriter names = new FileWriter(path + "names.txt")){
//            // write females
//            for(int i = 0; i<500; i++) {
//                names.write(firstNames.get(random.nextInt(100)));
//                names.write(",");
//                names.write(lastNames.get(random.nextInt(lastNames.size() - 1)));
//                names.write(",");
//                names.write(Sex.FEMALE.toString());
//                names.write("\n");
//            }
//            //write males
//            for(int i = 0; i<500; i++) {
//                names.write(firstNames.get(random.nextInt(99) + 100));
//                names.write(",");
//                names.write(lastNames.get(random.nextInt(lastNames.size() - 1)));
//                names.write(",");
//                names.write(Sex.MALE.toString());
//                names.write("\n");
//            }
//            //write unspecifiedSex
//            for(int i = 0; i<500; i++) {
//                names.write(firstNames.get(random.nextInt(firstNames.size()-1)));
//                names.write(",");
//                names.write(lastNames.get(random.nextInt(lastNames.size() - 1)));
//                names.write(",");
//                names.write(Sex.NOT_SPECIFIED.toString());
//                names.write("\n");
//            }
//        }
//    }

}
