package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity(name="user_session")
@NamedQueries({
        @NamedQuery(name="findUserBySessionId", query="SELECT u FROM user_session u WHERE u.sessionId = :sessionId")
})
public class UserSession implements Serializable {
    @Id
    @Column(name="sessionId", nullable=false, length=32)
    private String sessionId;

    @Column(name="name", nullable=false, length = 30)
    private String name;

    public UserSession() {}
    public UserSession(String sessionId, String name) {
        this.name = name;
        this.sessionId = sessionId;
    }
    public String getName() {
        return name;
    }
    public void setName(String email) {
        this.name = email;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}