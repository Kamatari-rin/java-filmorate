DELETE FROM MPA_RATINGS;
INSERT INTO MPA_RATINGS (RATING_ID, RATING_NAME)
VALUES (1, 'G'),
	 (2, 'PG'),
	 (3, 'PG-13'),
	 (4, 'R'),
	 (5, 'NC-17');

DELETE FROM GENRES;
INSERT INTO GENRES (GENRE_ID, GENRE_NAME)
VALUES (1, 'Комедия'),
(2, 'Драма'),
(3, 'Мультфильм'),
(4, 'Триллер'),
(5, 'Документальный'),
(6, 'Боевик');