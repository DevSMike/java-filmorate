package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAllMpa() {
        String sql = "SELECT FR.RATING_ID, FR.RATING FROM FILM_RATING fr ;";
        return new ArrayList<>(jdbcTemplate.query(sql, (rs,rowNum) -> makeMpa(rs)));
    }

    @Override
    public Mpa getMpaById(int id) {
        String sql = "SELECT FR.RATING_ID, FR.RATING FROM FILM_RATING fr WHERE FR.RATING_ID = ?;";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeMpa(rs), id);
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        return Mpa.builder()
                .id(rs.getInt("rating_id"))
                .name(rs.getString("rating"))
                .build();
    }
}
