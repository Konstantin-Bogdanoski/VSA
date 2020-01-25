package mk.ukim.finki.vsa.model;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.vsa.model.base.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
@Table(name = "vsa_video")
public class Video extends BaseEntity {
    private String key;
    private String name;
    private String fileName;
    private Double length;
    private String imdbLink;
    private Long upvotes;
    private Long downvotes;
    @ManyToMany(mappedBy = "videos")
    private List<Quality> qualities;

    public void setKey() {
        this.key = generateKey();
    }

    private String generateKey() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}