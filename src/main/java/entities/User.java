package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@NamedQueries({
        @NamedQuery(name="findUserById", query="SELECT u FROM users u WHERE u.name = :name")
})
@Entity(name="users")
public class User implements Serializable {
    @Id
    @Column(name="name", length=30)
    private String name;

    @Column(name="password", nullable=false, length=64)
    private String password;

    public User(){}

    public User(String name, String password) {
        this.password = password;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

