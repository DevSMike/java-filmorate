--добавляем инфу по рейтингам
MERGE INTO FILM_RATING (RATING_ID ,RATING) VALUES (1, 'G'), (2, 'PG'), (3, 'PG-13'), (4, 'R'), (5, 'NC-17');
--добавляем инфу по жанрам
MERGE INTO GENRE_INFO (GENRE_ID, GENRE_NAME) VALUES (1, 'Комедия'), (2, 'Драма'), (3, 'Мультфильм'), (4, 'Триллер'),
(5, 'Документальный'), (6, 'Боевик');