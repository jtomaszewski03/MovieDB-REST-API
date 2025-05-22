package pjatk.tpo6_tj_s31585.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int releaseYear;
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;
    private String description;
    private String imagePath;
}
