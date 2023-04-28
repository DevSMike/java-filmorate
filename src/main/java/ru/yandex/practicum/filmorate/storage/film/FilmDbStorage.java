package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genres;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Primary
@Component
@RequiredArgsConstructor
@Slf4j
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(Film film) {
        String sql = "INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION ,RATING_ID )VALUES (?,?,?,?,?);";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        int filmId = jdbcTemplate.queryForObject("SELECT FILM_ID FROM  FILMS ORDER BY FILM_ID DESC LIMIT 1;", Integer.class);
        film.setId(filmId);
        if (film.getGenres().size() == 0) {
            return;
        }
        for (Genres genres : film.getGenres()) {
            jdbcTemplate.update("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES (?,?)", film.getId(), genres.getId());
        }
    }

    @Override
    public void update(Film film) {
        jdbcTemplate.update("UPDATE FILMS SET FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, " +
                        "RATING_ID  = ? WHERE FILM_ID = ?;", film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());
        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE FILM_ID  = ?;", film.getId());
        Optional<LinkedHashSet<Genres>> filmGenres = Optional.ofNullable(film.getGenres());
        if (filmGenres.isEmpty()) {
            return;
        }
        for (Genres genres : filmGenres.get()) {
            jdbcTemplate.update("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES (?, ?);", film.getId(), genres.getId());
        }
    }

    @Override
    public void delete(Film film) {
        jdbcTemplate.update("DELETE FROM FILMS where FILM_ID = ? ", film.getId());
    }

    @Override
    public List<Film> getFilmsList() {
        String sql = "SELECT f.FILM_ID, FILM_NAME,DESCRIPTION,RELEASE_DATE, DURATION,fr.RATING , fr.RATING_ID, " +
                "STRING_AGG(DISTINCT gi.GENRE_ID || '-' || gi.GENRE_NAME, ',') AS genre " +
                "FROM FILMS  AS f " +
                "LEFT JOIN FILM_RATING fr ON f.RATING_ID = fr.RATING_ID " +
                "LEFT JOIN FILM_GENRES fg ON f.FILM_ID = fg.FILM_ID " +
                "LEFT JOIN GENRE_INFO gi ON fg.GENRE_ID = gi.GENRE_ID " +
                "GROUP BY f.FILM_ID;";
        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs, sql)));
    }

    @Override
    public Map<Long, Film> getFilmsMap() {
        return getFilmsList().stream().collect(Collectors.toMap(Film::getId, film -> film));
    }

    @Override
    public List<Film> getTopLikesFilms(int count, int genreId, int year) {
        String sql = "SELECT f.FILM_ID, FILM_NAME,DESCRIPTION,RELEASE_DATE, DURATION,fr.RATING , fr.RATING_ID, STRING_AGG(DISTINCT gi.GENRE_ID || '-' || gi.GENRE_NAME, ',') AS genre,\n" +
                "STRING_AGG(DISTINCT fl.USER_ID, ',') AS user_likes, LENGTH (STRING_AGG(DISTINCT fl.USER_ID,'' )) AS likes_count\n" +
                "FROM FILMS  AS f\n" +
                "LEFT JOIN FILM_RATING fr ON f.RATING_ID = fr.RATING_ID \n" +
                "LEFT JOIN FILM_GENRES fg ON f.FILM_ID = fg.FILM_ID \n" +
                "LEFT JOIN GENRE_INFO gi ON fg.GENRE_ID = gi.GENRE_ID \n" +
                "LEFT JOIN FILM_LIKES fl ON f.FILM_ID = fl.FILM_ID\n";
        String topLimit = "GROUP BY f.FILM_ID ";
        List<Integer> keys = new LinkedList<>();
        if (genreId != 0) {
            topLimit += "HAVING GENRE LIKE CONCAT('%', ?, '%') ";
            keys.add(genreId);
        }
        if (genreId != 0 && year != 0) {
            topLimit += " AND EXTRACT(YEAR FROM f.RELEASE_DATE) = ? ";
            keys.add(year);
        }
        if (genreId == 0 && year != 0) {
            topLimit += " HAVING EXTRACT(YEAR FROM f.RELEASE_DATE) = ? ";
            keys.add(year);
        }
        topLimit += "ORDER BY likes_count DESC LIMIT ? ";
        keys.add(count);
        return jdbcTemplate.query(sql + topLimit, (rs, rowNum) -> makeFilm(rs, sql), keys.toArray());
    }

    @Override
    public Film getFilmById(long id) {
        String sql = "SELECT f.FILM_ID, FILM_NAME,DESCRIPTION,RELEASE_DATE, DURATION,fr.RATING , fr.RATING_ID, " +
                "STRING_AGG(DISTINCT gi.GENRE_ID || '-' || gi.GENRE_NAME, ',') AS genre " +
                "FROM FILMS  AS f " +
                "LEFT JOIN FILM_RATING fr ON f.RATING_ID = fr.RATING_ID " +
                "LEFT JOIN FILM_GENRES fg ON f.FILM_ID = fg.FILM_ID " +
                "LEFT JOIN GENRE_INFO gi ON fg.GENRE_ID = gi.GENRE_ID " +
                "WHERE f.FILM_ID = ? ;";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeFilm(rs, sql), id);
    }

    private Film makeFilm(ResultSet rs, String sql) throws SQLException {
        Optional<Array> genres = Optional.ofNullable(rs.getArray("genre"));
        Set<Long> setLikes = new HashSet<>();
        if (sql.contains("user_likes")) {
            Optional<Array> userLikes = Optional.ofNullable(rs.getArray("user_likes"));
            if (userLikes.isPresent()) {
                setLikes = Arrays.stream((Object[]) userLikes.get().getArray()).map(Object::toString)
                        .flatMap(Pattern.compile(",")::splitAsStream).map(Long::valueOf)
                        .collect(Collectors.toSet());
            }
        }
        LinkedHashSet<Genres> listOfGenres = new LinkedHashSet<>();
        if (genres.isPresent()) {
            listOfGenres = Arrays.stream((Object[]) genres.get().getArray())
                    .map(Object::toString).flatMap(Pattern.compile(",")::splitAsStream).map(x ->
                            new Genres(Integer.parseInt(x.split("-")[0]), x.split("-")[1]))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("film_name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .mpa(new Mpa(rs.getInt("rating_id"), rs.getString("rating")))
                .genres(listOfGenres)
                .likes(setLikes)
                .directors(new HashSet<>())
                .build();
    }
}
