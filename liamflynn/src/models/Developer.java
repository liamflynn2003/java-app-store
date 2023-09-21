package models;

import java.util.Objects;

public class Developer {

    private String developerName = "<no developer>";
    private String developerWebsite = "<no website>";

    public Developer(String developerName, String developerWebsite) {
        this.developerName = developerName;
        this.developerWebsite = developerWebsite;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getDeveloperWebsite() {
        return developerWebsite;
    }

    public void setDeveloperWebsite(String developerWebsite) {
        this.developerWebsite = developerWebsite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Objects.equals(developerName, developer.developerName) && Objects.equals(developerWebsite, developer.developerWebsite);
    }

    @Override
    public String toString() {
        return developerName + "(" + developerWebsite + ")";
    }

}