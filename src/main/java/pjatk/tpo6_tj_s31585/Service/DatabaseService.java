package pjatk.tpo6_tj_s31585.Service;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pjatk.tpo6_tj_s31585.Model.Director;
import pjatk.tpo6_tj_s31585.Model.Movie;
import pjatk.tpo6_tj_s31585.Repository.DirectorRepository;
import pjatk.tpo6_tj_s31585.Repository.MovieRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DatabaseService {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;

    public DatabaseService(MovieRepository movieRepository, DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
    }

    public List<Movie> getMovies(String sort) {
        if (sort == null || sort.isEmpty()) return movieRepository.findAll();
        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return movieRepository.findAll(Sort.by(direction, "title"));
    }

    public List<Director> getAllDirectors() {
        return directorRepository.findAll(Sort.by("name"));
    }

    public ResponseEntity<Void> deleteMovie(int id) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> updateMovie(int id, String title, int releaseYear,
                                              String directorName, String description,
                                              MultipartFile image) {
        String validationError = validateInput(title, directorName, description, image);
        if (validationError != null) return ResponseEntity.badRequest().body(validationError);

        Optional<Movie> filmOptional = movieRepository.findById(id);
        if (filmOptional.isPresent()) {
            Movie movie = filmOptional.get();
            Director director = getOrCreateDirector(directorName);
            movie.setTitle(title);
            movie.setReleaseYear(releaseYear);
            movie.setDirector(director);
            movie.setDescription(description);
            try {
                if (image != null) {
                    String filename = saveImage(image);
                    movie.setImagePath(filename);
                }
            } catch (IOException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

            movieRepository.save(movie);
            return ResponseEntity.ok("Zaktualizowano film");
        }
        return addMovie(title, releaseYear, directorName, description, image);
    }

    public ResponseEntity<String> addMovie(String title, int releaseYear,
                                           String directorName, String description,
                                           MultipartFile image) {
        String validationError = validateInput(title, directorName, description, image);
        if (validationError != null) return ResponseEntity.badRequest().body(validationError);

        Director director = getOrCreateDirector(directorName);
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setDirector(director);
        movie.setDescription(description);
        try {
            if (image != null) {
                String filename = saveImage(image);
                movie.setImagePath(filename);
            } else {
                movie.setImagePath("emptydefault.jpg");
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        movieRepository.save(movie);
        return ResponseEntity.ok("Film dodany pomyślnie");
    }

    private Director getOrCreateDirector(String name) {
        return directorRepository.findByName(name)
                .orElseGet(() -> {
                    Director d = new Director();
                    d.setName(name);
                    return directorRepository.save(d);
                });
    }

    private String saveImage(MultipartFile image) throws IOException {
        String filename = image.getOriginalFilename();
        Path path = Paths.get("images", filename);
        Files.write(path, image.getBytes());
        return filename;
    }

    private String validateInput(String title, String directorName, String description, MultipartFile image) {
        if (title.length() > 200) return "Tytuł nie może przekraczać 200 znaków";
        if (directorName.length() > 200) return "Imię reżysera nie przekraczać 200 znaków";
        if (description.length() > 255) return "Opis nie może przekraczać 255 znaków";
        if (image != null && Objects.requireNonNull(image.getOriginalFilename()).length() > 200)
            return "Nazwa pliku nie może przekraczać 200 znaków";
        return null;
    }
}
