package mx.com.axity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "w_code_reset_token", schema = "public")
public class CodeResetDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "ds_token")
    private String token;

    public CodeResetDO() {}

    public CodeResetDO(long id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
