package tech.orbfin.api.gateway.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
public class User {
    @Id
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
//    Separate below from wordpress
    @Column(name = "provider_given_id")
    private String providerGivenID;
    @Column(name = "user_activation_key")
    private String userActivationKey;
    @Column(name = "confirmation_code")
    private String confirmationCode;
    private String roles;
    @Column(name = "is_authenticated")
    private Boolean isAuthenticated;
    @Column(name = "is_account_non_expired")
    private Boolean isAccountNonExpired;
    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;
    @Column(name = "is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;
    @Column(name = "is_enabled")
    private Boolean isEnabled;
    @Column(name = "additional_emails")
    private String additionalEmails;

    public boolean isEmpty() {
        if (
            id == null &&
            email == null &&
            username == null &&
            password == null &&
            firstName == null &&
            lastName == null &&
            phone == null
        ) {
            return true;
        }

        return false;
    }
}
