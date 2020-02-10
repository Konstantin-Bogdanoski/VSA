package mk.ukim.finki.vsa.model.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    @PrePersist
    public void prePersist() {
        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}
