package CVScreening.CVGenerator;

import CVScreening.DataModel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static CVScreening.CVGenerator.Constant.*;

public class CVReader extends CVReadWrite{

    private ObservableList<CV> cvs = FXCollections.observableArrayList();

    public ObservableList<CV> loadCVs() throws FileNotFoundException {
        // TODO remove cvs from this class and read all xml files in folder
        // Add threads to handle the read and write actions
        File cvFolder = new File("C:\\Users\\Bogdan\\Desktop\\Facultate\\Java advanced\\CV Screening Application\\src\\CVScreening\\CVs");
        if(cvFolder.list().length == 0){
            //TODO: Throw error!
            System.out.println("CVs folder is empty, generate the xml files first!");
            throw new RuntimeException();
        }else{
            System.out.println("Reading from CVs...");
            for(File file: cvFolder.listFiles())
                if (!file.isDirectory()) {
                    FileInputStream cv_file = new FileInputStream(file);
                    loadCV(cv_file);
                }
        }
        return cvs;
    }


    private void loadCV(FileInputStream in) {
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document

            //Initialize the variables
            CV cv;
            String firstName = "NA";
            String lastName = "NA";
            String phoneNumber = "NA";
            LocalDate birthday = LocalDate.now();
            Sex sex = null;
            PersonalInfo info = null;

            List<Education> educations = new ArrayList<>();
            TimeInterval period = null;
            String universityName = "NA";
            Domain domain = null;

            List<Experience> experiences = new ArrayList<>();
            String position = "NA";
            String description = "NA";
            String company = "NA";


            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();

                    if (    tagName.equals("cv") ||
                            tagName.equals(INFO.toString()) ||
                            tagName.equals(EDUCATION_SECTION.toString()) ||
                            tagName.equals(EDUCATION.toString()) ||
                            tagName.equals(EXPERIENCE_SECTION.toString()) ||
                            tagName.equals(EXPERIENCE.toString())) {
                        continue;
                    }

                    if(event.isStartElement()){
                        String localPart = event.asStartElement().getName().getLocalPart();
                        event = eventReader.nextEvent();
                        switch(Constant.valueOf(localPart.toUpperCase())){
                            case FIRST_NAME:
                                firstName = FIRST_NAME.parse(event);
                                break;
                            case LAST_NAME:
                                lastName = LAST_NAME.parse(event);
                                break;
                            case PHONE_NUMBER:
                                phoneNumber = PHONE_NUMBER.parse(event);
                                break;
                            case BIRTHDAY:
                                birthday = LocalDate.parse(BIRTHDAY.parse(event));
                                break;
                            case SEX:
                                sex = Sex.valueOf(SEX.parse(event));
                                break;
                            //education
                            case PERIOD:
                                period = TimeInterval.parse(PERIOD.parse(event));
                                break;
                            case UNIVERSITY_NAME:
                                universityName = UNIVERSITY_NAME.parse(event);
                                break;
                            case DOMAIN:
                                domain = Domain.valueOf(DOMAIN.parse(event));
                                break;
                            // experience
                            case POSITION:
                                position = POSITION.parse(event);
                                break;
                            case DESCRIPTION:
                                description = DESCRIPTION.parse(event);
                                break;
                            case COMPANY:
                                company = COMPANY.parse(event);
                                break;
                            default:
                                System.out.println("Something went wrong!");
                        }
                        continue;
                    }

                }

                // If we reach the end of a contact element, we add it to the list
                if (event.isEndElement()) {
                    String localPart = event.asEndElement().getName().getLocalPart();

                    if (localPart.equals(INFO.toString())) {
                        // create new info object
                        info = new PersonalInfo(firstName,lastName,phoneNumber,birthday,sex);
                    }

                    if (localPart.equals(EDUCATION.toString())) {
                        // add new education object
                        educations.add(new Education(period, universityName, domain));
                    }

                    if (localPart.equals(EXPERIENCE.toString())) {
                        // add new experience object
                        experiences.add(new Experience(period, position, description, company, domain));
                    }

                    if (localPart.equals("cv")) {
                        // create new cv object
                        cv = new CV(info, educations, experiences);
                        cvs.add(cv);
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
