package mk.ukim.finki.vsa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.vsa.model.base.BaseEntity;
import mk.ukim.finki.vsa.model.base.UserRole;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Getter
@Setter
@Entity
@Table(name = "vsa_user")
public class User extends BaseEntity {
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String name;
    @ManyToOne
    private UserRole userRole;
}
