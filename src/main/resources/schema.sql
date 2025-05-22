DROP TABLE IF EXISTS movie;
DROP TABLE IF EXISTS director;

CREATE TABLE director (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200),
    age INT
);

CREATE TABLE movie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    release_year INT,
    director_id INT,
    description VARCHAR(255),
    image_path VARCHAR(200),
    CONSTRAINT fk_director FOREIGN KEY (director_id) REFERENCES director(id)
);