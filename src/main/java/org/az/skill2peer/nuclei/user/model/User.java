package org.az.skill2peer.nuclei.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.az.skill2peer.nuclei.common.model.BaseEntity;
import org.az.skill2peer.nuclei.common.model.HasIntegerId;

@Entity
@Table(name = "user_accounts")
@SequenceGenerator(name = "user_accounts_id_seq_name", sequenceName = "user_accounts_id_seq")
public class User extends BaseEntity<Integer> implements HasIntegerId {

    public static class Builder {

        private final User user;

        public Builder() {
            user = new User();
            user.role = Role.ROLE_USER;
        }

        public User build() {
            return user;
        }

        public Builder email(final String email) {
            user.email = email;
            return this;
        }

        public Builder firstName(final String firstName) {
            user.firstName = firstName;
            return this;
        }

        public Builder id(final Integer id) {
            user.id = id;
            return this;
        }

        public Builder lastName(final String lastName) {
            user.lastName = lastName;
            return this;
        }

        public Builder password(final String password) {
            user.password = password;
            return this;
        }

        public Builder signInProvider(final SocialMediaService signInProvider) {
            user.signInProvider = signInProvider;
            return this;
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    private static final long serialVersionUID = -6982852348809467015L;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_accounts_id_seq_name")
    private Integer id;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "password", length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_in_provider", length = 20)
    private SocialMediaService signInProvider;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public SocialMediaService getSignInProvider() {
        return signInProvider;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("creationTime", this.getCreationTime())
                .append("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("modificationTime", this.getModificationTime())
                .append("signInProvider", this.getSignInProvider())
                .append("version", this.getVersion()).toString();
    }
}
