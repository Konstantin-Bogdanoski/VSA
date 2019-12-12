package mk.ukim.finki.vsa.model;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.vsa.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.File;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
@Table(name = "vsa_video")
public class Video extends BaseEntity {
    private String key;
    private File video;
    private Double length;
}
