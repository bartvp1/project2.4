package MeetUpAPI.dbModels;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "hobby", schema = "project24")
public class Hobby {

    @Id
    @Column(name = "hid", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    //@ManyToMany
    //Set<User> userSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}