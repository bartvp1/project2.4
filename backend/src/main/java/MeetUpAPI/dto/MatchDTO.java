package MeetUpAPI.dto;

import MeetUpAPI.dbModels.Hobby;

import java.util.Set;

public class MatchDTO {

    private String naam;
    private String phone;
    private String city;
    private String country;
    private Set<Hobby> hobbySet;
    private Set<Hobby> sameHobbies;


    public MatchDTO(String naam, String phone, String city, String country,Set<Hobby> hobbies,Set<Hobby> sameHobbies) {
        this.naam = naam;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.hobbySet = hobbies;
        this.sameHobbies = sameHobbies;
    }

    public void setSameHobbies(Set<Hobby> sameHobbies) { this.sameHobbies = sameHobbies; }

    public Set<Hobby> getSameHobbies() { return sameHobbies; }

    public Set<Hobby> getHobbySet() { return hobbySet; }

    public void setHobbySet(Set<Hobby> hobbySet) { this.hobbySet = hobbySet; }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
