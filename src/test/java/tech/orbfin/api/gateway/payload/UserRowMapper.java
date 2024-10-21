package tech.orbfin.api.gateway.payload;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = User.builder()
            .id(rs.getInt("id"))
            .email(rs.getString("email"))
            .username(rs.getString("username"))
            .password(rs.getString("password"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .phone(rs.getString("phone"))
            .providerGivenID(rs.getString("provider_given_id"))
            .userActivationKey(rs.getString("user_activation_key"))
            .confirmationCode(rs.getString("confirmation_code"))
            .roles(rs.getString("roles"))
            .isAuthenticated(rs.getBoolean("is_authenticated"))
            .isAccountNonExpired(rs.getBoolean("is_account_non_expired"))
            .isAccountNonLocked(rs.getBoolean("is_account_non_locked"))
            .isCredentialsNonExpired(rs.getBoolean("is_credentials_non_expired"))
            .isEnabled(rs.getBoolean("is_enabled"))
            .build();

        return user;
    }
}
