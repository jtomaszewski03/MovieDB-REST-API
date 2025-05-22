package pjatk.tpo6_tj_s31585.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pjatk.tpo6_tj_s31585.Model.Director;
import pjatk.tpo6_tj_s31585.Model.Movie;
import pjatk.tpo6_tj_s31585.Service.DatabaseService;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final DatabaseService dbService;

    public DataController(DatabaseService dbService) {
        this.dbService = dbService;
    }

    @GetMapping
    public List<Movie> getFilms(@RequestParam(required = false) String sort) {
        return dbService.getMovies(sort);
    }

    @GetMapping("/directors")
    public List<Director> getDirectors() {
        return dbService.getAllDirectors();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable int id) {
        return dbService.deleteMovie(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addFilm(@RequestParam String title,
                                          @RequestParam int releaseYear,
                                          @RequestParam String directorName,
                                          @RequestParam String description,
                                          @RequestParam(required = false) MultipartFile image) {
        return dbService.addMovie(title, releaseYear, directorName, description, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> editFilm(@PathVariable int id,
                                           @RequestParam String title,
                                           @RequestParam int releaseYear,
                                           @RequestParam String directorName,
                                           @RequestParam String description,
                                           @RequestParam(required = false) MultipartFile image) {
        return dbService.updateMovie(id, title, releaseYear, directorName, description, image);
    }
}


