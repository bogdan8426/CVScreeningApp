package CVScreening.CVGenerator;

import CVScreening.model.CV;
import CVScreening.model.Education;
import CVScreening.model.Experience;
import CVScreening.model.PersonalInfo;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import static CVScreening.CVGenerator.Constant.*;

class CVWriter {

    private List<CV> cvs;

    public CVWriter(List<CV> cvs){
        this.cvs = cvs;
    }

    public void saveCVs(){
        for (CV cv: cvs) {
            try {
                String path = "C:\\Users\\Bogdan\\Desktop\\Facultate\\Java advanced\\CV Screening Application\\src\\CVScreening\\CVs\\"
                        + getFileName(cv);
                FileOutputStream cv_file = new FileOutputStream(path);

                XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
                XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(cv_file);
                XMLEventFactory eventFactory = XMLEventFactory.newInstance();
                XMLEvent end = eventFactory.createDTD("\n");

                // create and write Start Tag
                StartDocument startDocument = eventFactory.createStartDocument();
                eventWriter.add(startDocument);
                eventWriter.add(end);

                StartElement contactsStartElement = eventFactory.createStartElement("",
                        "", "cv");
                eventWriter.add(contactsStartElement);
                eventWriter.add(end);

                saveCV(eventWriter, eventFactory, cv);

                eventWriter.add(eventFactory.createEndElement("", "", "cv"));
                eventWriter.add(end);
                eventWriter.add(eventFactory.createEndDocument());
                eventWriter.close();
            } catch (FileNotFoundException | XMLStreamException e) {
                e.printStackTrace();
            }

        }
    }

    private void saveCV(XMLEventWriter eventWriter, XMLEventFactory eventFactory, CV cv)
            throws XMLStreamException {

        saveInfo(eventWriter,eventFactory,cv.getInfo());
        saveEducation(eventWriter,eventFactory, cv.getEducation());
        saveExperience(eventWriter,eventFactory, cv.getExperience());
    }

    private void saveExperience(XMLEventWriter eventWriter, XMLEventFactory eventFactory, List<Experience> experiences)
            throws XMLStreamException{
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        // create experience_section open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", EXPERIENCE_SECTION.toString());
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(configStartElement);
        eventWriter.add(end);

        // Write the different nodes
        for(Experience experience: experiences){
            // create experience open tag
            configStartElement = eventFactory.createStartElement("",
                    "", EXPERIENCE.toString());
            eventWriter.add(tab);
            eventWriter.add(tab);
            eventWriter.add(configStartElement);
            eventWriter.add(end);

            createNode(eventWriter, PERIOD.toString(), experience.getTimeInterval().toString());
            createNode(eventWriter, POSITION.toString(), experience.getPosition());
            createNode(eventWriter, DESCRIPTION.toString(), experience.getDescription());
            createNode(eventWriter, COMPANY.toString(), experience.getCompany());
            createNode(eventWriter, DOMAIN.toString(), experience.getDomain().toString());

            eventWriter.add(tab);
            eventWriter.add(tab);
            eventWriter.add(eventFactory.createEndElement("", "", EXPERIENCE.toString()));
            eventWriter.add(end);
        }

        eventWriter.add(eventFactory.createEndElement("", "", EXPERIENCE_SECTION.toString()));
        eventWriter.add(end);
    }

    private void saveEducation(XMLEventWriter eventWriter, XMLEventFactory eventFactory, List<Education> educations)
            throws XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        // create education_section open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", EDUCATION_SECTION.toString());
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(configStartElement);
        eventWriter.add(end);

        // Write the different nodes
        for(Education education: educations){
            // create education open tag
            configStartElement = eventFactory.createStartElement("",
                    "", EDUCATION.toString());
            eventWriter.add(tab);
            eventWriter.add(tab);
            eventWriter.add(configStartElement);
            eventWriter.add(end);

            createNode(eventWriter, PERIOD.toString(), education.getTimeInterval().toString());
            createNode(eventWriter, UNIVERSITY_NAME.toString(), education.getUniversityName());
            createNode(eventWriter, DOMAIN.toString(), education.getDomain().toString());

            eventWriter.add(tab);
            eventWriter.add(tab);
            eventWriter.add(eventFactory.createEndElement("", "", EDUCATION.toString()));
            eventWriter.add(end);
        }

        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", EDUCATION_SECTION.toString()));
        eventWriter.add(end);
    }

    private void saveInfo(XMLEventWriter eventWriter, XMLEventFactory eventFactory, PersonalInfo info)
            throws XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        // create info open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", INFO.toString());
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(configStartElement);
        eventWriter.add(end);

        // Write the different nodes
        createNode(eventWriter, FIRST_NAME.toString(), info.getFirstName());
        createNode(eventWriter, LAST_NAME.toString(), info.getLastName());
        createNode(eventWriter, PHONE_NUMBER.toString(), info.getPhoneNumber());
        createNode(eventWriter, BIRTHDAY.toString(), info.getBirthday().toString());
        createNode(eventWriter, SEX.toString(), info.getSex().toString());

        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", INFO.toString()));
        eventWriter.add(end);
    }

    private void createNode(XMLEventWriter eventWriter, String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t\t\t");

        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);

        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }

    private String getFileName(CV cv){
        return "CV_" + cv.getInfo().getFirstName() + "_" + cv.getInfo().getLastName() + ".xml";
    }
}
