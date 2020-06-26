package MeetUpAPI.dbModels;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user", schema = "project24")
public class User {

    @Id
    @Column(name = "uid", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Length(min = 4, max = 45, message = "Required length: 4-45 characters")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Length(min = 4, max = 60, message = "Required length: 4-60 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "active", nullable = false)
    private int active = 1;

    @ManyToMany
    @JoinTable(
            name = "hobbyuser",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id"))

    private Set<Hobby> hobbySet;

    public int getActive() { return active; }

    public void setActive(int active) { this.active = active; }

    public Set<Hobby> getHobbySet() {
        return hobbySet;
    }

    public void setHobbySet(Set<Hobby> hobbySet) {
        this.hobbySet = hobbySet;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }


    public void setCountry(String country) {
        this.country = country;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id:" + id +
                ", username:'" + username + '\'' +
                ", password:'" + password + '\'' +
                ", firstname:'" + firstname + '\'' +
                ", lastname:'" + lastname + '\'' +
                ", phone:'" + phone + '\'' +
                ", country:'" + country + '\'' +
                ", city:'" + city + '\'' +
                ", hobbySet:'" + hobbySet + '\'' +
                '}';
    }
}
