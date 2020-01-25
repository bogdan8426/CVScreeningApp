package CVScreening.DataModel;

import java.time.LocalDate;
import java.util.Objects;

public class PersonalInfo {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
    private Sex sex;
    private final String ID;

    public PersonalInfo(String firstName, String lastName, String phoneNumber, LocalDate birthday, Sex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.sex = sex;
        ID = sex.toID()+""+Math.abs(lastName.hashCode()+birthday.hashCode());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Sex getSex() {
        return sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalInfo)) return false;
        PersonalInfo that = (PersonalInfo) o;
        return ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return  "\n\t" + firstName  + " " + lastName  +
                "\n\t" + phoneNumber +
                "\n\tBorn: " + birthday +
                "\n\tSex: " + sex +
                "\n\tID = " + ID;
    }
}
