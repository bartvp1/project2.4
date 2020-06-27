package MeetUpAPI.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistrationDTO {
    @Size(min = 3, max = 20, message = "Username must be 3-20 characters")
    private String username;

    @Size(min = 6, max = 20, message = "Password must be 6-20 characters")
    private String password;

    @Size(min = 2, max = 20, message = "First name must be 2-20 characters")
    private String firstname;

    @Size(min = 2, max = 20, message = "Last name must be 2-20 characters")
    private String lastname;

    @Size(min = 3, max = 20, message = "Phone number format: 31612345678")
    private String phone;

    @NotBlank(message = "Please specify the country you're living in")
    private String country;

    @NotBlank(message = "Please specify the city you're living in")
    private String city;

    private int active = 1;

    public int getActive() { return active; }

    public void setActive(int active) { this.active = active; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "UserRegistrationDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
