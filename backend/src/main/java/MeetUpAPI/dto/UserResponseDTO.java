package MeetUpAPI.dto;

import MeetUpAPI.dbModels.Hobby;
import java.util.Set;

public class UserResponseDTO {
  private int id;
  private String username;
  private String firstname;
  private String lastname;
  private String phone;
  private String country;
  private String city;
  private Set<Hobby> hobbySet;
  private int active;

  public int getActive() { return active; }

  public void setActive(int active) { this.active = active; }

  public int getId() {
    return id;
  }

  public void setId(int uid) {
    this.id = uid;
  }

  public Set<Hobby> getHobbySet() {
    return hobbySet;
  }

  public void setHobbySet(Set<Hobby> hobbySet) {
    this.hobbySet = hobbySet;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return "UserResponseDTO{" +
            "username:'" + username + '\'' +
            ", firstname:'" + firstname + '\'' +
            ", lastname:'" + lastname + '\'' +
            ", phone:'" + phone + '\'' +
            ", country:'" + country + '\'' +
            ", city:'" + city + '\'' +
            ", hobbySet:'" + hobbySet + '\'' +
            '}';
  }
}
