package ru.yandex.practicum.filmorate.storage.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Genres;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenresDbStorage implements GenresStorage{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genres> getAllGenres() {
        String sql = "SELECT GI.GENRE_ID , GI.GENRE_NAME  FROM GENRE_INFO gi ORDER BY GI.GENRE_ID ;";
        return new ArrayList<>(jdbcTemplate.query(sql, (rs,rowNum) -> makeGenre(rs)));
    }

    @Override
    public Genres getGenreById(int id) {
        String sql = " SELECT GI.GENRE_ID , GI.GENRE_NAME  FROM GENRE_INFO gi WHERE GI.GENRE_ID = ?; ";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeGenre(rs), id);
    }

    private Genres makeGenre (ResultSet rs) throws SQLException {
        return Genres.builder()
                .id(rs.getInt("genre_id"))
                .name(rs.getString("genre_name"))
                .build();
    }
}
