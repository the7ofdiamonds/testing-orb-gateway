package tech.orbfin.api.gateway.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import tech.orbfin.api.gateway.payload.User;
import tech.orbfin.api.gateway.payload.UserRowMapper;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Repository
public class RepositoryUser {
    @Autowired
    public final NamedParameterJdbcTemplate jdbcTemplate;

    public User findUserByID(Integer id) {
        String sql = "CALL findUserByID(:p_id)";

        Map<String, Object> params = new HashMap<>();
        params.put("p_id", id);

        return jdbcTemplate.queryForObject(sql, params, new UserRowMapper());
    }

    public User findUserByEmail(String email) {
        String sql = "CALL findUserByEmail(:p_email)";

        Map<String, Object> params = new HashMap<>();
        params.put("p_email", email);

        return jdbcTemplate.queryForObject(sql, params, new UserRowMapper());
    }

    public User findUserByUsername(String username) {
        String sql = "CALL findUserByUsername(:p_username)";

        Map<String, Object> params = new HashMap<>();
        params.put("p_username", username);

        return jdbcTemplate.queryForObject(sql, params, new UserRowMapper());
    }
}
