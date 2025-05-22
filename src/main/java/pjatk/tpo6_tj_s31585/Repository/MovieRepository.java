package pjatk.tpo6_tj_s31585.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pjatk.tpo6_tj_s31585.Model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
