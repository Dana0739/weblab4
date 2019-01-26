package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="user_groups")
public class Group implements Serializable {
    public static final String USERS_GROUP = "authorised";
    @Id
    @Column(name="name", length=30)
    private String name;

    @Column(name="groupname", nullable=false, length=32)
    private String groupname;

    public Group() {}
    public Group(String name, String groupname) {
        this.name = name;
        this.groupname = groupname;
    }
    public String getName() {
        return name;
    }
    public void setName(String email) {
        this.name = email;
    }
    public String getGroupname() {
        return groupname;
    }
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}