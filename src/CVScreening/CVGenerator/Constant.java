package CVScreening.CVGenerator;

import javax.xml.stream.events.XMLEvent;

enum Constant {
    INFO, FIRST_NAME, LAST_NAME, PHONE_NUMBER, BIRTHDAY, SEX,
    EDUCATION_SECTION, EDUCATION, PERIOD, UNIVERSITY_NAME, DOMAIN,
    EXPERIENCE_SECTION, EXPERIENCE, POSITION, DESCRIPTION, COMPANY;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    String parse(XMLEvent event) {
        return event.asCharacters().getData();
    }

}
