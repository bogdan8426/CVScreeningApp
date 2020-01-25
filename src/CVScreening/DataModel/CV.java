package CVScreening.DataModel;

import java.util.List;
import java.util.Objects;

public class CV {

    private PersonalInfo info;
    private List<Education> education;
    private List<Experience> experience;

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
        return "\n======================================================================\n"+
                "Curriculum Vitae for " + info.getFirstName() +" " + info.getLastName()+
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
}
