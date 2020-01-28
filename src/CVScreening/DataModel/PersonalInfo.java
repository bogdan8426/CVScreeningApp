package CVScreening.DataModel;

import java.time.LocalDate;

public class PersonalInfo {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
    private Sex sex;


    public PersonalInfo() {
    }

    public PersonalInfo(String firstName, String lastName, String phoneNumber, LocalDate birthday, Sex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.sex = sex;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }


    @Override
    public String toString() {
        return  "\n\t" + firstName  + " " + lastName  +
                "\n\t" + phoneNumber +
                "\n\tBorn: " + birthday +
                "\n\tSex: " + sex;
    }
}
