-- Reżyserzy
INSERT INTO director (name, age) VALUES ('Christopher Nolan', 53);
INSERT INTO director (name, age) VALUES ('Steven Spielberg', 77);
INSERT INTO director (name, age) VALUES ('Quentin Tarantino', 61);

-- Filmy
INSERT INTO movie (title, release_year, director_id, description, image_path) VALUES ('Incepcja', 2010, 1, 'Czasy, gdy technologia pozwala na wchodzenie w świat snów. Złodziej Cobb ma za zadanie wszczepić myśl do śpiącego umysłu.', 'inception.jpg');
INSERT INTO movie (title, release_year, director_id, description, image_path) VALUES ('Pulp Fiction', 1994, 3, 'Historie kryminalne przeplatane ze sobą w niechronologicznej kolejności. Film pełny kultowych scen i dialogów, który zrewolucjonizował kino.', 'pulp_fiction.jpg');
INSERT INTO movie (title, release_year, director_id, description, image_path) VALUES ('Interstellar', 2014, 1, 'Byt ludzkości na Ziemi dobiega końca wskutek zmian klimatycznych. Grupa naukowców odkrywa tunel czasoprzestrzenny, który umożliwia poszukiwanie nowego domu.', 'interstellar.jpg');
INSERT INTO movie (title, release_year, director_id, description, image_path) VALUES ('Jurassic Park', 1993, 2, 'Naukowcy tworzą park rozrywki z klonowanymi dinozaurami. Gdy systemy bezpieczeństwa zawiodą, goście muszą walczyć o przetrwanie.', 'jurassic_park.jpg');
INSERT INTO movie (title, release_year, director_id, description, image_path) VALUES ('Django', 2012, 3, 'Niewolnik Django zostaje uwolniony przez łowcę nagród. Razem wyruszają w podróż, by odnaleźć żonę Django i pomścić krzywdy.', 'django_unchained.jpg');
INSERT INTO movie (title, release_year, director_id, description, image_path) VALUES ('Mroczny Rycerz', 2008, 1, 'Batman stawia czoła Jokerowi, psychopatycznemu przestępcy, który chce pogrążyć Gotham City w chaosie.', 'dark_knight.jpg');
INSERT INTO movie (title, release_year, director_id, description, image_path) VALUES ('Lista Schindlera', 1993, 2, 'Historia Oskara Schindlera, niemieckiego przedsiębiorcy, który podczas II wojny światowej uratował ponad tysiąc Żydów.', 'schindlers_list.jpg');
INSERT INTO movie (title, release_year, director_id, description, image_path) VALUES ('Bękarty wojny', 2009, 3, 'Alternatywna historia II wojny światowej, w której grupa żydowskich żołnierzy planuje zamach na nazistowskich przywódców.', 'inglourious_basterds.jpg');
