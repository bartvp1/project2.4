package MeetUpAPI.dto;

public class MatchDTO {

    private String naam;
    private String phone;
    private String city;
    private String country;

    public MatchDTO(String naam, String phone, String city, String country) {
        this.naam = naam;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

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
