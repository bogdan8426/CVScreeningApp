package CVScreening.CVGenerator;

import CVScreening.exceptions.CVFilesReadException;
import CVScreening.model.CV;
import CVScreening.model.Education;
import CVScreening.model.Experience;
import CVScreening.model.PersonalInfo;
import CVScreening.model.helpers.Domain;
import CVScreening.model.helpers.Sex;
import CVScreening.model.helpers.TimeInterval;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static CVScreening.CVGenerator.Constant.*;

class CVReader {

    private ObservableList<CV> cvs = FXCollections.observableArrayList();
    private Logger logger = Logger.getLogger(CVGenerator.class.getName());

    public ObservableList<CV> loadCVs() throws CVFilesReadException, FileNotFoundException {
        File cvFolder = new File("C:\\Users\\Bogdan\\Desktop\\Facultate\\Java advanced\\CV Screening Application\\src\\CVScreening\\CVs");
        File[] files = cvFolder.listFiles();
        if (files == null) {
            throw new CVFilesReadException("The CVs directory is empty!");
        } else {
            logger.info("Reading files from CVs directory...");
            for (File file : files)
                if (!file.isDirectory()) {
                    cvs.add(loadCV(new FileInputStream(file)));
                }
        }
        return cvs;
    }

    private CV loadCV(FileInputStream in) throws CVFilesReadException {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            PersonalInfo info = new PersonalInfo();
            List<Education> educations = new LinkedList<>();
            List<Experience> experiences = new LinkedList<>();

            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    String tagName = event.asStartElement().getName().getLocalPart();
                    if (tagName.equals(INFO.toString())) {
                        info = loadInfo(eventReader);
                    }
                    if (tagName.equals(EDUCATION.toString())) {
                        educations.add(loadEducation(eventReader));
                    }
                    if (tagName.equals(EXPERIENCE.toString())) {
                        experiences.add(loadExperience(eventReader));
                    }
                }
                // If we reach the end of a cv element, we add it to the list
                if (event.isEndElement()) {
                    String tagName = event.asEndElement().getName().getLocalPart();
                    if (tagName.equals("cv")) {
                        return new CV(info, educations, experiences);
                    }
                }
            }
            throw new CVFilesReadException("XML file is empty!");
        } catch (XMLStreamException e) {
            throw new CVFilesReadException("Problem reading xml file: ", e);
        }
    }

    private PersonalInfo loadInfo(XMLEventReader eventReader) throws XMLStreamException, CVFilesReadException {
        PersonalInfo info = new PersonalInfo();
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            String tagName;

            if (event.isStartElement()) {
                tagName = event.asStartElement().getName().getLocalPart();
                event = eventReader.nextEvent();
                switch (Constant.valueOf(tagName.toUpperCase())) {
                    case FIRST_NAME:
                        info.setFirstName(FIRST_NAME.parse(event));
                        break;
                    case LAST_NAME:
                        info.setLastName(LAST_NAME.parse(event));
                        break;
                    case PHONE_NUMBER:
                        info.setPhoneNumber(PHONE_NUMBER.parse(event));
                        break;
                    case BIRTHDAY:
                        info.setBirthday(LocalDate.parse(BIRTHDAY.parse(event)));
                        break;
                    case SEX:
                        info.setSex(Sex.valueOf(SEX.parse(event)));
                        break;
                    default:
                        logger.warning("The xml file contains unrecognised tags in the info section, those will be ignored!");
                }
            }

            if (event.isEndElement()) {
                tagName = event.asEndElement().getName().getLocalPart();
                if (tagName.equals(INFO.toString())) {
                    return info;
                }
            }
        }
        throw new CVFilesReadException("Could not read education section!");
    }

    private Education loadEducation(XMLEventReader eventReader) throws XMLStreamException, CVFilesReadException {
        Education education = new Education();
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            String tagName;

            if (event.isStartElement()) {
                tagName = event.asStartElement().getName().getLocalPart();
                event = eventReader.nextEvent();
                switch (Constant.valueOf(tagName.toUpperCase())) {
                    case PERIOD:
                        education.setTimeInterval(TimeInterval.parse(PERIOD.parse(event)));
                        break;
                    case UNIVERSITY_NAME:
                        education.setUniversityName(UNIVERSITY_NAME.parse(event));
                        break;
                    case DOMAIN:
                        education.setDomain(Domain.valueOf(DOMAIN.parse(event)));
                        break;
                    default:
                        logger.warning("The xml file contains unrecognised tags in the education section, those will be ignored!");
                }
            }

            if (event.isEndElement()) {
                tagName = event.asEndElement().getName().getLocalPart();
                if (tagName.equals(EDUCATION.toString())) {
                    return education;
                }
            }
        }
        throw new CVFilesReadException("Could not read education section!");
    }

    private Experience loadExperience(XMLEventReader eventReader) throws XMLStreamException, CVFilesReadException {
        Experience experience = new Experience();
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            String tagName;

            if (event.isStartElement()) {
                tagName = event.asStartElement().getName().getLocalPart();
                event = eventReader.nextEvent();
                switch (Constant.valueOf(tagName.toUpperCase())) {
                    case PERIOD:
                        experience.setTimeInterval(TimeInterval.parse(PERIOD.parse(event)));
                        break;
                    case DOMAIN:
                        experience.setDomain(Domain.valueOf(DOMAIN.parse(event)));
                        break;
                    case POSITION:
                        experience.setPosition(POSITION.parse(event));
                        break;
                    case DESCRIPTION:
                        experience.setDescription(DESCRIPTION.parse(event));
                        break;
                    case COMPANY:
                        experience.setCompany(COMPANY.parse(event));
                        break;
                    default:
                        logger.warning("The xml file contains unrecognised tags in the experience section, those will be ignored!");
                }
            }

            if (event.isEndElement()) {
                tagName = event.asEndElement().getName().getLocalPart();
                if (tagName.equals(EXPERIENCE.toString())) {
                    return experience;
                }
            }
        }
        throw new CVFilesReadException("Could not read education section!");
    }

}

