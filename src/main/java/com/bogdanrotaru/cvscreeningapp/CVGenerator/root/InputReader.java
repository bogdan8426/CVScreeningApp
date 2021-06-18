package com.bogdanrotaru.cvscreeningapp.CVGenerator.root;

import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Sex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.*;

@Slf4j
public class InputReader {

    private Map<String, Sex> names = new LinkedHashMap<>();
    private String path = "D:\\Personal projects\\CV Screening Application\\src\\main\\java\\com\\bogdanrotaru\\cvscreeningapp\\CVGenerator\\root\\";
    private List<String> universities = new LinkedList<>();
    private Map<Domain, Map<String, String>> jobs = new LinkedHashMap<>();
    private List<String> companies = new LinkedList<>();

    public List<String> getCompanies() throws FileNotFoundException {
        try (Scanner companiesFile = new Scanner(new BufferedInputStream(new FileInputStream(path + "companies.txt")))) {
            while (companiesFile.hasNextLine()) {
                String line = companiesFile.nextLine();
                if (ObjectUtils.isEmpty(line)) continue;
                companies.add(line);
            }
        }

        // cleanup
        companies.removeAll(Collections.singleton(null));
        companies.removeAll(Collections.singleton(""));

        return companies;
    }

    public Map<Domain, Map<String, String>> getJobs() throws FileNotFoundException {

        List<String> list = new LinkedList<>();
        try (Scanner jobsFile = new Scanner(new BufferedInputStream(new FileInputStream(path + "jobs.txt")))) {
            while (jobsFile.hasNextLine()) {
                list.add(jobsFile.nextLine());
            }
        }

        Map<String, String> positionDescription = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            String[] values = line.split("\t");
            values[0] = values[0].replaceAll("[^\\p{Graph}\n\r\t ]", "");
            Domain domain = Domain.valueOf(values[0]);
            positionDescription.put(values[1], values[2]);
            if (i != 0) {
                Domain oldDomain = Domain.valueOf(list.get(i - 1).split("\t")[0].replaceAll("[^\\p{Graph}\n\r\t ]", ""));
                if (!domain.equals(oldDomain)) {
                    positionDescription.remove(values[1]);
                    jobs.put(oldDomain, positionDescription);
                    positionDescription.put(values[1], values[2]);
                    positionDescription = new LinkedHashMap<>();
                }
            }
            if (i == list.size() - 1) {
                jobs.put(domain, positionDescription);
            }
        }
        return jobs;
    }

    public Map<String, Sex> getNames() throws IOException {
        try (Scanner namesFile = new Scanner(new BufferedInputStream(new FileInputStream(path + "names.txt")));
             BufferedWriter writer = new BufferedWriter(new FileWriter(path + "better-names.txt"))) {
            while (namesFile.hasNextLine()) {
                String line = namesFile.nextLine();
                String[] values = line.split(",");
                if (values.length != 3) {
                    log.warn("Error reading: " + Arrays.toString(values));

                    continue;
                }
                try {
                    Sex sex = Sex.valueOf(values[2]);
                    names.put(values[0] + "," + values[1], sex);
                    writer.write(line + System.getProperty("line.separator"));
                } catch (Exception e) {
                    log.warn("Error reading : " + e.getMessage());
                }
            }
        }
        return names;
    }

    public List<String> getUniversities() throws FileNotFoundException {
        try (Scanner univFile = new Scanner(new BufferedInputStream(new FileInputStream(path + "universities.txt")))) {
            while (univFile.hasNextLine()) {
                universities.add(univFile.nextLine());
            }
        }
        return universities;
    }

}
