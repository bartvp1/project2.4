package MeetUpAPI.dto;

public class UserResponseDTO {
  private int uid;
  private String username;
  private String firstname;
  private String lastname;
  private String phone;
  private String country;
  private String city;


  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
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
            '}';
  }
}
