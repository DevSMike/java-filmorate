package ru.yandex.practicum.filmorate.storage.film.likes;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilmDbLikes implements FilmLikes {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLikeToFilm(long filmId, long userId) {
        String sql = "INSERT INTO FILM_LIKES (USER_ID, FILM_ID) VALUES (?,?);";
        jdbcTemplate.update(sql,userId, filmId);
    }

    @Override
    public void deleteFilmLike(long filmId, long userId) {
        String sql = "DELETE FROM FILM_LIKES WHERE USER_ID = ? AND FILM_ID = ?;";
        jdbcTemplate.update(sql, userId, filmId);
    }
}
