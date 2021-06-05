package com.bogdanrotaru.cvscreeningapp.model;


import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.TimeInterval;

public class Experience {

    private TimeInterval timeInterval;
    private String position;
    private String description;
    private String company;
    private Domain domain;

    public Experience(TimeInterval timeInterval, String position, String description, String company, Domain domain) {
        this.timeInterval = timeInterval;
        this.position = position;
        this.description = description;
        this.company = company;
        this.domain = domain;
    }

    public Experience() {

    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany() {
        return company;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "\n\n\t" + timeInterval +
                "\n\t" + position +
                "\n\t\t" + description +
                "\n\t" + company +
                "\n\t" + domain;
    }
}
